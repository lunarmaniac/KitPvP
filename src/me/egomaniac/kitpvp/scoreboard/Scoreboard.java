package me.egomaniac.kitpvp.scoreboard;

import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.scoreboard.api.AssembleAdapter;
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

        int credits = Main.getInstance().economyManager.getCredits(player.getUniqueId());
        int killstreak = Main.getInstance().profileManager.getProfile(player.getUniqueId()).getKillstreak();
        int highestKillstreak = Main.getInstance().profileManager.getProfile(player.getUniqueId()).getHighestKillstreak();

        if (Main.getInstance().sumoManager.isParticipant(player)) {
            toReturn.add(CC.translate(""));
            toReturn.add(CC.translate("&b⤷ Status: &f" + "Waiting"));
            toReturn.add(CC.translate(""));
            toReturn.add(CC.translate("    &7www.senaticpvp.net"));
        } else if (Main.getInstance().sumoManager.isFightPair(player)) {
            toReturn.add(CC.translate(""));
            toReturn.add(CC.translate("&b⤷ Status: &f" + "Fighting"));
            toReturn.add(CC.translate(""));
            toReturn.add(CC.translate("    &7www.senaticpvp.net"));

        } else if (Main.getInstance().combatManager.isCombat(player)) {
            toReturn.add(CC.translate(""));
            toReturn.add(CC.translate("&b⤷ Credits: &f" + credits));
            toReturn.add(CC.translate("&b⤷ Kills: &f" + player.getStatistic(Statistic.PLAYER_KILLS)));
            toReturn.add(CC.translate("&b⤷ Killstreak: &f" + killstreak));
            toReturn.add(CC.translate("&b⤷ Highest Killstreak: &f" + highestKillstreak));
            toReturn.add(CC.translate("&b⤷ Deaths: &f" + player.getStatistic(Statistic.DEATHS)));
            toReturn.add(CC.translate("&c⤷ Combat-tagged: &e" + Main.getInstance().combatManager.getCombatTime(player)) + "s");
            toReturn.add(CC.translate(""));
            toReturn.add(CC.translate("    &7www.senaticpvp.net"));
        } else {
            toReturn.add(CC.translate("                          "));
            toReturn.add(CC.translate("&b⤷ Credits: &f" + credits));
            toReturn.add(CC.translate("&b⤷ Kills: &f" + player.getStatistic(Statistic.PLAYER_KILLS)));
            toReturn.add(CC.translate("&b⤷ Killstreak: &f" + killstreak));
            toReturn.add(CC.translate("&b⤷ Highest Killstreak: &f" + highestKillstreak));
            toReturn.add(CC.translate("&b⤷ Deaths: &f" + player.getStatistic(Statistic.DEATHS)));
            toReturn.add(CC.translate(""));
            toReturn.add(CC.translate("    &7www.senaticpvp.net"));
        }

        return toReturn;
    }
}