package org.toxichazard.kingdoms.Constants.Land.Turrets;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.toxichazard.kingdoms.Constants.Land.Land;
import org.toxichazard.kingdoms.Settings.Turrets.TurretSetting;
import org.toxichazard.kingdoms.main;

import java.util.HashMap;

public class Arrow extends Turret {

    public Arrow(Turret turret)
    {
        int lv = turret.getLevel();
        TurretSetting setting = main.getKingdomConfig().getArrow();

        this.range = setting.range + lv ;
        this.speed = setting.speed + (setting.speed*0.1*lv);
        this.repetition = setting.repetition - lv;
        this.head = setting.head;

        this.title = setting.title;
        this.lore = setting.lore;
        this.block=turret.getBlock();
        this.type=TurretType.ARROW;
        this.level=turret.level;
    }

    public Arrow()
    {
        TurretSetting setting = main.getKingdomConfig().getArrow();

        this.range = setting.range;
        this.speed = setting.speed;
        this.repetition = setting.repetition;
        this.head = setting.head;

        this.title = setting.title;
        this.lore = setting.lore;

    }
    public void Shoot()
    {
        HashMap<Float, Entity> targets = new HashMap<>();
        Land land = main.getCLands().get(this.block.getLocation().getChunk().toString());

        targets = AimForPart(land,1.0f,targets);
        targets = AimForPart(land,0.0f,targets);

        if(targets== null ||targets.isEmpty())
            return;

        targets = getClosestTarget(targets);
        Entity target = (Entity) targets.values().toArray()[0];
        Float targetPart = (Float) targets.keySet().toArray()[0];
        Location toLoc = target.getLocation().clone().add(0.0D, targetPart, 0.0D);
        Location fromLoc = this.block.getLocation().clone().add(0.5D, 0.75D, 0.5D);

        double added = fromLoc.distance(target.getLocation()) * 0.01;
        Vector to = toLoc.add(0.0D,added,0.0D).toVector();

        Vector from = fromLoc.toVector();
        Vector direction = to.subtract(from);
        direction.normalize();


        org.bukkit.entity.Arrow arrow = this.block.getLocation().getWorld().spawnArrow(fromLoc, direction, (float) this.speed, this.accuracy);
        arrow.setMetadata("Ammo",
                new FixedMetadataValue(main.getPlugin(),
                        this.type.toString()
                                +","+this.level+","+
                                land.getKingdomName()));

    }

}
