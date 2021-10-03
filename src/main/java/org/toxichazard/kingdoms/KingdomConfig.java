package org.toxichazard.kingdoms;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.toxichazard.kingdoms.Settings.Structures.StructureSetting;
import org.toxichazard.kingdoms.Settings.Turrets.TurretSetting;
import org.toxichazard.kingdoms.Settings.Upgrades.UpgradeSetting;

import java.io.FileReader;
import java.io.IOException;

public class KingdomConfig{

    private TurretSetting flame;
    private TurretSetting healing;
    private TurretSetting arrow;

    private StructureSetting nexus;

    private UpgradeSetting gear;
    private UpgradeSetting damage;
    private UpgradeSetting health;
    private UpgradeSetting hits;
    private UpgradeSetting lava;
    private UpgradeSetting regeneration;
    private UpgradeSetting speed;


    public TurretSetting getFlame() {
        return flame;
    }

    public final TurretSetting getHealing() {
        return healing;
    }

    public final TurretSetting getArrow() {
        return arrow;
    }

    public StructureSetting getNexus() {
        return nexus;
    }

    public UpgradeSetting getGear() {
        return gear;
    }

    public UpgradeSetting getDamage() {
        return damage;
    }

    public UpgradeSetting getHealth() {
        return health;
    }

    public UpgradeSetting getHits() {
        return hits;
    }

    public UpgradeSetting getLava() {
        return lava;
    }

    public UpgradeSetting getRegeneration() {
        return regeneration;
    }

    public UpgradeSetting getSpeed() {
        return speed;
    }

    public KingdomConfig()
    {
        try{

            JSONParser jsonParser = new JSONParser();
            Object parsed = jsonParser.parse(new FileReader(main.file.getPath()));

            JSONObject jsonObject = (JSONObject) parsed;

            JSONObject turrets = (JSONObject) jsonObject.get("turrets");
            JSONObject arrowTurret = (JSONObject) turrets.get("arrow");
            JSONObject flameTurret = (JSONObject) turrets.get("flame");
            JSONObject healingTurret = (JSONObject) turrets.get("healing");

            JSONObject structures = (JSONObject) jsonObject.get("structures");
            JSONObject nexusStructure = (JSONObject) structures.get("nexus");

            JSONObject upgrades = (JSONObject) jsonObject.get("upgrades");
            JSONObject gearUpgrade = (JSONObject) upgrades.get("gear");
            JSONObject damageUpgrade = (JSONObject) upgrades.get("damage");
            JSONObject healthUpgrade = (JSONObject) upgrades.get("health");
            JSONObject hitsUpgrade = (JSONObject) upgrades.get("hits");
            JSONObject lavaUpgrade = (JSONObject) upgrades.get("lava");
            JSONObject regenerationUpgrade = (JSONObject) upgrades.get("regeneration");
            JSONObject speedUpgrade = (JSONObject) upgrades.get("speed");

            arrow =new TurretSetting(arrowTurret);
            flame =new TurretSetting(flameTurret);
            healing =new TurretSetting(healingTurret);

            nexus =new StructureSetting(nexusStructure);

            gear = new UpgradeSetting(gearUpgrade);
            damage = new UpgradeSetting(damageUpgrade);
            health = new UpgradeSetting(healthUpgrade);
            hits = new UpgradeSetting(hitsUpgrade);
            lava = new UpgradeSetting(lavaUpgrade);
            regeneration = new UpgradeSetting(regenerationUpgrade);
            speed = new UpgradeSetting(speedUpgrade);



        }catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }
    }
}
