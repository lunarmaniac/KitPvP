package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.managers.ProfileManager;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ListCommand {

    public ListCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "list", permission = "kitpvp.player", inGameOnly = true)
    public void execute(CommandArgs args) {
        Player player = args.getPlayer();

        player.sendMessage(CC.translate(Arrays.stream(ProfileManager.Rank.values())
                .map(rank -> rank.getColor() + rank.getDisplayName())
                .collect(Collectors.joining(", "))));
        player.sendMessage("");
        player.sendMessage(CC.translate("("+ Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers()) + "): " + Bukkit.getOnlinePlayers().stream()
                .map(p -> Main.getInstance().profileManager.getPlayerRank(p.getUniqueId()).getColor() + p.getName())
                .collect(Collectors.joining(", ")));
    }
}
