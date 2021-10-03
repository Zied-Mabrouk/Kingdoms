package org.toxichazard.kingdoms.Constants.Land.Structures;

import org.bukkit.Material;

public enum StructureType {
    NEXUS,
    POWERCELL,
    ARSENAL;

    @Override
    public String toString() {
        return super.toString();
    }

    public static StructureType fromString(String val)
    {
        switch (val)
        {
            case "NEXUS":
                return NEXUS;
            case "POWERCELL":
                return POWERCELL;
            case "ARSENAL":
                return ARSENAL;

        }
        return null;
    }

    public static StructureType fromMaterial(Material val)
    {
        switch (val)
        {
            case BEACON:
                return NEXUS;

        }
        return null;
    }

}
