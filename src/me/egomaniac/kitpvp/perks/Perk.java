package me.egomaniac.kitpvp.perks;

import lombok.Getter;

@Getter
public class Perk {
    protected String id;
    protected String name;
    protected String description;
    protected int cost;
    protected int tier;
    protected String tierColor; // Added tierColor field

    protected static final String tier1Color = "&b";
    protected static final String tier2Color = "&c";
    protected static final String tier3Color = "&5";

    public Perk() {
        // Default constructor with no parameters
    }

    public Perk(String id, String name, String description, int cost, int tier) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.tier = tier;
    }

        // Set tierColor based on the tier value
    public static String getTierColorByTier(int tier) {
        switch (tier) {
            case 1:
                return tier1Color;
            case 2:
                return tier2Color;
            case 3:
                return tier3Color;
            default:
                return "&7"; // Default to gray color if tier is not 1, 2, or 3
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Perk otherPerk = (Perk) obj;
        return id.equals(otherPerk.id);
    }

}