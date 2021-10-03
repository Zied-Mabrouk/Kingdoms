package org.toxichazard.kingdoms.Constants.Kingdom.Upgrades;

public enum NexusUpgradeType {
    REGENERATION(0),
    TNT(1)
    ;

    private int index;
    NexusUpgradeType(int index) {
        this.index=index;
    }


    public int getIndex() {
        return index;
    }
}
