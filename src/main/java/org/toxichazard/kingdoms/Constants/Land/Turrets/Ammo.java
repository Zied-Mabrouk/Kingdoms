package org.toxichazard.kingdoms.Constants.Land.Turrets;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class Ammo {
    private TurretType type;
    private int level;
    private String kingdomName;

    public Ammo(TurretType type, int level, String kingdomName) {
        this.type = type;
        this.level = level;
        this.kingdomName = kingdomName;
    }

    public static boolean isAmmo(Entity entity)
    {
        return (entity.hasMetadata("Ammo"));
    }

    public TurretType getType() {
        return type;
    }

    public void setType(TurretType type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getKingdomName() {
        return kingdomName;
    }

    public void setKingdomName(String kingdomName) {
        this.kingdomName = kingdomName;
    }
}
