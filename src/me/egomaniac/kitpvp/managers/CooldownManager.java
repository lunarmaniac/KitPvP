package me.egomaniac.kitpvp.managers;

import lombok.Getter;
import me.egomaniac.kitpvp.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class CooldownManager {

    // Cooldown sets for different items
    private final Map<UUID, Integer> enderpearlCooldown = new HashMap<>();
    private final Map<UUID, Integer> notchAppleCooldown = new HashMap<>();

    // Start or reset a player's cooldown for the specified type
    public void setCooldownTime(Player player, CooldownType type, int time) {
        UUID playerId = player.getUniqueId();
        if (type == CooldownType.ENDERPEARL) {
            enderpearlCooldown.put(playerId, time);
        } else if (type == CooldownType.NOTCH_APPLE) {
            notchAppleCooldown.put(playerId, time);
        }
    }

    // Toggle cooldown for a player manually (true to activate, false to clear)
    public void setCooldown(Player player, CooldownType type, boolean active) {
        UUID playerId = player.getUniqueId();
        if (active) {
            // Ensure cooldown time is set before starting
            if (!isOnCooldown(player, type)) {
                int cooldownTime = getCooldownTime(player, type);  // Get the current cooldown time
                if (cooldownTime > 0) {
                    // Only set cooldown if it's greater than 0
                    setCooldownTime(player, type, cooldownTime);
                }
            }
        } else {
            clearCooldown(player, type);  // Clear cooldown when setting to false
        }
    }

    // Check if the player is on cooldown for the specified type
    public boolean isOnCooldown(Player player, CooldownType type) {
        UUID playerId = player.getUniqueId();
        return (type == CooldownType.ENDERPEARL && enderpearlCooldown.containsKey(playerId)) ||
                (type == CooldownType.NOTCH_APPLE && notchAppleCooldown.containsKey(playerId));
    }

    // Get remaining cooldown time for the player
    public int getCooldownTime(Player player, CooldownType type) {
        UUID playerId = player.getUniqueId();
        return type == CooldownType.ENDERPEARL
                ? enderpearlCooldown.getOrDefault(playerId, 0)
                : notchAppleCooldown.getOrDefault(playerId, 0);
    }

    // Clear the cooldown for a player
    public void clearCooldown(Player player, CooldownType type) {
        UUID playerId = player.getUniqueId();
        if (type == CooldownType.ENDERPEARL) {
            enderpearlCooldown.remove(playerId);
        } else if (type == CooldownType.NOTCH_APPLE) {
            notchAppleCooldown.remove(playerId);
        }
    }

    // Method to return a BukkitRunnable that handles cooldown timer for a player
    public BukkitRunnable getStartCooldownTimer(Player player, CooldownType type) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                UUID playerId = player.getUniqueId();
                // Check for Enderpearl cooldown
                if (type == CooldownType.ENDERPEARL && enderpearlCooldown.containsKey(playerId)) {
                    int remaining = enderpearlCooldown.get(playerId) - 1;
                    if (remaining <= 0) {
                        clearCooldown(player, CooldownType.ENDERPEARL);
                        player.sendMessage(CC.translate("&aYou are no longer on enderpearl cooldown."));
                        cancel();
                    } else {
                        enderpearlCooldown.put(playerId, remaining);
                    }
                }
                // Check for Notch Apple cooldown
                if (type == CooldownType.NOTCH_APPLE && notchAppleCooldown.containsKey(playerId)) {
                    int remaining = notchAppleCooldown.get(playerId) - 1;
                    if (remaining <= 0) {
                        clearCooldown(player, CooldownType.NOTCH_APPLE);
                        player.sendMessage(CC.translate("&aYou are no longer on god apple cooldown."));
                        cancel();
                    } else {
                        notchAppleCooldown.put(playerId, remaining);
                    }
                }
            }
        };
    }

    public enum CooldownType {
        ENDERPEARL,
        NOTCH_APPLE
    }
}
