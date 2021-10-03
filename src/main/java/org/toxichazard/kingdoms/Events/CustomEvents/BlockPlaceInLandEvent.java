package org.toxichazard.kingdoms.Events.CustomEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockPlaceEvent;
import org.toxichazard.kingdoms.Constants.Land.Land;

public class BlockPlaceInLandEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    BlockPlaceEvent event;
    Land land;

    public BlockPlaceInLandEvent(BlockPlaceEvent event, Land land)
    {
        this.event=event;
        this.land=land;
    }


    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    public BlockPlaceEvent getEvent() {
        return event;
    }

    public Land getLand() {
        return land;
    }

}
