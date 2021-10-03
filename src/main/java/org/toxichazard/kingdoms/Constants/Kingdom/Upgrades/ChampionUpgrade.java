package org.toxichazard.kingdoms.Constants.Kingdom.Upgrades;

public class ChampionUpgrade {
    private int level;
    private ChampionUpgradeType type;

    public ChampionUpgrade(ChampionUpgradeType type)
    {
        this.type=type;
    }
    public ChampionUpgrade(ChampionUpgradeType type,int level)
    {
        this.type=type;
        this.level=level;

    }

    public int getLevel() {
        return level;
    }

    public ChampionUpgradeType getType() {
        return type;
    }

    public void setType(ChampionUpgradeType type) {
        this.type = type;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public void increaseLevel( ) {
        this.level ++;
    }
    public void increaseLevel(int level) {
        this.level += level;
    }

}
