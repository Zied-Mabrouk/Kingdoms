package org.toxichazard.kingdoms.Constants.Land.Structures;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.toxichazard.kingdoms.Constants.Land.Land;
import org.toxichazard.kingdoms.Utils.LandsUtil;
import org.toxichazard.kingdoms.main;

import java.util.ArrayList;
import java.util.List;

public class Structure {

    protected StructureType type;
    protected Block block;
    protected Material head;

    public Structure()
    {

    }

    public Structure(StructureType type, Block block)
    {
        this.type=type;
        this.block = block;
    }

    public StructureType getStructureType() {
        return type;
    }

    public Block getBlock() {
        return block;
    }

    public Location getLocation()
    {
        return block.getLocation();
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public void setStructureType(StructureType structureType) {
        this.type = structureType;
    }

    public void setInWorld(String kingdomName)
    {
        switch(type)
        {
            case NEXUS:
                block.setMetadata("Structure",new FixedMetadataValue(main.getPlugin(),kingdomName));
                break;
        }
    }

    public Structure transform()
    {
        switch (type)
        {
            case NEXUS:
                return (new Nexus(this));
        }
        return null;
    }

    public static boolean isStructure(Block block)
    {
        return(block.hasMetadata("Structure"));
    }
    public void toBlock(String kingdomName)
    {

    }

    public ItemStack toItem()
    {
        ItemStack item = new ItemStack(this.head);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        switch (this.getClass().getSimpleName())
        {
            case "Nexus":
                meta.setDisplayName(main.getKingdomConfig().getNexus().title);
                break;
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return (item);

    }
    public static boolean isStructureItem(ItemStack item)
    {
        String displayName = item.getItemMeta().getDisplayName();

        ItemStack itemStack = new ItemStack(item.getType());

        if(displayName.equals(itemStack.getItemMeta().getDisplayName()))
            return false;

        if(displayName.endsWith("Structure"))
            return true;

        return false;
    }

    public void Break(Player player)
    {
        Land land = main.getCLands().get(block.getLocation().getChunk().toString());
        ItemStack item = toItem();
        block.getWorld().dropItemNaturally(block.getLocation(),item);

        block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY,block.getLocation(),40,.5,.5,.5,0);
        block.getWorld().playSound(block.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE,2.0F,1.0F);
        land.setStructure(null);
        player.sendMessage(ChatColor.GREEN+"Structure broken !");
        LandsUtil.update(land.getChunk());

        block.removeMetadata(land.getKingdomName(), main.getPlugin());
        block.setType(Material.AIR);
    }


}
