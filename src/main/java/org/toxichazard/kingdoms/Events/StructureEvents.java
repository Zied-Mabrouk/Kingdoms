package org.toxichazard.kingdoms.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.toxichazard.kingdoms.Constants.Land.Land;
import org.toxichazard.kingdoms.Constants.Land.Structures.Structure;
import org.toxichazard.kingdoms.Constants.Land.Structures.StructureType;
import org.toxichazard.kingdoms.Events.CustomEvents.StructureBreakEvent;
import org.toxichazard.kingdoms.Settings.Messages;
import org.toxichazard.kingdoms.Utils.Converter;
import org.toxichazard.kingdoms.main;

public class StructureEvents implements Listener {

    @EventHandler
    public void onStructureBreak(StructureBreakEvent event)
    {
        Land land = main.getCLands().get(event.getBlock().getChunk().toString());
        Structure structure = Converter.toStructure(event.getBlock(),land);
        structure.transform();

        if(!structure.getStructureType().equals(StructureType.NEXUS))
            structure.Break(event.getPlayer());
        else
        {
           event.getEvent().setCancelled(true);
           event.getPlayer().sendMessage(Messages.Nexus_Break);
        }


    }

}
