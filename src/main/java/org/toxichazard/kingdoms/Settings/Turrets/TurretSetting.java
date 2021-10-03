package org.toxichazard.kingdoms.Settings.Turrets;

import org.bukkit.Material;
import org.json.simple.JSONObject;

public class TurretSetting {

    public  int range ;
    public  double speed ;
    public  int repetition ;
    public  Material head ;
    public  String lore;
    public  String title;


    public TurretSetting(JSONObject jsonObject) {

        long range = (long) jsonObject.get("range");
        double speed = (double) jsonObject.get("speed");
        long repetition = (long) jsonObject.get("repetition");

        this.range = (int)range;
        this.speed = speed;
        this.repetition = (int)repetition;
        this.head = Material.valueOf((String)jsonObject.get("head"));
        this.title = (String)jsonObject.get("title");
        this.lore = (String)jsonObject.get("lore");
    }
}
