package me.egomaniac.kitpvp.perks.perks;

import me.egomaniac.kitpvp.perks.Perk;

public class Revenge extends Perk {
    public Revenge() {
        this.id = "revenge";
        this.name = "Revenge";
        this.description = "Gain triple your normal credits for killing a player who just killed you.";
        this.cost = 800; // Set a negative cost to make it unobtainable.
        this.tier = 1;
    }

    // Implement the functionality to prevent tracking of kill streak
}