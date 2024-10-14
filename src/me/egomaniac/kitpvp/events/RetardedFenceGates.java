package me.egomaniac.kitpvp.events;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class RetardedFenceGates implements Listener {

    private boolean gateOpenPermission = false;
    private int gateClickCounter = 0;
    private static final long GATE_OPEN_INTERVAL = 2 * 60 * 1000; // 2 minutes
    private static final long GATE_OPEN_DURATION = 30 * 1000; // 30 seconds
    private static final int GATE_CLICK_THRESHOLD = 5; // Threshold to send the message

    public RetardedFenceGates() {
        // Schedule the gate-open permission periodically if there are more than 1 players online
            new BukkitRunnable() {
                @Override
                public void run() {
                    // fence gates open
                    gateOpenPermission = true;

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            // fence gates closed
                            gateOpenPermission = false;
                        }
                    }.runTaskLater(Main.getInstance(), GATE_OPEN_DURATION / 50); // Convert milliseconds to ticks (1 tick = 50ms)
                }
            }.runTaskTimer(Main.getInstance(), GATE_OPEN_INTERVAL / 50, GATE_OPEN_INTERVAL / 50); // Convert milliseconds to ticks (1 tick = 50ms)
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            if (!Main.getInstance().spawnManager.getCuboid().isIn(player)) {
                Block clickedBlock = event.getClickedBlock();
                Material blockMaterial = clickedBlock.getType();

                // Check if the player interacted with a fence gate
                if (blockMaterial == Material.FENCE_GATE ||
                        blockMaterial == Material.SPRUCE_FENCE_GATE ||
                        blockMaterial == Material.BIRCH_FENCE_GATE ||
                        blockMaterial == Material.JUNGLE_FENCE_GATE ||
                        blockMaterial == Material.ACACIA_FENCE_GATE ||
                        blockMaterial == Material.DARK_OAK_FENCE_GATE) {

                    // Check if the player has the gate-open permission
                    if (!gateOpenPermission) {
                        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                            event.setCancelled(true);

                            // Increment the click counter and send the message every 5 clicks
                            gateClickCounter++;
                            if (gateClickCounter % GATE_CLICK_THRESHOLD == 0) {
                                player.sendMessage(CC.translate("&cYou can't change fence gates state right now."));
                            }
                        }
                    }
                }
            }
        }
    }

}
