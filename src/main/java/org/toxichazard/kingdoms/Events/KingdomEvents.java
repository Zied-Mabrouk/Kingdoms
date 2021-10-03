package org.toxichazard.kingdoms.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.toxichazard.kingdoms.Constants.Land.Land;
import org.toxichazard.kingdoms.Constants.Land.Structures.Structure;
import org.toxichazard.kingdoms.Constants.Land.Turrets.Turret;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.Events.CustomEvents.*;
import org.toxichazard.kingdoms.Settings.Messages;
import org.toxichazard.kingdoms.main;

public class KingdomEvents implements Listener {

    @EventHandler
    public void onMoveBetweenLands(MoveBetweenLandsEvent event)
    {
        String subTitle = Messages.Welcome_Wilderness_SubTitle;
        String title = Messages.Welcome_Wilderness_Title;

        if (!event.getKingdomName().isEmpty()) {
            if(main.getCKPlayers().containsKey(event.getPlayer().getUniqueId()))
            {
                KPlayer kPlayer = main.getCKPlayers().get(event.getPlayer().getUniqueId());
                if(kPlayer.getKingdomName().equals(event.getKingdomName())) {
                    title = ChatColor.GOLD + event.getKingdomName();
                    subTitle = Messages.Welcome_Home;
                }
                else
                {
                    subTitle = Messages.Kingdom_Found(event.getKingdomName());
                    title = ChatColor.RED+event.getKingdomName();
                }
            }
            else
            {
                subTitle = Messages.Kingdom_Found(event.getKingdomName());
                title = ChatColor.RED+event.getKingdomName();
            }
        }
        event.getPlayer().sendMessage(subTitle);

        event.getPlayer().sendTitle(title,subTitle,5,50,5);
    }

    @EventHandler
    public void onBlockBreakInLand(BlockBreakInLandEvent event)
    {
        Player player = event.getEvent().getPlayer();

        if(main.getCKPlayers().containsKey(player.getUniqueId()))
        {
            KPlayer kPlayer = main.getCKPlayers().get(player.getUniqueId());

            if(kPlayer.getKingdomName().equals(event.getLand().getKingdomName()))
            {

                Block block = event.getEvent().getBlock();

                if(Structure.isStructure(block))
                {
                    Bukkit.getServer().getPluginManager().callEvent(new StructureBreakEvent(block, player, event.getEvent()));
                    return;
                }

                if(Turret.isTurret(block))
                {
                    Bukkit.getServer().getPluginManager().callEvent(new TurretBreakEvent(block, player));
                    return;

                }
            }
            else{
                event.getEvent().setCancelled(true);
                player.sendMessage(Messages.Block_Break_In_Other_Kingdom);
                return;
            }
        }
        else{
            event.getEvent().setCancelled(true);
            player.sendMessage(Messages.Block_Break_In_Other_Kingdom);
            return;
        }


    }


    @EventHandler
    public void onBlockPlaceInLand(BlockPlaceInLandEvent event)
    {
        Land land = main.getCLands().get(event.getEvent().getBlock().getChunk().toString());
        Player player = event.getEvent().getPlayer();
        if(main.getCKPlayers().containsKey(player.getUniqueId()))
        {


            KPlayer kPlayer = main.getCKPlayers().get(player.getUniqueId());

            if(kPlayer.getKingdomName().equals(land.getKingdomName()))
            {
                if(kPlayer.hasRequiredRank(land.getRequiredRank()))
                {
                    if(Turret.isTurretItem(event.getEvent().getItemInHand())) {
                        Bukkit.getServer().getPluginManager().callEvent(new TurretPlaceEvent(event.getEvent().getBlock(), player, event.getEvent()));
                        return;
                    }
                    if(Structure.isStructureItem(event.getEvent().getItemInHand()))
                    {
                        Bukkit.getServer().getPluginManager().callEvent(new StructurePlaceEvent(event.getEvent().getBlock(), player, event.getEvent()));
                        return;
                    }
                }
                else
                {
                    event.getEvent().setCancelled(true);
                    player.sendMessage(Messages.No_Permission);
                    return;
                }


            }
            else
            {
                event.getEvent().setCancelled(true);
                player.sendMessage(Messages.Block_Place_In_Other_Kingdom);
                return;
            }
        }

        else
        {
            event.getEvent().setCancelled(true);
            player.sendMessage(Messages.Block_Place_In_Other_Kingdom);
            return;
        }
    }


}
