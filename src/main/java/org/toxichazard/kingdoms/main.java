package org.toxichazard.kingdoms;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.toxichazard.kingdoms.Commands.KingdomCommand;
import org.toxichazard.kingdoms.Commands.Sunny;
import org.toxichazard.kingdoms.Constants.Kingdom.Champion;
import org.toxichazard.kingdoms.Constants.Kingdom.Invitation;
import org.toxichazard.kingdoms.Constants.Kingdom.Kingdom;
import org.toxichazard.kingdoms.Constants.Land.Land;
import org.toxichazard.kingdoms.Constants.Land.Structures.Structure;
import org.toxichazard.kingdoms.Constants.Land.Structures.StructureType;
import org.toxichazard.kingdoms.Constants.Land.Turrets.Turret;
import org.toxichazard.kingdoms.Constants.Land.Turrets.TurretType;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.Events.*;
import org.toxichazard.kingdoms.Utils.Converter;
import org.toxichazard.kingdoms.Utils.KingdomUtil;
import org.toxichazard.kingdoms.Utils.PlayerUtil;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class main extends JavaPlugin {


    public static File file ;
    private static  KingdomConfig kingdomConfig;

    public static KingdomConfig getKingdomConfig() {
        return kingdomConfig;
    }

    private static MongoCollection<Document> KingdomsCollection;
    private static MongoCollection<Document> PlayersCollection;
    private static MongoCollection<Document> LandsCollection;

    private MongoClient mongoClient;
    private MongoDatabase database;

    private static main plugin;

    private static HashMap<UUID, KPlayer> CKPlayers= new HashMap<>();
    private static HashMap<String, Kingdom> CKingdoms= new HashMap<>();
    private static HashMap<String, Land> CLands= new HashMap<>();
    private static HashMap<UUID, Invitation> Invitations= new HashMap<>();
    private static HashMap<UUID, Champion> Invades= new HashMap<>();

    private static World world = null;

    public static List<Material> turrets ;

    public static List<Material> forbiddenItems = Arrays.asList(
            Material.SNOWBALL,
            Material.STICK
    );

    @Override
    public void onEnable() {
        file= new File(this.getDataFolder()+"/kingdom_configs.json");
        kingdomConfig = new KingdomConfig();

        plugin = this;

        world = Bukkit.getWorld("world");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nThe Plugin has been enabled\n\n");

        System.setProperty("DEBUG.GO","true");
        System.setProperty("DB.TRACE","true");
        Logger mongoLobber = Logger.getLogger("org.mongodb.driver");
        mongoLobber.setLevel(Level.WARNING);
        Connect();
        Init();

        getServer().getPluginManager().registerEvents(new Weather(),this);
        getServer().getPluginManager().registerEvents(new MainEvents(),this);
        getServer().getPluginManager().registerEvents(new KingdomEvents(),this);
        getServer().getPluginManager().registerEvents(new TurretsEvents(),this);
        getServer().getPluginManager().registerEvents(new StructureEvents(),this);
        getServer().getPluginManager().registerEvents(new InvadeEvents(),this);

        getCommand("s").setExecutor(new Sunny());

        getCommand("k").setTabCompleter(new KingdomsTabCompleter());
        getCommand("k").setExecutor(new KingdomCommand());

        turrets=Arrays.asList(
                main.getKingdomConfig().getHealing().head,
                main.getKingdomConfig().getArrow().head,
                main.getKingdomConfig().getFlame().head
        );
        turretTitles=Arrays.asList(
                main.getKingdomConfig().getHealing().title,
                main.getKingdomConfig().getArrow().title,
                main.getKingdomConfig().getFlame().title
        );

    }

    @Override
    public void onDisable() {
        KingdomUtil.updateAll();
        CKingdoms.clear();
        CKPlayers.clear();
        CLands.clear();
        for(Map.Entry<UUID,Champion> entry: Invades.entrySet()) {
            Champion champion = entry.getValue();
            champion.getBossBar().removeAll();
            champion.DisableBar();
        }

        Invades.clear();
        Invitations.clear();
    }


    public void Connect()
    {
        MongoClientURI uri = new MongoClientURI("mongodb+srv://ToxicHazard:YknfkzRh2kmnLBZ@kingdoms.oqpjm.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");

        mongoClient = new MongoClient(uri);
        database = mongoClient.getDatabase("Kingdoms");
        KingdomsCollection = database.getCollection("Kingdoms");
        PlayersCollection = database.getCollection("Players");
        LandsCollection = database.getCollection("Lands");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Connected successfully");

    }

    public static List<String> cmds = Arrays.asList(
            "claim",
            "deny",
            "disband",
            "nexus",
            "invade",
            "unclaim",
            "accept",
            "home",
            "kick",
            "invite",
            "create",
            "getTurret"
    );

    public static List<String> turretTitles ;

    public static MongoCollection<Document> getKingdomsCollection() {
        return KingdomsCollection;
    }
    public static MongoCollection<Document> getPlayersCollection() {
        return PlayersCollection;
    }
    public static MongoCollection<Document> getLandsCollection() {
        return LandsCollection;
    }

    public static World getWorld() {
        return world;
    }

    public static HashMap<String, Kingdom> getCKingdoms() {
        return CKingdoms;
    }

    public static HashMap<String, Land> getCLands() {
        return CLands;
    }

    public static HashMap<UUID, KPlayer> getCKPlayers() {
        return CKPlayers;
    }

    public static main getPlugin() {
        return plugin;
    }

    public static HashMap<UUID, Invitation> getInvitations() {
        return Invitations;
    }

    public static HashMap<UUID, Champion> getInvades() {
        return Invades;
    }

    public void Init()
    {

        for(Document tmp : LandsCollection.find())
        {
            Chunk chunk = Converter.toChunk(tmp.getString("chunk"));
            Land land = new Land(chunk);

            Structure structure= null;
            Document str = (Document) tmp.get("structure");
            if(!str.getString("type").equals("")) {
                Block block = world.getBlockAt(str.getInteger("x"),str.getInteger("y"),str.getInteger("z"));
                structure = new Structure(StructureType.fromString(str.getString("type")), block);
            }

            if(structure != null && structure.getStructureType() != null) {
                structure = structure.transform();
                structure.toBlock(land.getKingdomName());
            }
            land.setStructure(structure);
            List<String> turretstmp = tmp.getList("turrets",String.class);
            if(turretstmp!=null && turretstmp.size()!=0 ) {
                for (String turretString : turretstmp) {
                    String[] data = turretString.split(",",5);
                    Block blockTurret = Converter.toBlock((int)Double.parseDouble(data[1]),(int)Double.parseDouble(data[2]),(int)Double.parseDouble(data[3]));

                    Turret turret = new Turret(Integer.parseInt(data[4]),blockTurret, TurretType.FromString(data[0]));
                    turret = turret.transform();
                    turret.setOwner(land.getKingdomName());
                    land.getTurrets().put(blockTurret.getLocation().toString(),turret);
                    turret.toBlock(land.getKingdomName());

                }
            }

            CLands.put(chunk.toString(),land);
        }

        UUID uuid;

        for(Player p : Bukkit.getOnlinePlayers())
        {
            uuid = p.getUniqueId();
            if(PlayerUtil.isInKingdom(uuid))
            {
                KPlayer kPlayer = PlayerUtil.getKPlayer(uuid);
                kPlayer.updateScoreBoard();
                CKPlayers.put(uuid,kPlayer);
            }

        }

    }
}
