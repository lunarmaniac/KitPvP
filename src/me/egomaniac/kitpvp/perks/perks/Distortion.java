package me.egomaniac.kitpvp.perks.perks;

import me.egomaniac.kitpvp.perks.Perk;

public class Distortion extends Perk {
    public Distortion() {
        this.id = "distortion";
        this.name = "Distortion";
        this.description = "Attacking players has a 5% chance of blinding your enemies.";
        this.cost = 750;
        this.tier = 2;
    }

    // Implement the functionality for the 5% chance of blinding enemies
}