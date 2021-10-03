package org.toxichazard.kingdoms.Constants.Land.Turrets;

import org.toxichazard.kingdoms.Settings.Turrets.TurretSetting;
import org.toxichazard.kingdoms.main;

public class Healing extends Turret {

    public Healing(Turret turret)
    {
        TurretSetting setting = main.getKingdomConfig().getHealing();
        int lv = turret.getLevel();

        this.range = setting.range + lv ;
        this.speed = setting.speed + (setting.speed*0.1*lv);
        this.repetition = setting.repetition - lv;
        this.head = setting.head;

        this.title = setting.title;
        this.lore = setting.lore;
        this.block=turret.getBlock();
        this.type=TurretType.HEALING;
        this.level=turret.level;
    }

    public Healing()
    {
        TurretSetting setting = main.getKingdomConfig().getHealing();
        this.range = setting.range;
        this.speed = setting.speed;
        this.repetition = setting.repetition;
        this.head = setting.head;

        this.title = setting.title;
        this.lore = setting.lore;
    }
}
