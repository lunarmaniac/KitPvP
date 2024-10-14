package me.egomaniac.kitpvp.commands.misc;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SmiteCommand {

    public SmiteCommand() {
        Main.getInstance().framework.registerCommands(this);
    }
    @Command(name = "smite", permission = "kitpvp.admin", usage = "&cUsage: /smite <player>", inGameOnly = true)
    public void execute(CommandArgs args) {
        if (args.length() < 1) {
            args.getPlayer().sendMessage(CC.translate(args.getCommand().getUsage()));
            return;
        }

        Player target = Bukkit.getPlayer(args.getArgs(0));

        if (target == null) {
            args.getPlayer().sendMessage(CC.translate("&cPlayer not found."));
            return;
        }

        target.getWorld().strikeLightning(target.getLocation());
        target.getWorld().strikeLightning(target.getLocation());
        target.getWorld().strikeLightning(target.getLocation());

        args.getPlayer().sendMessage(CC.translate("&aSmite executed on &e" + target.getName()));
    }
}
