package me.egomaniac.kitpvp.perks.perks;

import me.egomaniac.kitpvp.perks.Perk;

public class Hardline extends Perk {
    public Hardline() {
        this.id = "hardline";
        this.name = "Hardline";
        this.description = "Require 1 less kill for your killstreaks. \n&cNOTE: &7Resets your current killstreak when removed.";
        this.cost = 1000; // Set a negative cost to make it unobtainable.
        this.tier = 2;
    }

    // Implement the functionality to prevent tracking of kill streak
}