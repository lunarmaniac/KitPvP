package me.egomaniac.kitpvp.scoreboard;

import me.egomaniac.kitpvp.managers.CooldownManager;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.scoreboard.api.AssembleAdapter;
import me.egomaniac.kitpvp.utils.creditConversionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard implements AssembleAdapter {

    @Override
    public String getTitle(final Player player) {
        return CC.translate("  &b&lSENATIC &7┃ &fKitPvP");
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> toReturn = new ArrayList<>();

        // Check if the player is in Sumo mode
        if (Main.getInstance().sumoManager.isParticipant(player)) {
            toReturn.add(CC.translate(""));
            toReturn.add(CC.translate("&b⤷ Status: &f" + "Waiting"));
            toReturn.add(CC.translate(""));
            toReturn.add(CC.translate("    &7www.senaticpvp.net"));
            return toReturn;  // Return early for sumo participant
        } else if (Main.getInstance().sumoManager.isFightPair(player)) {
            toReturn.add(CC.translate(""));
            toReturn.add(CC.translate("&b⤷ Status: &f" + "Fighting"));
            toReturn.add(CC.translate(""));
            toReturn.add(CC.translate("    &7www.senaticpvp.net"));
            return toReturn;  // Return early for sumo fight pair
        }

        // Check if the player is in Staff mode
        if (Main.getInstance().staffManager.isInStaffMode(player)) {
            toReturn.add(CC.translate(""));
            toReturn.add(CC.translate("&b⤷ Staff Mode"));
            toReturn.add(CC.translate("&b⤷ Vanish: &f" + (Main.getInstance().staffManager.isVanished(player) ? "&aTrue" : "&cFalse")));
            toReturn.add(CC.translate("&b⤷ Players: &f" + Bukkit.getOnlinePlayers().size()));
            toReturn.add(CC.translate("&b⤷ RAM: &f" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576 + "MB"));
            toReturn.add(CC.translate(""));
            toReturn.add(CC.translate("    &7www.senaticpvp.net"));
            return toReturn;  // Return early for staff mode
        }

        // General Info (only for players not in staff mode or sumo)
        int credits = Main.getInstance().economyManager.getCredits(player.getUniqueId());
        String creditsDisplay = creditConversionUtil.formatNumber(credits);

        int killstreak = Main.getInstance().profileManager.getProfile(player.getUniqueId()).getKillstreak();
        int highestKillstreak = Main.getInstance().profileManager.getProfile(player.getUniqueId()).getHighestKillstreak();

        toReturn.add(CC.translate(""));
        toReturn.add(CC.translate("&b⤷ Credits: &f" + creditsDisplay));
        toReturn.add(CC.translate("&b⤷ Kills: &f" + player.getStatistic(Statistic.PLAYER_KILLS)));
        toReturn.add(CC.translate("&b⤷ Killstreak: &f" + killstreak));
        toReturn.add(CC.translate("&b⤷ Highest Killstreak: &f" + highestKillstreak));
        toReturn.add(CC.translate("&b⤷ Deaths: &f" + player.getStatistic(Statistic.DEATHS)));

        // Combat status
        if (Main.getInstance().combatManager.isCombat(player)) {
            toReturn.add(CC.translate("&c⤷ Combat-tagged: &e" + Main.getInstance().combatManager.getCombatTime(player)) + "s");
        }

        // Cooldown status (append only if cooldowns are active)
        if (Main.getInstance().cooldownManager.isOnCooldown(player, CooldownManager.CooldownType.NOTCH_APPLE)) {
            int godAppleTime = Main.getInstance().cooldownManager.getCooldownTime(player, CooldownManager.CooldownType.NOTCH_APPLE);
            toReturn.add(CC.translate("&b⤷ &eGod Apple: &f" + godAppleTime + "s"));
        }

        if (Main.getInstance().cooldownManager.isOnCooldown(player, CooldownManager.CooldownType.ENDERPEARL)) {
            int pearlCooldownTime = Main.getInstance().cooldownManager.getCooldownTime(player, CooldownManager.CooldownType.ENDERPEARL);
            toReturn.add(CC.translate("&b⤷ &3Enderpearl: &f" + pearlCooldownTime + "s"));
        }

        // Footer
        toReturn.add(CC.translate(""));
        toReturn.add(CC.translate("    &7www.senaticpvp.net"));

        return toReturn;
    }
}
