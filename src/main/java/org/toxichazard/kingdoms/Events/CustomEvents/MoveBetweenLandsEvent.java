package org.toxichazard.kingdoms.Events.CustomEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MoveBetweenLandsEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    Player player;
    String kingdomName;

    public MoveBetweenLandsEvent(String s,Player player)
    {
        this.player=player;
        this.kingdomName=s;
    }

    public Player getPlayer() {
        return player;
    }

    public String getKingdomName() {
        return kingdomName;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}
