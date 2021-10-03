package org.toxichazard.kingdoms.Events.CustomEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.toxichazard.kingdoms.Constants.Land.Land;

public class BlockBreakInLandEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    BlockBreakEvent event;
    Land land;

    public BlockBreakInLandEvent(BlockBreakEvent event, Land land)
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

    public BlockBreakEvent getEvent() {
        return event;
    }

    public Land getLand() {
        return land;
    }
}
