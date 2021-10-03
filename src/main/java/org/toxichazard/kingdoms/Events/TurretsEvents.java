package org.toxichazard.kingdoms.Events;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.toxichazard.kingdoms.Constants.Kingdom.Champion;
import org.toxichazard.kingdoms.Constants.Land.Land;
import org.toxichazard.kingdoms.Constants.Land.Turrets.Turret;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.Events.CustomEvents.AmmoDamageEvent;
import org.toxichazard.kingdoms.Events.CustomEvents.TurretBreakEvent;
import org.toxichazard.kingdoms.Events.CustomEvents.TurretPlaceEvent;
import org.toxichazard.kingdoms.Settings.Messages;
import org.toxichazard.kingdoms.Utils.Converter;
import org.toxichazard.kingdoms.Utils.LandsUtil;
import org.toxichazard.kingdoms.main;

public class TurretsEvents implements Listener {
    @EventHandler
    public void onTurretBreak(TurretBreakEvent event)
    {
        Land land = main.getCLands().get(event.getBlock().getChunk().toString());
        Turret turret = Converter.toTurret(event.getBlock(),land);
        turret.transform();
        turret.Break(event.getPlayer());

    }

    @EventHandler
    public void onTurretPlace(TurretPlaceEvent event)
    {
        ItemStack itemInHand = event.getEvent().getItemInHand();
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Chunk chunk = block.getChunk();
        Land land = main.getCLands().get(chunk.toString());

        if(!Turret.isOnFence(block))
        {
            player.sendMessage(Messages.Turret_On_Fence);
            event.getEvent().setCancelled(true);
            return;
        }
        Turret turret = Converter.toTurret(itemInHand,block);
        land.getTurrets().put(block.getLocation().toString(),turret);
        turret.toBlock(land.getKingdomName());
        player.sendMessage(ChatColor.GREEN+"Turret added !");
        LandsUtil.update(chunk);
    }

    @EventHandler
    public void onAmmoDamage(AmmoDamageEvent event)
    {
        Entity target = event.getEvent().getEntity();

        if(main.getCKPlayers().containsKey(target.getUniqueId()))
        {
            KPlayer kPlayer = main.getCKPlayers().get(target.getUniqueId());
            if(kPlayer.getKingdomName().equals(event.getAmmo().getKingdomName()))
                event.getEvent().setCancelled(true);

            return;
        }

        if(main.getInvades().containsKey(target.getUniqueId()))
        {
            Champion champion = main.getInvades().get(target.getUniqueId());

            if(champion.getKingdom().getName().equals(event.getAmmo().getKingdomName()))
                event.getEvent().setCancelled(true);
            return;
        }
    }

}
