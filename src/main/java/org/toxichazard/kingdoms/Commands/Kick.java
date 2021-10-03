package org.toxichazard.kingdoms.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.toxichazard.kingdoms.Constants.Kingdom.Kingdom;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.Constants.Player.Rank;
import org.toxichazard.kingdoms.Settings.Messages;
import org.toxichazard.kingdoms.Utils.PlayerUtil;
import org.toxichazard.kingdoms.main;

import java.util.UUID;

import static org.toxichazard.kingdoms.Commands.Invite.getInvitedPlayerUUID;

public class Kick {

    public static void command(Player player,String playerName)
    {
        if(!main.getCKPlayers().containsKey(player.getUniqueId()))
        {
            player.sendMessage(Messages.Kingdom_Necessity);
            return;
        }
        UUID targetUUID = getInvitedPlayerUUID(playerName);
        if(targetUUID== null)
        {
            player.sendMessage(Messages.Player_Not_Found);
            return;
        }
        Player target = Bukkit.getPlayer(targetUUID);
        if(!main.getCKPlayers().containsKey(targetUUID))
        {
            player.sendMessage(Messages.Player_Not_In_Kingdom);
            return;
        }
        KPlayer kPlayer = main.getCKPlayers().get(player.getUniqueId());

        if(!kPlayer.getRank().isHigherOrEqualTo(Rank.NOBLE))
        {
            player.sendMessage(Messages.No_Permission);
            return;
        }


        if(!main.getCKPlayers().get(targetUUID).getKingdomName().equals(kPlayer.getKingdomName()))
        {
            player.sendMessage(Messages.Player_Not_In_Kingdom);
            return;
        }

        if(player.getUniqueId().equals(targetUUID))
        {
            player.sendMessage(Messages.Kick_Yourself);
            return;
        }

        Kingdom kingdom = main.getCKingdoms().get(kPlayer.getKingdomName());
        player.sendMessage(Messages.Kicked_Player(target.getName()));
        kingdom.getMembers().remove(targetUUID);
        main.getCKPlayers().remove(targetUUID);
        PlayerUtil.delete(target);
        target.sendMessage(Messages.Kicked_You);
        Bukkit.getPlayer(targetUUID).getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        kingdom.setNeedsUpdate(true);
    }

}
