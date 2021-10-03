package org.toxichazard.kingdoms.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.toxichazard.kingdoms.Settings.Messages;
import org.toxichazard.kingdoms.main;

public class Deny {


    public static void command(Player player)
    {
        if(!main.getInvitations().containsKey(player.getUniqueId()))
        {
            player.sendMessage(Messages.No_Invitation);
            return;
        }
        player.sendMessage(Messages.You_Denied);
        Bukkit.getPlayer(main.getInvitations().get(player.getUniqueId()).getInviter()).sendMessage(Messages.Denied_Invitation(player.getName()));
        main.getInvitations().remove(player.getUniqueId());
    }

}
