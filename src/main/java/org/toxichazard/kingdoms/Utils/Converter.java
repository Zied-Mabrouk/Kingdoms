package org.toxichazard.kingdoms.Utils;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.toxichazard.kingdoms.Constants.Land.Land;
import org.toxichazard.kingdoms.Constants.Land.Structures.Structure;
import org.toxichazard.kingdoms.Constants.Land.Structures.StructureType;
import org.toxichazard.kingdoms.Constants.Land.Turrets.Ammo;
import org.toxichazard.kingdoms.Constants.Land.Turrets.Turret;
import org.toxichazard.kingdoms.Constants.Land.Turrets.TurretType;
import org.toxichazard.kingdoms.main;

public class Converter {

    public static Chunk toChunk(String value)
    {
        if(value.isEmpty())
            return null;
        String[] metas = value.split("=",3);

        String x = metas[1].replace("z","");
        String z = metas[2].replace("}","");

        Location location = new Location(main.getWorld(),Integer.parseInt(x)*16,0,Integer.parseInt(z)*16);

        return (main.getWorld().getChunkAt(location));
    }

    public static Block toBlock(String value)
    {
        if(value.isEmpty())
            return null;
        String[] metas = value.split("=",4);

        String x = metas[1].replace("z","");
        String y = metas[2].replace("z","");
        String z = metas[3].replace("}","");

        Location location = new Location(main.getWorld(),Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(z));

        return (main.getWorld().getBlockAt(location));
    }

    public static Location toLocation(double x,double y,double z)
    {
        return (new Location(main.getWorld(),x,y,z));
    }

    public static Block toBlock(int x, int y, int z)
    {
        return (main.getWorld().getBlockAt(x,y,z));
    }

    public static Turret toTurret(Block block, Land land)
    {
        if(land.getTurrets().containsKey(block.getLocation().toString()))
            return land.getTurrets().get(block.getLocation().toString());

        String meta = block.getMetadata("Turret").get(0).asString();
        String[] metas = meta.split(",",6);
        Turret turret = new Turret(Integer.parseInt(metas[4]),block,TurretType.FromString(metas[0]));

        return turret;
    }


    public static Ammo toAmmo(Entity entity)
    {
        String str = entity.getMetadata("Ammo").get(0).asString();
        String[] s = str.split(",",3);
        Ammo ammo = new Ammo(TurretType.FromString(s[0]),Integer.parseInt(s[1]),s[2]);
        return ammo;
    }

    public static Turret toTurret(ItemStack item,Block block)
    {
        String title = item.getItemMeta().getDisplayName();
        TurretType type=null;
        switch (title)
        {
            case "Arrow Turret":
                type=TurretType.ARROW;
                break;
            case "Healing Turret":
                type=TurretType.HEALING;
                break;
            case "Flame Turret":
                type=TurretType.FLAME;
                break;
        }
        int levelItem = Integer.parseInt(item.getItemMeta().getLore().get(1).split(":",2)[1]);
        Turret turret = new Turret(levelItem,block, type);
        turret = turret.transform();
        return turret;
    }
    public static Structure toStructure(Block block, Land land)
    {
        if(land.getStructure() != null && land.getStructure().getBlock().equals(block))
            return land.getStructure();

        if(block.hasMetadata("Structure")) {
            String meta = block.getMetadata("Structure").get(0).asString();
            Bukkit.broadcastMessage(meta);
            Structure structure = new Structure(StructureType.fromMaterial(block.getType()),block);
            return structure;
        }

        return null;
    }

}
