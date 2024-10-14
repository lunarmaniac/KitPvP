package me.egomaniac.kitpvp.tasks;

import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnTeleportTask extends BukkitRunnable {
    private final Player player;

    public SpawnTeleportTask(Player player) {
        this.player = player;
    }


    @Override
    public void run() {

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
        player.sendMessage(CC.GREEN + "You have been teleported to spawn.");
        Main.getInstance().profileManager.getProfile(player.getUniqueId()).setTeleporting(false);

    }
}