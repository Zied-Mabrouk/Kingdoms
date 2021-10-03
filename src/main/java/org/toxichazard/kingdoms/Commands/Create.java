package org.toxichazard.kingdoms.Commands;

import org.bukkit.entity.Player;
import org.toxichazard.kingdoms.Constants.Kingdom.Kingdom;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.Constants.Player.Rank;
import org.toxichazard.kingdoms.Utils.KingdomUtil;
import org.toxichazard.kingdoms.Utils.PlayerUtil;
import org.toxichazard.kingdoms.main;

public class Create {
    public static void command(Player player,String kingdomName){

        if(PlayerUtil.add(player,kingdomName, Rank.KING)) {
            KingdomUtil.create(player, kingdomName);
            Kingdom kingdom = new Kingdom();

            kingdom.setKing(player.getUniqueId());
            kingdom.setName(kingdomName);
            kingdom.getMembers().add(player.getUniqueId());


            main.getCKingdoms().put(kingdomName,kingdom);

            KPlayer kPlayer = new KPlayer(player,Rank.KING,kingdomName);

            if(!main.getCKPlayers().containsKey(player.getUniqueId()))
                main.getCKPlayers().put(player.getUniqueId(), kPlayer);

            kPlayer.updateScoreBoard();
        }

    }
}
