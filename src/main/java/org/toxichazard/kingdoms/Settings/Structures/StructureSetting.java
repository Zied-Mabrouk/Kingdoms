package org.toxichazard.kingdoms.Settings.Structures;

import org.bukkit.Material;
import org.json.simple.JSONObject;

public class StructureSetting {
    public Material head ;
    public String title;


    public StructureSetting(JSONObject jsonObject) {

        this.head = Material.valueOf((String)jsonObject.get("head"));
        this.title = (String)jsonObject.get("title");
    }

}
