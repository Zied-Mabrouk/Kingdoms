package org.toxichazard.kingdoms.Managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.toxichazard.kingdoms.Constants.Kingdom.Kingdom;
import org.toxichazard.kingdoms.Constants.Kingdom.Upgrades.ChampionUpgrade;
import org.toxichazard.kingdoms.KingdomConfig;
import org.toxichazard.kingdoms.Settings.Messages;
import org.toxichazard.kingdoms.Settings.Upgrades.UpgradeSetting;
import org.toxichazard.kingdoms.main;

import java.util.ArrayList;
import java.util.List;

public class NexusManager {

    public static void openMainGUI(Player player, Kingdom kingdom)
    {

        Inventory nexusGUI = Bukkit.createInventory(player,36, Messages.nexusGUITitle);

        ItemStack RP = new ItemStack(Material.WHEAT,1);
        ItemMeta RP_meta = RP.getItemMeta();
        RP_meta.setDisplayName(ChatColor.YELLOW+"Resource Points Converter");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD+"Convert your items to resource points.");
        lore.add(ChatColor.GOLD+"Your empire has "+ChatColor.YELLOW+String.valueOf(kingdom.getRP())+ChatColor.GOLD+" resource points.");
        RP_meta.setLore(lore);
        RP.setItemMeta(RP_meta);

        ItemStack champion = new ItemStack(Material.DIAMOND,1);
        ItemMeta championMeta = champion.getItemMeta();
        championMeta.setDisplayName("Upgrade Your Champion");
        champion.setItemMeta(championMeta);


        ItemStack regeneration = new ItemStack(Material.POTION,1);
        ItemMeta regenerationMeta = regeneration.getItemMeta();
        regenerationMeta.setDisplayName(ChatColor.YELLOW+"Land Regeneration");
        ArrayList<String> loreRegeneration = new ArrayList<>();
        loreRegeneration.add(ChatColor.GOLD+"Boosts your health regeneration whenever you are not in a fight.");
        loreRegeneration.add(ChatColor.GREEN+"Level: "+ ChatColor.GOLD+String.valueOf(kingdom.getRegenerationUpgrade().getLevel()));
        loreRegeneration.add(ChatColor.GREEN+"Cost: "+ ChatColor.GOLD+String.valueOf(200));
        regenerationMeta.setLore(loreRegeneration);
        regeneration.setItemMeta(regenerationMeta);


        nexusGUI.setItem(3,champion);
        nexusGUI.setItem(4,RP);
        nexusGUI.setItem(5,regeneration);

        player.openInventory(nexusGUI);
    }


    public static void openResourcePointsConverterGUI(Player player)
    {
        Inventory RPGUI = Bukkit.createInventory(player,36, Messages.RPGUITitle);
        player.openInventory(RPGUI);
    }



    public static ItemStack ItemUpgrade(UpgradeSetting upgrades, ChampionUpgrade championUpgrade)
    {
        ItemStack item = new ItemStack(upgrades.material,1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(upgrades.title);
        List<String> lore = new ArrayList<>(upgrades.lore);

        if(championUpgrade.getLevel() ==0)
            lore.set(3,lore.get(3)+ChatColor.RED+"DISABLED");
        else
            lore.set(3,lore.get(3)+ChatColor.GOLD+championUpgrade.getLevel());
        lore.set(4,lore.get(4)+ChatColor.GOLD+upgrades.cost);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return  item;
    }

    public static ItemStack add(ItemStack item,String change,int index)
    {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();

        lore.set(index,lore.get(index)+change);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static void openChampionUpgradeGUI(Player player,Kingdom kingdom)
    {
        Inventory championGUI = Bukkit.createInventory(player,36, Messages.championUpgradeGUITitle);
        KingdomConfig kingdomConfig = main.getKingdomConfig();


        ItemStack health = ItemUpgrade(kingdomConfig.getHealth(),kingdom.getChampionHealthUpgrade());

        ItemStack lava = ItemUpgrade(kingdomConfig.getLava(),kingdom.getChampionLavaUpgrade());

        ItemStack gear = ItemUpgrade(kingdomConfig.getGear(),kingdom.getChampionGearUpgrade());

        ItemStack damage = ItemUpgrade(kingdomConfig.getDamage(),kingdom.getChampionDamageUpgrade());

        ItemStack eject = ItemUpgrade(kingdomConfig.getHits(),kingdom.getChampionHitsUpgrade());

        ItemStack speed = ItemUpgrade(kingdomConfig.getSpeed(),kingdom.getChampionSpeedUpgrade());

        health = add(health,"50",0);
        health = add(health,""+kingdom.getHealthPoints(),1);

        lava = add(lava,""+kingdom.getChampionLavaUpgrade().getLevel(),1);

        gear = add(gear,kingdom.getGearByLevel(),1);

        if(kingdom.getChampionHitsUpgrade().getLevel() !=0)
            eject = add(eject,""+(7-kingdom.getChampionHitsUpgrade().getLevel())+" hits",0);
        else
            eject = add(eject,"x hits",0);


        championGUI.setItem(1,health);
        championGUI.setItem(2,gear);
        championGUI.setItem(3,lava);
        championGUI.setItem(4,damage);
        championGUI.setItem(5,eject);
        championGUI.setItem(6,speed);


        player.openInventory(championGUI);
    }


}
