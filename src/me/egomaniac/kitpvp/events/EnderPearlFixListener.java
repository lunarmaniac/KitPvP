package me.egomaniac.kitpvp.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashSet;

public class EnderPearlFixListener implements Listener {
    private final HashSet<Material> blockedPearlTypes;

    public EnderPearlFixListener() {
        blockedPearlTypes = new HashSet<>();
        blockedPearlTypes.add(Material.ACACIA_FENCE_GATE);
        blockedPearlTypes.add(Material.BIRCH_FENCE_GATE);
        blockedPearlTypes.add(Material.DARK_OAK_FENCE_GATE);
        blockedPearlTypes.add(Material.JUNGLE_FENCE_GATE);
        blockedPearlTypes.add(Material.FENCE_GATE);
        blockedPearlTypes.add(Material.SPRUCE_FENCE_GATE);
        blockedPearlTypes.add(Material.GLASS);
        blockedPearlTypes.add(Material.WOOL);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.hasItem() && event.getItem().getType() == Material.ENDER_PEARL) {
            final Block block = event.getClickedBlock();
            if (block.getType().isSolid() && !(block.getState() instanceof InventoryHolder)) {
                event.setCancelled(true);
                final Player player = event.getPlayer();
                player.setItemInHand(event.getItem());
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPearlClip(final PlayerTeleportEvent event) {
        if (event.getCause() == TeleportCause.ENDER_PEARL) {
            final Location to = event.getTo();

            if (blockedPearlTypes.contains(to.getBlock().getType())) {
                // event.getPlayer().sendMessage("enderpearl glitch detected " + to.getBlock().getType());
                event.setCancelled(true);
                return;
            }

            to.setX(to.getBlockX() + 1);
            to.setZ(to.getBlockZ() + 1);
            event.setTo(to);
        }
    }
}