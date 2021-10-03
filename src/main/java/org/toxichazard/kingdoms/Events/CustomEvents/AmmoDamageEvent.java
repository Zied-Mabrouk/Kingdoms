package org.toxichazard.kingdoms.Events.CustomEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.toxichazard.kingdoms.Constants.Land.Turrets.Ammo;

public class AmmoDamageEvent extends Event {
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList()
    {
        return handlers;
    }
    private static final HandlerList handlers = new HandlerList();
    Ammo ammo;
    EntityDamageByEntityEvent event;

    public AmmoDamageEvent(Ammo ammo,EntityDamageByEntityEvent event)
    {
        this.ammo=ammo;
        this.event=event;
    }

    public Ammo getAmmo() {
        return ammo;
    }

    public EntityDamageByEntityEvent getEvent() {
        return event;
    }


}
