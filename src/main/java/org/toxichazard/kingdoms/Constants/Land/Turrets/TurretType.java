package org.toxichazard.kingdoms.Constants.Land.Turrets;

public enum TurretType {
    ARROW,
    FLAME,
    HEALING;


    public static TurretType FromString(String type)
    {
        type = type.toUpperCase();
        switch (type)
        {
            case "ARROW":
                return ARROW;
            case "FLAME":
                return FLAME;
            case "HEALING":
                return HEALING;
        }
        return null;
    }
}
