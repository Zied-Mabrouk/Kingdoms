package org.toxichazard.kingdoms.Managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.toxichazard.kingdoms.Constants.Land.Land;
import org.toxichazard.kingdoms.Constants.Land.Turrets.Turret;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.Utils.LandsUtil;
import org.toxichazard.kingdoms.main;

import java.util.ArrayList;

public class TurretManager {
    public static void openMainGUI(Player player, Turret turret)
    {
        KPlayer kPlayer = main.getCKPlayers().get(player.getUniqueId());

        Inventory turretGUI=null;

        if(kPlayer.getTurretClicked().getType().equals(main.getKingdomConfig().getArrow().head))
            turretGUI = Bukkit.createInventory(player,36, main.getKingdomConfig().getArrow().title);
        else if(kPlayer.getTurretClicked().getType().equals(main.getKingdomConfig().getFlame().head))
            turretGUI = Bukkit.createInventory(player,36, main.getKingdomConfig().getFlame().title );
        else if(kPlayer.getTurretClicked().getType().equals(main.getKingdomConfig().getHealing().head))
            turretGUI = Bukkit.createInventory(player,36, main.getKingdomConfig().getHealing().title );


        ItemStack add = new ItemStack(Material.NETHER_STAR,1);
        ItemMeta add_meta = add.getItemMeta();
        add_meta.setDisplayName(ChatColor.YELLOW+"Increase Level");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD+"Actual Level: "+turret.getLevel());
        add_meta.setLore(lore);
        add.setItemMeta(add_meta);


        ItemStack desc = new ItemStack(Material.SPRUCE_SIGN,1);
        ItemMeta desc_meta = desc.getItemMeta();
        desc_meta.setDisplayName(ChatColor.YELLOW+"Description");
        ArrayList<String> desclore = new ArrayList<>();
        desclore.add(ChatColor.GOLD+"Level: "+turret.getLevel());
        desclore.add(ChatColor.GOLD+"Range: "+turret.getRange());
        desclore.add(ChatColor.GOLD+"Speed: "+String.format("%.2f",turret.getSpeed()));
        desc_meta.setLore(desclore);
        desc.setItemMeta(desc_meta);

        turretGUI.setItem(3,add);
        turretGUI.setItem(5,desc);


        player.openInventory(turretGUI);
    }

    public static void onClickTurretGUI(InventoryClickEvent event, KPlayer kPlayer)
    {
        Land land = main.getCLands().get(kPlayer.getTurretClicked().getLocation().getChunk().toString());
        Turret turret = land.getTurrets().get(kPlayer.getTurretClicked().getLocation().toString());
        if(event.getCurrentItem()==null)
            return;
        switch(event.getCurrentItem().getType())
        {
            case NETHER_STAR:
                if(turret.getLevel()<9) {
                    turret.setActivated(false);

                    land.getTurrets().remove(turret.getBlock().getLocation().toString());

                    turret.getBlock().removeMetadata(land.getKingdomName(), main.getPlugin());

                    turret.setLevel(turret.getLevel() + 1);
                    turret = turret.transform();
                    turret.toBlock(land.getKingdomName());
                    land.getTurrets().put(turret.getBlock().getLocation().toString(),turret);
                    LandsUtil.update(land.getChunk());
                    TurretManager.openMainGUI(kPlayer.getPlayer(), turret);
                }
                else
                    kPlayer.getPlayer().playSound(kPlayer.getPlayer().getLocation(), Sound.ENTITY_HOSTILE_BIG_FALL,2.0F,1.0F);
                break;
            case SPRUCE_SIGN:
                break;
        }
    }
}
