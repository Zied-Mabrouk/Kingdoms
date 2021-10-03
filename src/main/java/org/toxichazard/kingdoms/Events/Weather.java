package org.toxichazard.kingdoms.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class Weather implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event)
    {
        event.setCancelled(true);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event)
    {
        event.setCancelled(true);
        if(event.getFoodLevel()!=20){
            if(event.getEntity() instanceof Player)
            {
                Player player = (Player) event.getEntity();
                player.setFoodLevel(20);
            }
        }
    }

}
