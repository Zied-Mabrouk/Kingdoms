package org.toxichazard.kingdoms.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.toxichazard.kingdoms.Constants.Kingdom.Kingdom;
import org.toxichazard.kingdoms.Constants.Land.Land;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.Constants.Player.Rank;
import org.toxichazard.kingdoms.Settings.Messages;
import org.toxichazard.kingdoms.Utils.LandsUtil;
import org.toxichazard.kingdoms.main;

public class Claim {

    public static void command(Player player) {
        Chunk chunk = player.getLocation().getChunk();

        if(!main.getCKPlayers().containsKey(player.getUniqueId())){
            player.sendMessage(Messages.Kingdom_Necessity);
            return ;
        }
        KPlayer kPlayer = main.getCKPlayers().get(player.getUniqueId());
        if(!kPlayer.getRank().isHigherOrEqualTo(Rank.KNIGHT))
        {
            player.sendMessage(Messages.No_Permission);
            return;
        }
        if(main.getCLands().containsKey(chunk.toString()))
        {
            String kingdomName = main.getCLands().get(chunk.toString()).getKingdomName();
            if(kingdomName.equals(kPlayer.getKingdomName()))
               player.sendMessage(Messages.Already_Yours);
            else
                player.sendMessage(Messages.Already_Claimed_By(kingdomName));
            return ;
        }

        Kingdom kingdom = main.getCKingdoms().get(kPlayer.getKingdomName());
        if(kingdom.getRP()<10)
        {
            player.sendMessage(Messages.Not_Enough_RP);
            return ;
        }

        if(kingdom.getClaimLimit() <= kingdom.getNumberOfLands())
        {
            player.sendMessage(Messages.Claim_Limit);
            return;
        }


        LandsUtil.add(kPlayer,chunk);
        Land land = new Land(chunk);
        main.getCLands().put(chunk.toString(),land);
        kingdom.addOne();
        kingdom.addRP(-10);
        kingdom.updateMembersScoreBoard();
        kingdom.setNeedsUpdate(true);
        player.sendMessage(Messages.Claimed_Successfully);


    }

}
