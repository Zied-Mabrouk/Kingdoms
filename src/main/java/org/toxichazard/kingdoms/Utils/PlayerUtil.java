package org.toxichazard.kingdoms.Utils;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.Constants.Player.Rank;
import org.toxichazard.kingdoms.main;

import java.util.UUID;

public class PlayerUtil {

    public static boolean isInKingdom(Player player)
    {
        Document filter = new Document("player",player.getUniqueId().toString());
        return(main.getPlayersCollection().countDocuments(filter)==1);
    }
    public static boolean isInKingdom(UUID uuid)
    {
        Document filter = new Document("player",uuid.toString());
        return(main.getPlayersCollection().countDocuments(filter)==1);
    }

    public static void add(KPlayer p)
    {
        if(!isInKingdom(p.getPlayer())) {

            Document player = new Document("player", p.getPlayer().getUniqueId().toString())
                    .append("kingdomName", p.getKingdomName())
                    .append("rank",p.getRank().toString());

            main.getPlayersCollection().insertOne(player);
        }
    }

    public static boolean add(Player p, String s, Rank rank)
    {
        if(!isInKingdom(p)) {

            Document player = new Document("player", p.getUniqueId().toString())
                    .append("kingdomName", s)
                    .append("rank",rank.toString());

            main.getPlayersCollection().insertOne(player);
            return true;
        }
        else
            p.sendMessage(ChatColor.RED+"You are already in a kingdom !");
        return false;
    }

    public static void delete(Player p)
    {
        if(isInKingdom(p)) {

            Document filter = new Document("player", p.getUniqueId().toString());
            main.getPlayersCollection().deleteOne(main.getPlayersCollection().find(filter).first());

        }
    }

    public static void delete(UUID uuid)
    {
        if(isInKingdom(uuid)) {

            Document filter = new Document("player", uuid.toString());
            main.getPlayersCollection().deleteOne(main.getPlayersCollection().find(filter).first());
            if(Bukkit.getOfflinePlayer(uuid).isOnline())
                Bukkit.getPlayer(uuid).sendMessage(ChatColor.RED+"Your kingdom has been disbanded");

        }
    }

    public static String getKingdomName(UUID uuid)
    {
        Document filter = new Document("player", uuid.toString());
        return (main.getPlayersCollection().find(filter).first().getString("kingdomName"));
    }


    public static KPlayer getKPlayer(UUID uuid)
    {
        Document filter = new Document("player", uuid.toString());
        Document doc = main.getPlayersCollection().find(filter).first();
        String kingdomName = doc.getString("kingdomName");
        Player player = Bukkit.getPlayer(uuid);
        Rank rank = Rank.fromString(doc.getString("rank"));
        KPlayer kPlayer = new KPlayer(player,rank,kingdomName);
        return kPlayer;
    }



}
