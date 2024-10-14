package me.egomaniac.kitpvp.commands.misc;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SpeedCommand {

    public SpeedCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "speed", permission = "kitpvp.admin", usage = "&cUsage: /speed <type> <speed> <player>", inGameOnly = true)
    public void execute(CommandArgs args) {
        Player target;

        if (args.length() == 3) {
            target = Bukkit.getPlayer(args.getArgs(2));

            if (target == null) {
                args.getPlayer().sendMessage(CC.translate("&cPlayer not found."));
                return;
            }
        } else {
            target = args.getPlayer();
        }

        String type = args.getArgs(0);
        int speedAmount = Integer.parseInt(args.getArgs(1));

        if (type.equalsIgnoreCase("walk")) {
            target.setWalkSpeed((float) speedAmount / 10);
        } else if (type.equalsIgnoreCase("fly")) {
            target.setFlySpeed((float) speedAmount / 10);
        } else {
            args.getPlayer().sendMessage(CC.translate("&cInvalid speed type. Available types: walk, fly."));
            return;
        }

        args.getPlayer().sendMessage(CC.translate("&aSpeed set for " + target.getName()));
    }
}
