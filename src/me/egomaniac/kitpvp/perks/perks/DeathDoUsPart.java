package me.egomaniac.kitpvp.perks.perks;

import me.egomaniac.kitpvp.perks.Perk;

public class DeathDoUsPart extends Perk {
    public DeathDoUsPart() {
        this.id = "deathdouspart";
        this.name = "Death Do Us Part";
        this.description = "Have a chance to spawn a random debuff potion at your death point.";
        this.cost = 500; // Set a negative cost to make it unobtainable.
        this.tier = 2;
    }

    // Implement the functionality to prevent tracking of kill streak
}