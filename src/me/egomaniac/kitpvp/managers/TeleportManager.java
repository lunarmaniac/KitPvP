package me.egomaniac.kitpvp.managers;

import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportManager {

    private final Map<UUID, BukkitRunnable> teleportRunnables;

    public TeleportManager() {
        this.teleportRunnables = new HashMap<>();
    }

    public void teleportSpawn(Player player) {

        String spawnLocation = Main.getInstance().getConfig().getString("SERVER.SPAWN.LOCATION");

        String[] locationData = spawnLocation.split(", ");
        if (locationData.length != 5) {
            player.sendMessage(CC.RED + "Invalid spawn location in the configuration.");
            return;
        }

        double x = Double.parseDouble(locationData[0]);
        double y = Double.parseDouble(locationData[1]);
        double z = Double.parseDouble(locationData[2]);
        float yaw = Float.parseFloat(locationData[3]);
        float pitch = Float.parseFloat(locationData[4]);

        Location spawn = new Location(player.getWorld(), x, y, z, yaw, pitch);
        player.teleport(spawn);
    }

    public void teleportSpawnAfterDelay(Player player, int delaySeconds) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                teleportSpawn(player);
                player.sendMessage(CC.translate("&aYou have been teleported to spawn."));
                Main.getInstance().profileManager.getProfile(player.getUniqueId()).setTeleporting(false);
                teleportRunnables.remove(player.getUniqueId());
            }
        };

        runnable.runTaskLater(Main.getInstance(), delaySeconds * 20L);
        teleportRunnables.put(player.getUniqueId(), runnable);
    }

    public void cancelTeleport(Player player) {
        BukkitRunnable runnable = teleportRunnables.remove(player.getUniqueId());
        if (runnable != null) {
            runnable.cancel();
            player.sendMessage(CC.translate("&cYour teleportation has been canceled due to movement."));
            Main.getInstance().profileManager.getProfile(player.getUniqueId()).setTeleporting(false);
        }
    }


    public void teleportSumoPositions(Player player1, Player player2) {
        String pos1Location = Main.getInstance().getConfig().getString("SERVER.SUMO.POS1");

        String[] locationDataPos1 = pos1Location.split(", ");

        double pos1x = Double.parseDouble(locationDataPos1[0]);
        double pos1y = Double.parseDouble(locationDataPos1[1]);
        double pos1z = Double.parseDouble(locationDataPos1[2]);
        float pos1yaw = Float.parseFloat(locationDataPos1[3]);
        float pos1pitch = Float.parseFloat(locationDataPos1[4]);

        String pos2Location = Main.getInstance().getConfig().getString("SERVER.SUMO.POS2");

        String[] locationDataPos2 = pos2Location.split(", ");

        double pos2x = Double.parseDouble(locationDataPos2[0]);
        double pos2y = Double.parseDouble(locationDataPos2[1]);
        double pos2z = Double.parseDouble(locationDataPos2[2]);
        float pos2yaw = Float.parseFloat(locationDataPos2[3]);
        float pos2pitch = Float.parseFloat(locationDataPos2[4]);

        Location pos1 = new Location(Bukkit.getWorld("world"), pos1x, pos1y, pos1z, pos1yaw, pos1pitch);
        Location pos2 = new Location(Bukkit.getWorld("world"), pos2x, pos2y, pos2z, pos2yaw, pos2pitch);
        player1.teleport(pos1);
        player2.teleport(pos2);
    }
}
