package org.toxichazard.kingdoms.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.toxichazard.kingdoms.Constants.Kingdom.Invitation;
import org.toxichazard.kingdoms.Settings.Messages;
import org.toxichazard.kingdoms.main;

import java.util.UUID;

public class Invite {

    public static UUID getInvitedPlayerUUID(String playerName)
    {
        for(Player p:Bukkit.getOnlinePlayers())
        {
            if(p.getName().equals(playerName))
                return p.getUniqueId();
        }
        return null;
    }

    public static void command(Player player, String playerName)
    {
        if(!main.getCKPlayers().containsKey(player.getUniqueId()))
        {
            player.sendMessage(Messages.Kingdom_Necessity);
            return;
        }
        UUID uuid = getInvitedPlayerUUID(playerName);
        if(uuid== null)
        {
            player.sendMessage(Messages.Player_Not_Found);
            return;
        }
        if(main.getCKPlayers().containsKey(uuid))
        {
            player.sendMessage(Messages.Player_Already_In_Kingdom);
            return;
        }

        String kingdomName = main.getCKPlayers().get(player.getUniqueId()).getKingdomName();

        Invitation invitation = new Invitation(kingdomName,player.getUniqueId());
        main.getInvitations().put(uuid,invitation);
        Bukkit.getPlayer(uuid).sendMessage(Messages.Invitation_Received(kingdomName));
        player.sendMessage(Messages.Invitation_Sent);
        Bukkit.getScheduler().runTaskLater(main.getPlugin(),new Runnable()
        {
            @Override
            public void run()
            {
                if(!invitation.isAccepted()){
                    if(main.getInvitations().containsKey(uuid))
                        main.getInvitations().remove(uuid);
                }
            }
        },200);


    }


}
