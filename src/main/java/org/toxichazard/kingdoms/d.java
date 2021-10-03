package org.toxichazard.kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class d {
    public static void display(String s)
    {
        Bukkit.broadcastMessage(s);
    }
    public static void display(Location s)
    {
        Bukkit.broadcastMessage("X:"+s.getX());
        Bukkit.broadcastMessage("Y:"+s.getY());
        Bukkit.broadcastMessage("Z:"+s.getZ());
    }
    public static void display(Block B)
    {
        Location s = B.getLocation();
        Bukkit.broadcastMessage("X:"+s.getX());
        Bukkit.broadcastMessage("Y:"+s.getY());
        Bukkit.broadcastMessage("Z:"+s.getZ());
        Bukkit.broadcastMessage("Type:"+B.getType());
    }
}
