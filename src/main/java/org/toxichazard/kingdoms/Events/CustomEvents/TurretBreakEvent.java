package org.toxichazard.kingdoms.Events.CustomEvents;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TurretBreakEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    Player player;
    Block block;

    public TurretBreakEvent(Block block,Player player)
    {
        this.block=block;
        this.player=player;
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
}
