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

/**
 * Created by Infames
 * Date: 23/07/2021 @ 20:08
 */

@Getter
public class CombatManager {

    private final Set<Player> combatSet = new HashSet<>();
    private final Map<Player, Integer> timeMap = new HashMap<>();

    private int count = 0;

    public void setCombatSet(Player player, boolean b) {
        if (b) {
            if (!combatSet.contains(player)) {
                combatSet.add(player);
                timeMap.put(player, 20);
                player.sendMessage(CC.RED + "You're now combat-tagged for " + getCombatTime(player) + " seconds.");
            } else {
                combatSet.add(player);
                timeMap.put(player, 20);
            }
        } else {
            combatSet.remove(player);
            timeMap.remove(player);
        }
    }

    public void setCombatTime(Player player, int time) {
        timeMap.remove(player);
        timeMap.put(player, time);
    }

    public boolean isCombat(Player player) {
        return combatSet.contains(player);
    }


    public int getCombatTime(Player player) {
        return timeMap.get(player);
    }


    BukkitRunnable startCombatTimer = new BukkitRunnable() {
        @Override
        public void run() {
            count++;

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Main.getInstance().combatManager.isCombat(player)) {
                    int count = Main.getInstance().combatManager.getCombatTime(player);
                    --count;
                    Main.getInstance().combatManager.setCombatTime(player, count);
                    if (count == 0) {
                        player.sendMessage(CC.translate("&aYou are no longer combat-tagged."));
                        Main.getInstance().combatManager.getCombatSet().remove(player);
                        Main.getInstance().combatManager.getTimeMap().remove(player);
                    }
                }

                if (count == 160) {
                    count = 0;
                }
            }
        }
    };
}