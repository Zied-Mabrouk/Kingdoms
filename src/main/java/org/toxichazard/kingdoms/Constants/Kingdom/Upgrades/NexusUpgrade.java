package org.toxichazard.kingdoms.Constants.Kingdom.Upgrades;

public class NexusUpgrade {
    private int level;
    private NexusUpgradeType type;


    public NexusUpgrade(NexusUpgradeType type)
    {
        this.type=type;
    }
    public NexusUpgrade(NexusUpgradeType type,int level)
    {
        this.type=type;
        this.level=level;

    }

    public int getLevel() {
        return level;
    }

    public NexusUpgradeType getType() {
        return type;
    }

    public void setType(NexusUpgradeType type) {
        this.type = type;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
