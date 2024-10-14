package me.egomaniac.kitpvp.perks.perks;

import me.egomaniac.kitpvp.perks.Perk;

public class QuickFix extends Perk {
    public QuickFix() {
        this.id = "quick_fix";
        this.name = "Quick Fix";
        this.description = "Gain resistance after slaying an opponent.";
        this.cost = 350; // Set a negative cost to make it unobtainable.
        this.tier = 1;
    }

    // Implement the functionality to prevent tracking of kill streak
}