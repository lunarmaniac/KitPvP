package me.egomaniac.kitpvp.managers;

import lombok.Getter;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class EnderpearlManager {

    private final Set<Player> cooldownSet = new HashSet<>();
    private final Map<Player, Integer> timeMap = new HashMap<>();

    private int count = 0;

    public void setEnderpearlSet(Player player, boolean b) {
        if (b) {
            if (!cooldownSet.contains(player)) {
                cooldownSet.add(player);
                timeMap.put(player, 20);
                //player.sendMessage(CC.RED + "You're now on enderpearl cooldown for " + getCooldownTime(player) + " seconds.");
            } else {
                cooldownSet.add(player);
                timeMap.put(player, 20);
            }
        } else {
            cooldownSet.remove(player);
            timeMap.remove(player);
        }
    }

    public void setCooldownTime(Player player, int time) {
        timeMap.remove(player);
        timeMap.put(player, time);
    }

    public boolean isCooldown(Player player) {
        return cooldownSet.contains(player);
    }


    public int getCooldownTime(Player player) {
        return timeMap.get(player);
    }

    BukkitRunnable startCooldownTimer = new BukkitRunnable() {
        @Override
        public void run() {
            count++;

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Main.getInstance().enderpearlManager.isCooldown(player)) {
                    int count = Main.getInstance().enderpearlManager.getCooldownTime(player);
                    --count;
                    Main.getInstance().enderpearlManager.setCooldownTime(player, count);
                    if (count == 0) {
                        player.sendMessage(CC.translate("&aYou are no longer on enderpearl cooldown."));
                        Main.getInstance().enderpearlManager.getCooldownSet().remove(player);
                        Main.getInstance().enderpearlManager.getTimeMap().remove(player);
                    }
                }

                if (count == 160) {
                    count = 0;
                }
            }
        }
    };
}