package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.managers.ProfileManager;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Comparator;

public class RankCommand {
    public RankCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "rank", permission = "kitpvp.admin", inGameOnly = true)
    public boolean execute(CommandArgs cmd) {
        final Player player = cmd.getPlayer();
        player.sendMessage(CC.translate("&aYour rank is " + Main.getInstance().profileManager.getPlayerRank(player.getUniqueId()).getColor() + Main.getInstance().profileManager.getPlayerRank(player.getUniqueId()).getDisplayName()));
        return false;
    }

    @Command(name = "rank.list", aliases = {"ranks"}, permission = "kitpvp.admin", inGameOnly = true)
    public void rankList(CommandArgs args) {
        final Player player = args.getPlayer();
        ProfileManager.Rank[] ranks = ProfileManager.Rank.values();


        Arrays.sort(ranks, Comparator.comparingInt(ProfileManager.Rank::getWeight));
        player.sendMessage(CC.translate("&7&m-------------------------------------------"));
        player.sendMessage(CC.translate("&6&lKitPvP &7┃ &fAll ranks"));
        player.sendMessage(CC.translate("&7&m-------------------------------------------"));
        for (ProfileManager.Rank rank : ranks) {
            String prefix;
            if (Main.getInstance().profileManager.getPlayerRank(player.getUniqueId()) == rank) {
                prefix = "*";
            } else {
                prefix = "-";
            }
            player.sendMessage(CC.translate("  " + prefix + " " + rank.getColor() + rank.getDisplayName() + ", " + rank.getPrefix()));
        }
        player.sendMessage(CC.translate("&7&m-------------------------------------------"));
    }

}