package org.toxichazard.kingdoms.Utils;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.toxichazard.kingdoms.Constants.Kingdom.Kingdom;
import org.toxichazard.kingdoms.Constants.Kingdom.Upgrades.ChampionUpgrade;
import org.toxichazard.kingdoms.Constants.Kingdom.Upgrades.ChampionUpgradeType;
import org.toxichazard.kingdoms.Constants.Kingdom.Upgrades.NexusUpgrade;
import org.toxichazard.kingdoms.Constants.Kingdom.Upgrades.NexusUpgradeType;
import org.toxichazard.kingdoms.Constants.Land.Land;
import org.toxichazard.kingdoms.Constants.Land.Structures.StructureType;
import org.toxichazard.kingdoms.main;

import java.util.*;

public class KingdomUtil {

    public static boolean isExist(Player king)
    {
        Document filter = new Document("king",king.getUniqueId().toString());
        return main.getKingdomsCollection().countDocuments(filter) == 1;
    }

    public static void create(Player p, String s)
    {
        if(!isExist(p)) {
            List<String> members = new ArrayList<>();
            members.add(p.getUniqueId().toString());
            Document kingdom = new Document("king", p.getUniqueId().toString())
                    .append("kingdomName", s)
                    .append("nexus", new Document("regenerationLevel", 0)
                            .append("TNTLevel", 0)
                    )
                    .append("champion", new Document("movementSpeedLevel", 1)
                            .append("damageLevel", 1)
                            .append("healthLevel", 1)
                            .append("gearLevel", 1)
                            .append("clearLavaRadius", 0)
                            .append("numberOfHits", 0)
                            .append("disarm", 0)
                    ).append("members", members)
                    .append("RP",10)
                    .append("nexusChunk", "");

            main.getKingdomsCollection().insertOne(kingdom);

            p.sendMessage(ChatColor.GREEN+"Your kingdom "+ChatColor.GOLD+s+ChatColor.GREEN+" has been created !");
            p.sendMessage(ChatColor.GREEN+"Have a good start by claiming a land and putting a nexus");
        }
        else
            p.sendMessage(ChatColor.RED+"This kingdom exists already !");
    }

    public static void updateAll() {
        for(Map.Entry<String, Kingdom> entry: main.getCKingdoms().entrySet())
        {
            Kingdom tmp = entry.getValue();
            if(tmp.isNeedsUpdate()) {

                Document filter = new Document("kingdomName", tmp.getName());


                List<String> members = new ArrayList<>();
                for (UUID id : tmp.getMembers())
                    members.add(id.toString());

                String val ="";
                if(tmp.getNexusLand()!=null)
                    val = tmp.getNexusLand().toString();

                Document update = new Document("king", tmp.getKing().toString())
                        .append("kingdomName", tmp.getName())
                        .append("nexus", new Document("regenerationLevel", tmp.getRegenerationUpgrade().getLevel())
                                .append("TNTLevel", tmp.getTntUpgrade().getLevel())
                        )
                        .append("champion", new Document("movementSpeedLevel", tmp.getChampionSpeedUpgrade().getLevel())
                                .append("damageLevel", tmp.getChampionDamageUpgrade().getLevel())
                                .append("healthLevel", tmp.getChampionHealthUpgrade().getLevel())
                                .append("gearLevel", tmp.getChampionGearUpgrade().getLevel())
                                .append("clearLavaRadius", tmp.getChampionLavaUpgrade().getLevel())
                                .append("numberOfHits", tmp.getChampionHitsUpgrade().getLevel())
                                .append("disarm", tmp.getChampionDisarmUpgrade().getLevel())
                        ).append("members", members)
                        .append("RP", tmp.getRP())
                        .append("nexusChunk", val);

                main.getKingdomsCollection().replaceOne(filter, update);
            }
        }
    }


