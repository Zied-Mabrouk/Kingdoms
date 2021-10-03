package org.toxichazard.kingdoms.Commands;

import org.bukkit.entity.Player;
import org.toxichazard.kingdoms.Constants.Kingdom.Kingdom;
import org.toxichazard.kingdoms.Settings.Messages;
import org.toxichazard.kingdoms.main;

public class Home {

    public static void command(Player player)
    {
        if(!main.getCKPlayers().containsKey(player.getUniqueId())){
            player.sendMessage(Messages.Kingdom_Necessity);
            return ;
        }
        Kingdom kingdom = main.getCKPlayers().get(player.getUniqueId()).getKingdom();
        if(kingdom.getNexusLand() == null)
        {
            return ;
        }
        player.teleport(kingdom.getNexus().getLocation().add(0,1,0));
    }


}
