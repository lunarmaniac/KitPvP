package me.egomaniac.kitpvp.events;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.commands.KitCommand;
import me.egomaniac.kitpvp.managers.ProfileManager;
import me.egomaniac.kitpvp.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerListener implements Listener {
    private static final int IMMUNITY_DURATION_SECONDS = 5;
    private static final int FALL_DAMAGE_DELAY_TICKS = 100; // 5 seconds (20 ticks per second)

    private final Map<UUID, Long> playerImmunityTimers;

    public PlayerListener() {
        this.playerImmunityTimers = new HashMap<>();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage("");
        Main.getInstance().playerManager.updatePermissions(player);

        if (!player.hasPlayedBefore()) {
            Main.getInstance().teleportManager.teleportSpawn(player);
        }

        player.sendMessage("");
        player.sendMessage(CC.translate("&fYou're now connected to our &3&lKitPvP &fserver"));
        player.sendMessage(CC.translate("&7&oThe map began on the 6th of July."));
        player.sendMessage("");

        Main.getInstance().profileManager.updateNameTag(player);
        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 2.0f, 1.0f);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");

        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        ProfileManager.Profile profile = Main.getInstance().profileManager.getProfile(playerId);
        if (Main.getInstance().combatManager.isCombat(player)) {
            event.getPlayer().setHealth(0);
        }
        Main.getInstance().profileManager.saveProfile(profile);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (Main.getInstance().spawnManager.getCuboid().isIn(player)) {
            playerImmunityTimers.remove(player.getUniqueId());
        } else {
            if (!playerImmunityTimers.containsKey(player.getUniqueId())) {
                    playerImmunityTimers.put(player.getUniqueId(), System.currentTimeMillis());
                if (hasNoItems(player)) {
                    if (player.getGameMode() != GameMode.CREATIVE) {
                        if (!Main.getInstance().sumoManager.isParticipant(player)) {
                            KitCommand kitCommand = new KitCommand();
                            kitCommand.giveKitPvP(player);
                        }
                    }
                }
            }
        }
    }

    private boolean hasNoItems(Player player) {
        ItemStack[] inventoryContents = player.getInventory().getContents();
        ItemStack[] armorContents = player.getInventory().getArmorContents();

        for (ItemStack item : inventoryContents) {
            if (item != null && item.getType() != Material.AIR) {
                return false;
            }
        }

        for (ItemStack armor : armorContents) {
            if (armor != null && armor.getType() != Material.AIR) {
                return false;
            }
        }

        return true;
    }




        @EventHandler
        public void onFallDamage(EntityDamageEvent e) {
            if (!(e.getEntity() instanceof Player)) {
                return;
            }

            Player player = (Player) e.getEntity();
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (isFallDamageImmune(player)) {
                    // The player is immune to fall damage, cancel the event
                    e.setCancelled(true);
                }
            }
    }

    private boolean isFallDamageImmune(Player player) {
        Long immunityStart = playerImmunityTimers.get(player.getUniqueId());
        return immunityStart != null && System.currentTimeMillis() - immunityStart < IMMUNITY_DURATION_SECONDS * 1000;
    }

    private void startFallDamageDelay(Player player) {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            playerImmunityTimers.remove(player.getUniqueId());
        }, FALL_DAMAGE_DELAY_TICKS);
    }

    @EventHandler
    public void onVoidDamage(final EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
            e.setDamage(100000);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();

        if (!(player.getGameMode() == GameMode.CREATIVE)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();

        if (!(player.getGameMode() == GameMode.CREATIVE)) {
            e.setCancelled(true);
        }
    }
}