    public static void update(String kingdomName) {
            Kingdom tmp = main.getCKingdoms().get(kingdomName);
            if(tmp.isNeedsUpdate()) {

                Document filter = new Document("kingdomName", tmp.getName());


                List<String> members = new ArrayList<>();
                for (UUID id : tmp.getMembers())
                    members.add(id.toString());
                String val ="";
                if(tmp.getNexusLand()!=null)
                    val = tmp.getNexusLand().toString();

                Document update = new Document("king", tmp.getKing().toString())
                        .append("kingdomName", tmp.getName())
                        .append("nexus", new Document("regenerationLevel", tmp.getRegenerationUpgrade().getLevel())
                                .append("TNTLevel", tmp.getTntUpgrade().getLevel())
                        )
                        .append("champion", new Document("movementSpeedLevel", tmp.getChampionSpeedUpgrade().getLevel())
                                .append("damageLevel", tmp.getChampionDamageUpgrade().getLevel())
                                .append("healthLevel", tmp.getChampionHealthUpgrade().getLevel())
                                .append("gearLevel", tmp.getChampionGearUpgrade().getLevel())
                                .append("clearLavaRadius", tmp.getChampionLavaUpgrade().getLevel())
                                .append("numberOfHits", tmp.getChampionHitsUpgrade().getLevel())
                                .append("disarm", tmp.getChampionDisarmUpgrade().getLevel())
                        ).append("members", members)
                        .append("RP", tmp.getRP())
                        .append("nexusChunk", val);

                main.getKingdomsCollection().replaceOne(filter, update);
            }
    }




    public static void delete (Player p)
    {
        Document kingdom = new Document("king", p.getUniqueId().toString());
        String s = main.getKingdomsCollection().find(kingdom).first().getString("kingdomName");
        List<String> members  = main.getKingdomsCollection().find(kingdom).first().getList("members", String.class);

        for(String uuid : members)
        {
            UUID id = UUID.fromString(uuid);
            PlayerUtil.delete(id);
            if(main.getCKPlayers().containsKey(id)) {
                Bukkit.getPlayer(id).getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                main.getCKPlayers().remove(id);
            }

        }
        List<String> removed= new ArrayList<>();

        for(Map.Entry<String,Land> entry: main.getCLands().entrySet())
        {
            if(entry.getValue().getKingdomName().equals(s))
                removed.add(entry.getKey());
        }

        for(String chunk:removed)
        {
            Land land = main.getCLands().get(chunk);
            if(land.getStructure()!=null)
            {
                if(land.getStructure().getStructureType()!=null)
                {
                    if(land.getStructure().getStructureType().equals(StructureType.NEXUS))
                    {
                        Block block = land.getStructure().getBlock();
                        block.setType(Material.AIR);
                    }
                }
            }
            main.getCLands().remove(chunk);
        }
        removed.clear();


        LandsUtil.deleteKingdomClaims(s);
        main.getKingdomsCollection().deleteOne(main.getKingdomsCollection().find(kingdom).first());
        main.getCKingdoms().remove(s);
    }


    public static Kingdom getKingdom(String name)
    {
        Document filter = new Document("kingdomName", name);
        Document kingdom = main.getKingdomsCollection().find(filter).first();

        Document champion = (Document) kingdom.get("champion");
        Document nexus = (Document) kingdom.get("nexus");

        Kingdom k = new Kingdom();

        k.setName(name);
        k.setKing(UUID.fromString(kingdom.getString("king")));
        k.setRP(kingdom.getInteger("RP"));
        List<String> members = kingdom.getList("members", String.class);

        for(String member: members)
        {
            k.getMembers().add(UUID.fromString(member));
        }

        List<ChampionUpgrade> championUpgrades = Arrays.asList(
                new ChampionUpgrade(ChampionUpgradeType.HEALTH,champion.getInteger("healthLevel")),
                new ChampionUpgrade(ChampionUpgradeType.SPEED,champion.getInteger("movementSpeedLevel")),
                new ChampionUpgrade(ChampionUpgradeType.DAMAGE,champion.getInteger("damageLevel")),
                new ChampionUpgrade(ChampionUpgradeType.GEAR,champion.getInteger("gearLevel")),
                new ChampionUpgrade(ChampionUpgradeType.LAVA,champion.getInteger("clearLavaRadius")),
                new ChampionUpgrade(ChampionUpgradeType.HITS,champion.getInteger("numberOfHits")),
                new ChampionUpgrade(ChampionUpgradeType.DISARM,champion.getInteger("disarm"))
        );
        List<NexusUpgrade> nexusUpgrades = Arrays.asList(
                new NexusUpgrade(NexusUpgradeType.REGENERATION,nexus.getInteger("regenerationLevel")),
                new NexusUpgrade(NexusUpgradeType.TNT,nexus.getInteger("TNTLevel"))
        );
        k.setChampionUpgrades(championUpgrades);
        k.setNexusUpgrades(nexusUpgrades);
        k.setNexusLand(Converter.toChunk(kingdom.getString("nexusChunk")));


        return k;
    }


}
