package me.egomaniac.kitpvp.managers;

import lombok.Getter;
import lombok.Setter;
import me.egomaniac.kitpvp.utils.cuboid.Cuboid;
import me.egomaniac.kitpvp.utils.cuboid.CustomLocation;
import me.egomaniac.kitpvp.Main;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by Elb1to
 * Project: SoupPvP
 * Date: 7/18/2021 @ 12:06 AM
 */
@Getter @Setter
public class SpawnManager {

    private CustomLocation spawnLocation;
    private CustomLocation safezoneMin;
    private CustomLocation safezoneMax;

    private Cuboid cuboid;

    public SpawnManager() {
        this.loadConfig();
    }

    private void loadConfig() {
        final FileConfiguration config = Main.getInstance().getConfig();

        if (config.contains("SERVER.SPAWN.LOCATION")) {
            try {
                this.spawnLocation = CustomLocation.stringToLocation(config.getString("SERVER.SPAWN.LOCATION"));
                this.safezoneMin = CustomLocation.stringToLocation(config.getString("SERVER.SPAWN.SAFEZONE-MIN"));
                this.safezoneMax = CustomLocation.stringToLocation(config.getString("SERVER.SPAWN.SAFEZONE-MAX"));
                this.cuboid = new Cuboid(safezoneMin.toBukkitLocation(), safezoneMax.toBukkitLocation());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}