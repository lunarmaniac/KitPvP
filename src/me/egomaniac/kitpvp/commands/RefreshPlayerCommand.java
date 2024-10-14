package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class RefreshPlayerCommand {

    public RefreshPlayerCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "refresh", permission = "kitpvp.admin", usage = "&cUsage: /refresh <player>", inGameOnly = true)
    public void execute(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();


        if (args.length < 1) {
            command.getSender().sendMessage(CC.translate(command.getCommand().getUsage()));
            return;
        }

        OfflinePlayer target = Bukkit.getPlayerExact(args[0]);

        if (target.isOnline()) {
            Main.getInstance().profileManager.updateNameTag(target.getPlayer());
            player.sendMessage(CC.GREEN + target.getName() + "'s profile has been updated.");
        } else {
            player.sendMessage(CC.RED + "This player couldn't be found");
        }
    }
}
