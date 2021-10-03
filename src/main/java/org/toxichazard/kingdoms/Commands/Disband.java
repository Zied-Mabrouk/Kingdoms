package org.toxichazard.kingdoms.Commands;

import org.bukkit.entity.Player;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.Constants.Player.Rank;
import org.toxichazard.kingdoms.Settings.Messages;
import org.toxichazard.kingdoms.Utils.KingdomUtil;
import org.toxichazard.kingdoms.main;

public class Disband {

    public static void command(Player player) {

        if(!main.getCKPlayers().containsKey(player.getUniqueId()))
        {
            player.sendMessage(Messages.Kingdom_Necessity);
            return ;
        }
        KPlayer kPlayer = main.getCKPlayers().get(player.getUniqueId());
        if(!kPlayer.getRank().isHigherOrEqualTo(Rank.KING))
        {
            player.sendMessage(Messages.No_Permission);
            return;
        }
        KingdomUtil.delete(player);
    }

}
