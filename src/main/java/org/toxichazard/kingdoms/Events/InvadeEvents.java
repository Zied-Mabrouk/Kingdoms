package org.toxichazard.kingdoms.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.toxichazard.kingdoms.Constants.Kingdom.Champion;
import org.toxichazard.kingdoms.Events.CustomEvents.ChampionDamageEvent;
import org.toxichazard.kingdoms.main;

import java.util.concurrent.ThreadLocalRandom;

public class InvadeEvents implements Listener {

    @EventHandler
    public void onChampionDamage(ChampionDamageEvent championDamageEvent)
    {
        EntityDamageByEntityEvent event = championDamageEvent.getEvent();
        Player player = (Player) event.getEntity();
        Champion champion = main.getInvades().get(event.getDamager().getUniqueId());
        if(champion.getKingdom().getChampionHitsUpgrade().getLevel()==0)
            return;

        champion.setHit(champion.getHit() + 1);


        if(champion.getHit()==(7-champion.getKingdom().getChampionHitsUpgrade().getLevel())) {
            champion.setHit(0);
            player.setVelocity(champion.getEntity().getLocation().getDirection().multiply(3));
        }

        int disarmChances = champion.getKingdom().getChampionDisarmUpgrade().getLevel();

        if(disarmChances==0)
            return;

        if( ThreadLocalRandom.current().nextInt(0, 100)>disarmChances)
            return;

        int slot = player.getInventory().getHeldItemSlot();

        if(player.getInventory().getItem(slot)!=null){
            if (player.getInventory().getItem(slot).getType() != Material.AIR) {
                for (int i = 9; i < 36; i++) {
                    if (player.getInventory().getItem(i) == null) {
                        player.getInventory().setItem(i, player.getInventory().getItemInMainHand());
                        player.getInventory().setItem(slot, null);

                        break;
                    }
                }
            }
        }


    }

}
