package me.egomaniac.kitpvp.perks.perks;

import me.egomaniac.kitpvp.perks.Perk;

public class Taunt extends Perk {
    public Taunt() {
        this.id = "taunt";
        this.name = "Taunt";
        this.description = "Automatically taunt players in private messages when you kill them.";
        this.cost = 800; // Set a negative cost to make it unobtainable.
        this.tier = 2;
    }

    // Implement the functionality to prevent tracking of kill streak
}