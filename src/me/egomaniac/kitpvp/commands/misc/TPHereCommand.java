package me.egomaniac.kitpvp.commands.misc;

import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TPHereCommand {

    public TPHereCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "tphere", permission = "kitpvp.admin", usage = "&cUsage: /tphere <player>", inGameOnly = true)
    public void execute(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() == 1) {
            String targetName = args.getArgs(0);
            Player target = Bukkit.getPlayer(targetName);

            if (target == null || !target.isOnline()) {
                player.sendMessage(CC.translate("&cPlayer not found."));
                return;
            }

            target.teleport(player.getLocation());
            player.sendMessage(CC.translate("&aTeleported " + target.getName() + " to your location."));
        } else {
            player.sendMessage(CC.translate(args.getCommand().getUsage()));
        }
    }
}
