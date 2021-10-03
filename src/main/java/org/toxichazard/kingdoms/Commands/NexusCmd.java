package org.toxichazard.kingdoms.Commands;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.toxichazard.kingdoms.Constants.Kingdom.Kingdom;
import org.toxichazard.kingdoms.Constants.Land.Land;
import org.toxichazard.kingdoms.Constants.Land.Structures.Nexus;
import org.toxichazard.kingdoms.Constants.Player.KPlayer;
import org.toxichazard.kingdoms.Constants.Player.Rank;
import org.toxichazard.kingdoms.Settings.Messages;
import org.toxichazard.kingdoms.Utils.Converter;
import org.toxichazard.kingdoms.Utils.LandsUtil;
import org.toxichazard.kingdoms.main;

public class NexusCmd {

    public static void command(Player player) {
        if(!main.getCKPlayers().containsKey(player.getUniqueId()))
        {
            player.sendMessage(Messages.Kingdom_Necessity);
            return;
        }
        Chunk chunk = player.getLocation().getChunk();
        if(!main.getCLands().containsKey(chunk.toString()))
        {
            player.sendMessage(Messages.Not_Claimed_Chunk);
            return;
        }
        KPlayer kPlayer = main.getCKPlayers().get(player.getUniqueId());
        Land land = main.getCLands().get(chunk.toString());

        if(!kPlayer.getRank().isHigherOrEqualTo(Rank.KING))
        {
            player.sendMessage(Messages.No_Permission);
            return;
        }
        if(!land.getKingdomName().equals(kPlayer.getKingdomName()))
        {
            player.sendMessage(Messages.Block_Place_In_Other_Kingdom);
            return;
        }

        Block block = player.getTargetBlock(null,5);

        if(block.getType()== Material.AIR)
        {
            player.sendMessage(Messages.Nexus_Air);
            return;
        }

        Kingdom kingdom = kPlayer.getKingdom();
        if(kingdom.getNexusLand()!= null)
        {
            Land nexusChunk = main.getCLands().get(kingdom.getNexusLand().toString());
            Block blockNexus = nexusChunk.getStructure().getBlock();
            blockNexus.removeMetadata("Structure",main.getPlugin());
            blockNexus.setType(Material.AIR);
            nexusChunk.getStructure().setStructureType(null);
            nexusChunk.getStructure().setBlock(null);
            LandsUtil.update(nexusChunk.getChunk());
        }
        Nexus nexus = new Nexus(block);
        kingdom.setNexusLand(Converter.toChunk(block.getChunk().toString()));
        nexus.toBlock(land.getKingdomName());
        land.setStructure(nexus);
        kingdom.setNeedsUpdate(true);
        LandsUtil.update(land.getChunk());
    }

}
