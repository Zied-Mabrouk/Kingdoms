package org.toxichazard.kingdoms.Commands;

import org.bukkit.entity.Player;
import org.toxichazard.kingdoms.Constants.Kingdom.Kingdom;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.Settings.Messages;
import org.toxichazard.kingdoms.Utils.KingdomUtil;
import org.toxichazard.kingdoms.Utils.PlayerUtil;
import org.toxichazard.kingdoms.main;

public class Accept {

    public static void command(Player player)
    {
        if(!main.getInvitations().containsKey(player.getUniqueId()))
        {
            player.sendMessage(Messages.No_Invitation);
            return;
        }

        String kingdomName = main.getInvitations().get(player.getUniqueId()).getKingdomName();

        KPlayer tmp = new KPlayer(player,kingdomName);
        main.getCKPlayers().put(player.getUniqueId(),tmp);
        PlayerUtil.add(tmp);

        player.sendMessage(Messages.You_Kingdom_Join(kingdomName));

        Kingdom kingdom = main.getCKingdoms().get(kingdomName);
        kingdom.notifyJoin(player.getName());
        if(!kingdom.getMembers().contains(player.getUniqueId()))
            kingdom.getMembers().add(player.getUniqueId());
        //KingdomUtil.update(kingdom.getName());
        kingdom.setNeedsUpdate(true);


    }

}
