package org.toxichazard.kingdoms.Constants.Kingdom.Upgrades;

public enum ChampionUpgradeType {
    HEALTH(0),
    SPEED(1),
    DAMAGE(2),
    GEAR(3),
    LAVA(4),
    HITS(5),
    DISARM(6)
    ;

    private int index;
    ChampionUpgradeType(int index) {
        this.index=index;
    }


    public int getIndex() {
        return index;
    }
}
