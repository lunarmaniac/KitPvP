package me.egomaniac.kitpvp.events;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class AfkListener implements Listener {

    // Afk players, and afk area is different on senatic, afk players havent moved in 5 minutes, afk area are players in the afk area. we do this so we can add a little clock icon to their username on tab

    private final Map<UUID, Long> playerActivityTimers = new HashMap<>();
    private final List<UUID> afkPlayers = new ArrayList<>();
    private static final long AFK_TIME = 120; //300000
    private final Map<UUID, Long> afkAreaPlayers = new HashMap<>();
    private final Map<UUID, Long> lastCreditsAwarded = new HashMap<>();
    private final Map<UUID, Long> lastKeyAwarded = new HashMap<>();

    private final long TEN_SECONDS = 60000; // actually its 1m
    private final long FIVE_MINUTES = 600000; // actually 10 mins i think
    final FileConfiguration config = Main.getInstance().getConfig();

    public AfkListener() {
        startAfkCheckTask();

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (config.contains("SERVER.AFK")) {

                        if (Main.getInstance().afkManager.getCuboid().isIn(player)) {
                            long currentTime = System.currentTimeMillis();

                            afkAreaPlayers.putIfAbsent(player.getUniqueId(), currentTime);
                            lastCreditsAwarded.putIfAbsent(player.getUniqueId(), currentTime);
                            lastKeyAwarded.putIfAbsent(player.getUniqueId(), currentTime);

                            long totalDuration = currentTime - afkAreaPlayers.get(player.getUniqueId());
                            long lastCreditsTime = lastCreditsAwarded.get(player.getUniqueId());
                            long lastKeyTime = lastKeyAwarded.get(player.getUniqueId());

                            if (currentTime - lastCreditsTime >= TEN_SECONDS) {
                                Main.getInstance().economyManager.addCredits(player.getUniqueId(), 10);
                                lastCreditsAwarded.put(player.getUniqueId(), currentTime);
                                player.sendMessage(CC.translate("&aYou have earned &e10 &acredits!"));
                            }

                            if (currentTime - lastKeyTime >= FIVE_MINUTES) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "crate key " + player.getName() + " epic 1");
                                lastKeyAwarded.put(player.getUniqueId(), currentTime);
                                player.sendMessage(CC.translate("&aYou have earned an &dEPIC &acrate key"));
                            }
                        } else {
                            afkAreaPlayers.remove(player.getUniqueId());
                            lastCreditsAwarded.remove(player.getUniqueId());
                            lastKeyAwarded.remove(player.getUniqueId());
                        }
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 20); // Run the task every second (20 ticks)
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();
        playerActivityTimers.put(playerId, System.currentTimeMillis());
        Main.getInstance().afkManager.removeAfkPlayer(playerId);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();
        playerActivityTimers.put(playerId, System.currentTimeMillis());
        Main.getInstance().afkManager.removeAfkPlayer(playerId);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();
        playerActivityTimers.put(playerId, System.currentTimeMillis());
        Main.getInstance().afkManager.removeAfkPlayer(playerId);
    }

    public void startAfkCheckTask() {
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            long currentTime = System.currentTimeMillis();
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID playerId = player.getUniqueId();
                if (!playerActivityTimers.containsKey(playerId)) {
                    playerActivityTimers.put(playerId, currentTime);
                } else if (currentTime - playerActivityTimers.get(playerId) >= AFK_TIME) {
                    if (!Main.getInstance().afkManager.isPlayerAfk(playerId)) {
                        Main.getInstance().afkManager.addAfkPlayer(playerId);
                    }
                }
            }
        }, 0, 100);
    }
}
