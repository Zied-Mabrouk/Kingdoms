package org.toxichazard.kingdoms.Events.CustomEvents;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockPlaceEvent;

public class StructurePlaceEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    Player player;
    Block block;
    BlockPlaceEvent event;

    public StructurePlaceEvent(Block block,Player player,BlockPlaceEvent event)
    {
        this.block=block;
        this.player=player;
        this.event=event;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getBlock() {
        return block;
    }

    public BlockPlaceEvent getEvent() {
        return event;
    }

}
