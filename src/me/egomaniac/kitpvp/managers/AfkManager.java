package me.egomaniac.kitpvp.managers;

import lombok.Getter;
import lombok.Setter;
import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.cuboid.Cuboid;
import me.egomaniac.kitpvp.utils.cuboid.CustomLocation;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AfkManager {

    private CustomLocation safezoneMin;
    private CustomLocation safezoneMax;
    private Cuboid cuboid;

    private final List<UUID> afkPlayers = new ArrayList<>(); // List to track AFK players

    public AfkManager() {
        this.loadConfig();
    }

    private void loadConfig() {
        final FileConfiguration config = Main.getInstance().getConfig();

        if (config.contains("SERVER.AFK")) {
            try {
                this.safezoneMin = CustomLocation.stringToLocation(config.getString("SERVER.AFK.SAFEZONE-MIN"));
                this.safezoneMax = CustomLocation.stringToLocation(config.getString("SERVER.AFK.SAFEZONE-MAX"));
                this.cuboid = new Cuboid(safezoneMin.toBukkitLocation(), safezoneMax.toBukkitLocation());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Methods to manage AFK players
    public void addAfkPlayer(UUID playerId) {
        if (!afkPlayers.contains(playerId)) {
            afkPlayers.add(playerId);
        }
    }

    public void removeAfkPlayer(UUID playerId) {
        afkPlayers.remove(playerId);
    }

    public boolean isPlayerAfk(UUID playerId) {
        return afkPlayers.contains(playerId);
    }
}
