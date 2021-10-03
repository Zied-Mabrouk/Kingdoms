package org.toxichazard.kingdoms.Commands;

import org.bukkit.entity.Player;
import org.toxichazard.kingdoms.Constants.Land.Turrets.Flame;
import org.toxichazard.kingdoms.Constants.Land.Turrets.Turret;
import org.toxichazard.kingdoms.Constants.Land.Turrets.TurretType;

public class getTurret {

    public static void command(Player player, String[] args) {

        if(args.length<1)
            return;

        if(args.length==1) {

            Turret turret = new Turret(player.getLocation().getBlock());

            Flame t = new Flame(turret);

            player.getInventory().addItem(t.toItem());
        }
        else
        {
            Turret turret = new Turret(player.getLocation().getBlock());
            turret.setType(TurretType.FromString(args[1]));

            turret = turret.transform();

            player.getInventory().addItem(turret.toItem());
        }
    }


}
