package org.toxichazard.kingdoms.Commands;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.toxichazard.kingdoms.Constants.Kingdom.Kingdom;
import org.toxichazard.kingdoms.Constants.Land.Land;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.Settings.Messages;
import org.toxichazard.kingdoms.Utils.LandsUtil;
import org.toxichazard.kingdoms.main;

public class Unclaim {

    public static void command(Player player){
        Chunk chunk = player.getLocation().getChunk();
        if (!main.getCLands().containsKey(chunk.toString()))
        {
            player.sendMessage(Messages.Not_Claimed_Chunk);
            return ;
        }

        if(!main.getCKPlayers().containsKey(player.getUniqueId()))
        {
            player.sendMessage(Messages.Kingdom_Necessity);
            return ;
        }

        KPlayer kPlayer = main.getCKPlayers().get(player.getUniqueId());
        Land land = main.getCLands().get(chunk.toString());

        if(!land.getKingdomName().equals(kPlayer.getKingdomName()))
        {
            player.sendMessage(Messages.Others_Kingdom_Interact);
            return ;
        }
        Kingdom kingdom = land.getKingdom();
        if(kingdom.getNexusLand().equals(land.getChunk()))
            kingdom.setNexusLand(null);
        kingdom.removeOne();
        LandsUtil.delete(chunk);
        main.getCLands().remove(chunk.toString());
        player.sendMessage(Messages.Unclaimed_Successfully);
    }

}
