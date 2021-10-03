package org.toxichazard.kingdoms.Commands;

import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.toxichazard.kingdoms.Constants.Kingdom.Champion;
import org.toxichazard.kingdoms.Constants.Land.Land;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.Constants.Player.Rank;
import org.toxichazard.kingdoms.Settings.Messages;
import org.toxichazard.kingdoms.main;

public class Invade {

    public static void command(Player player)
    {
        Chunk chunk = player.getLocation().getChunk();

        if(!main.getCKPlayers().containsKey(player.getUniqueId()))
        {
            player.sendMessage(Messages.Kingdom_Necessity);
            return ;
        }

        if(!main.getCLands().containsKey(chunk.toString()))
        {
            player.sendMessage(Messages.Not_Claimed_Chunk);
            return ;
        }

        KPlayer kPlayer = main.getCKPlayers().get(player.getUniqueId());
        if(kPlayer.getKingdomName().equals(main.getCLands().get(chunk.toString()).getKingdomName()))
        {
            player.sendMessage(Messages.Invade_Own_Land);
            return ;
        }
        if(!kPlayer.getRank().isHigherOrEqualTo(Rank.KNIGHT))
        {
            player.sendMessage(Messages.No_Permission);
            return;
        }

        Zombie zombie = (Zombie)player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
        Champion champion = new Champion(zombie,kPlayer,chunk);
        Land land = main.getCLands().get(chunk.toString());

        land.invade(kPlayer,champion);
        main.getInvades().put(zombie.getUniqueId(),champion);
        kPlayer.setChampionInvading(zombie.getUniqueId());
        //player.setGameMode(GameMode.SURVIVAL);
    }


}
