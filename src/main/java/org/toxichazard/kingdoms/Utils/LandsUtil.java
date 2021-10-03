package org.toxichazard.kingdoms.Utils;

import org.bson.Document;
import org.bukkit.Chunk;
import org.toxichazard.kingdoms.Constants.Land.Land;
import org.toxichazard.kingdoms.Constants.Land.Turrets.Turret;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class LandsUtil {

    public static void add(KPlayer p, Chunk chunk)
    {
        List<String> turrets = new ArrayList<>();
        Document claim = new Document("chunk",chunk.toString())
                .append("kingdomName",p.getKingdom().getName())
                .append("claimer",p.getPlayer().getUniqueId().toString())
                .append("structure",new Document("x",0)
                        .append("y",0)
                        .append("z",0)
                        .append("type","")
                )
                .append("turrets",turrets)
                ;
        main.getLandsCollection().insertOne(claim);
    }

    public static void delete(Chunk chunk)
    {
        Document filter = new Document("chunk", chunk.toString());

        main.getLandsCollection().deleteOne(main.getLandsCollection().find(filter).first());
    }

    public static void update(Chunk chunk)
    {
        Land land = main.getCLands().get(chunk.toString());
        Document filter = new Document("chunk", chunk.toString());

        List<String> turrets = new ArrayList<>();
        for(Map.Entry<String, Turret> entry: land.getTurrets().entrySet())
        {
            Turret turret = entry.getValue();
            turrets.add(turret.getType().toString()+","+
                    turret.getBlock().getX()+","+
                    turret.getBlock().getY()+","+
                    turret.getBlock().getZ()+","+
                    turret.getLevel()
            );
        }

        Document origin = main.getLandsCollection().find(filter).first();
        Document update;
        if(land.getStructure() == null || land.getStructure()
                .getStructureType() == null) {
            update = new Document("chunk",chunk.toString())
                    .append("kingdomName",land.getKingdomName())
                    .append("claimer",origin.getString("claimer"))
                    .append("structure",new Document("x",0)
                            .append("y",0)
                            .append("z",0)
                            .append("type","")
                    )
                    .append("turrets",turrets)
            ;
        }
        else{
            update = new Document("chunk",chunk.toString())
                    .append("kingdomName",land.getKingdomName())
                    .append("claimer",origin.getString("claimer"))
                    .append("structure",new Document("x",land.getStructure().getBlock().getX())
                            .append("y",land.getStructure().getBlock().getY())
                            .append("z",land.getStructure().getBlock().getZ())
                            .append("type",land.getStructure().getStructureType().toString())
                    )
                    .append("turrets",turrets)
            ;
        }
        main.getLandsCollection().replaceOne(filter,update);
    }

    public static void deleteKingdomClaims(String kingdomName)
    {
        Document filter = new Document("kingdomName", kingdomName);

        for(Document tmp : main.getLandsCollection().find(filter))
        {
            main.getLandsCollection().deleteOne(tmp);
            Chunk chunk = Converter.toChunk(tmp.getString("chunk"));
            main.getCLands().remove(chunk);
        }

    }


    public static boolean isClaimed(Chunk chunk)
    {
        Document filter = new Document("chunk",chunk.toString());
        return main.getLandsCollection().countDocuments(filter) == 1;
    }

    public static String getKingdomName(String chunk)
    {
        Document filter = new Document("chunk",chunk);
        return(main.getLandsCollection().find(filter).first().getString("kingdomName"));
    }

}
