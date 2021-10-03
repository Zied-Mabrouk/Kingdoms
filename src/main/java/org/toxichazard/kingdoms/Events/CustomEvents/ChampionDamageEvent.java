package org.toxichazard.kingdoms.Events.CustomEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ChampionDamageEvent extends Event {
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList()
    {
        return handlers;
    }
    private static final HandlerList handlers = new HandlerList();

    EntityDamageByEntityEvent event;

    public EntityDamageByEntityEvent getEvent() {
        return event;
    }

    public ChampionDamageEvent(EntityDamageByEntityEvent event) {
        this.event = event;
    }
}
