package me.egomaniac.kitpvp.events;

import me.egomaniac.kitpvp.Main;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class SpawnListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (Main.getInstance().spawnManager.getCuboid().isIn(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            if (Main.getInstance().spawnManager.getCuboid().isIn(player)) {
                if (event.getEntity() instanceof Arrow || event.getEntity() instanceof EnderPearl) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (Main.getInstance().spawnManager.getCuboid().isIn(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (Main.getInstance().profileManager.getProfile(player.getUniqueId()).isTeleporting()) {
            Location previousLocation = event.getFrom();
            Location newLocation = event.getTo();

            if (previousLocation.getBlockX() != newLocation.getBlockX() ||
                    previousLocation.getBlockY() != newLocation.getBlockY() ||
                    previousLocation.getBlockZ() != newLocation.getBlockZ()) {
                Main.getInstance().teleportManager.cancelTeleport(player);
            }
        }
    }
}