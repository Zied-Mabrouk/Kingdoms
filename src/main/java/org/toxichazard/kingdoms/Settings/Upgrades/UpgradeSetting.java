package org.toxichazard.kingdoms.Settings.Upgrades;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.json.simple.JSONObject;

import java.util.List;

public class UpgradeSetting {
    public Material material;
    public String title;
    public List<String> lore;
    public int cost,maxLevel;

    public UpgradeSetting(UpgradeSetting u) {
        this.material = u.material;
        this.maxLevel = u.maxLevel;
        this.title = u.title;
        this.lore = u.lore;
        this.cost = u.cost;
    }

    public UpgradeSetting(JSONObject jsonObject) {
        long cost = (long) jsonObject.get("cost");
        long maxLevel = (long) jsonObject.get("MaxLevel");

        this.material = Material.valueOf((String)jsonObject.get("material"));
        this.title = ChatColor.AQUA+(String)jsonObject.get("title");
        this.lore = (List)jsonObject.get("lore");
        this.cost= (int)cost;
        this.maxLevel= (int)maxLevel;

        this.lore.set(0,ChatColor.GRAY+this.lore.get(0));
        this.lore.set(1,ChatColor.GRAY+this.lore.get(1));
        this.lore.set(3,ChatColor.GREEN+this.lore.get(3));
        this.lore.set(4,ChatColor.GREEN+this.lore.get(4));
    }

}
