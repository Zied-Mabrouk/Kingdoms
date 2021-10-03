package org.toxichazard.kingdoms;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class KingdomsTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if(args.length==1)
        {
            ArrayList<String> arguments = new ArrayList<>();
            for(String cmd : main.cmds)
            {
                if(cmd.startsWith(args[0]))
                    arguments.add(cmd);
            }
            return arguments;
        }
        return null;

    }

}
