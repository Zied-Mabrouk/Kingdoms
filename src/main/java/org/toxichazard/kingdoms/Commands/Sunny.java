package org.toxichazard.kingdoms.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

public class Sunny implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player)
        {
            Player player = (Player) sender;

            player.getWorld().setTime(1000);
            for(Entity e :player.getWorld().getEntities())
            {
                if(!e.getType().equals(EntityType.PLAYER))
                {
                    if(e instanceof Damageable)
                    {
                        Damageable damageable = (Damageable)e;
                        damageable.setHealth(0);
                    }
                    else if(e instanceof Item)
                    {
                        Item item = (Item)e;
                        item.remove();
                    }
                }
            }

            for(Entity e :player.getWorld().getEntities())
            {
                if(e instanceof Item)
                {
                    Item item = (Item)e;
                    item.remove();
                }
            }
        }
        return false;
    }
}
