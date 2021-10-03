package org.toxichazard.kingdoms.Constants.Land.Structures;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.toxichazard.kingdoms.main;

public class Nexus extends Structure{
    public Nexus(Structure structure)
    {
        this.type=StructureType.NEXUS;
        this.block=structure.getBlock();
        this.head=main.getKingdomConfig().getNexus().head;
    }

    public Nexus()
    {
        this.head=main.getKingdomConfig().getNexus().head;
        this.type=StructureType.NEXUS;
    }
    public Nexus(Block block)
    {
        this.block=block;
        this.type=StructureType.NEXUS;
        this.head=main.getKingdomConfig().getNexus().head;
    }
    public void toBlock(String kingdomName)
    {
        block.setType(main.getKingdomConfig().getNexus().head);
        block.setMetadata("Structure",new FixedMetadataValue(main.getPlugin(),kingdomName));
    }
    public static boolean isNexus(Block block)
    {
        return(Structure.isStructure(block) && block.getType().equals(Material.BEACON));
    }
}
