package me.egomaniac.kitpvp.events;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.inventory.ItemStack;

public class StaffModeListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (Main.getInstance().staffManager.isInStaffMode(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item != null && item.getType() == Material.COMPASS && item.getItemMeta() != null &&
                item.getItemMeta().getDisplayName().equals("§bRandom Teleport")) {

            if (!player.hasPermission("kitpvp.staff")) {
                event.setCancelled(true);
                return;
            }

            event.setCancelled(true);
            teleportToRandomPlayer(player);
        }
    }

    private void teleportToRandomPlayer(Player player) {
        List<Player> targetPlayers = Bukkit.getOnlinePlayers().stream()
                .filter(p -> !p.hasPermission("kitpvp.staff"))
                .collect(Collectors.toList());

        if (!targetPlayers.isEmpty()) {
            Player target = targetPlayers.get(new Random().nextInt(targetPlayers.size()));
            player.teleport(target.getLocation());
            player.sendMessage(CC.translate("&aTeleported to " + target.getName()));
        } else {
            player.sendMessage(CC.translate("&cNo available players found."));

        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (Main.getInstance().staffManager.isInStaffMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (Main.getInstance().staffManager.isInStaffMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if (Main.getInstance().staffManager.isInStaffMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (Main.getInstance().staffManager.isInStaffMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (Main.getInstance().staffManager.isInStaffMode(player)) {
            event.setCancelled(true);
        }
    }
}
