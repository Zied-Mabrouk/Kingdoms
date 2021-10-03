package org.toxichazard.kingdoms.Events.CustomEvents;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;

public class StructureBreakEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    Player player;
    Block block;
    BlockBreakEvent event;

    public StructureBreakEvent(Block block, Player player,BlockBreakEvent event) {
        this.block=block;
        this.player=player;
        this.event=event;
    }


    public Block getBlock() {
        return block;
    }

    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    public BlockBreakEvent getEvent() {
        return event;
    }
}
