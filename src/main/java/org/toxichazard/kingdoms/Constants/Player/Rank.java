package org.toxichazard.kingdoms.Constants.Player;

import org.bukkit.ChatColor;

public enum Rank {
    KING(0),
    NOBLE(1),
    KNIGHT(2),
    CITIZEN(3);

    private int rank;


    Rank(int i) {
        rank=i;
    }

    public int getRank() {
        return rank;
    }
    public boolean isHigherOrEqualTo(Rank target) {
        return (this.rank <= target.rank);
    }

    public boolean isHigherThan(Rank target) {
        return (this.rank < target.rank);
    }
    public static ChatColor colorByRank(Rank rank) {
        switch (rank) {
            case CITIZEN:
                return ChatColor.WHITE;
            case KNIGHT:
                return ChatColor.BLUE;
            case NOBLE:
                return ChatColor.GOLD;
            case KING:
                return ChatColor.RED;
        }
        return ChatColor.WHITE;
    }

    public String toString() {

        return (this.name());
    }

    public static Rank fromString(String val)
    {
        if (val.equalsIgnoreCase("CITIZEN"))
            return CITIZEN;
        if (val.equalsIgnoreCase("NOBLE"))
            return NOBLE;
        if (val.equalsIgnoreCase("KNIGHT"))
            return KNIGHT;
        if (val.equalsIgnoreCase("KING"))
            return KING;

        return CITIZEN;
    }
}
