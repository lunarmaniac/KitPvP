package me.egomaniac.kitpvp.perks.perks;

import me.egomaniac.kitpvp.perks.Perk;

public class SecondChance extends Perk {
    public SecondChance() {
        this.id = "secondchance";
        this.name = "Second chance";
        this.description = "Chance to be saved from death by receiving bonus hearts.";
        this.cost = 2000;
        this.tier = 3;
    }

    // Implement the functionality to prevent tracking of kill streak
}