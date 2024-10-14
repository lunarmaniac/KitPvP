package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class StatsCommand
{
    public StatsCommand() {
        Main.getInstance().framework.registerCommands(this);

    }

    @Command(name = "stats", aliases = {"statistics"}, permission = "kitpvp.player", inGameOnly = true)
    public boolean execute(CommandArgs cmd) {
        final Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&7&m--------------------------"));
            player.sendMessage(CC.translate("&b&lStatistics"));
            player.sendMessage(CC.translate("&bKills&7: &f" + player.getStatistic(Statistic.PLAYER_KILLS)));
            player.sendMessage(CC.translate("&bDeaths&7: &f" + player.getStatistic(Statistic.DEATHS)));
            player.sendMessage(CC.translate("&7&m--------------------------"));
        }
        else {

            final Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                player.sendMessage(CC.translate("&7&m--------------------------"));
                player.sendMessage(CC.translate("&b&l" + target.getDisplayName() + "'s Statistics"));
                player.sendMessage(CC.translate("&bKills&7: &f" + target.getStatistic(Statistic.PLAYER_KILLS)));
                player.sendMessage(CC.translate("&bDeaths&7: &f" + target.getStatistic(Statistic.DEATHS)));
                player.sendMessage(CC.translate("&7&m--------------------------"));
            } else {
                player.sendMessage(CC.translate("&cPlayer not found."));
            }
        }
        return false;
    }
}
