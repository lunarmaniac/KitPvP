package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.managers.StaffManager;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class VanishCommand {

    public VanishCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "vanish", aliases = {"v"}, permission = "kitpvp.staff", inGameOnly = true)
    public boolean execute(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        Main.getInstance().staffManager.toggleVanish(player);

        if (Main.getInstance().staffManager.isVanished(player)) {
            player.sendMessage("§cYou are now invisible to other players.");
        } else {
            player.sendMessage("§aYou are now visible to other players.");
        }

        return true;
    }
}