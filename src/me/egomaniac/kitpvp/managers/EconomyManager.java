package me.egomaniac.kitpvp.managers;

import me.egomaniac.kitpvp.Main;

import java.util.UUID;

public class EconomyManager {

    public int getCredits(UUID playerId) {
        ProfileManager.Profile profile = Main.getInstance().profileManager.getProfile(playerId);
        return profile.getCredits();
    }

    public void setCredits(UUID playerId, int credits) {
        ProfileManager.Profile profile = Main.getInstance().profileManager.getProfile(playerId);
        profile.setCredits(credits);
        Main.getInstance().profileManager.saveProfile(profile);
    }
}