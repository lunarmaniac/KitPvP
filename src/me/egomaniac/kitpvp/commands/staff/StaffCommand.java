package me.egomaniac.kitpvp.commands.staff;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.managers.StaffManager;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class StaffCommand {

    public StaffCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "staff", permission = "kitpvp.staff", aliases = {"h", "mod"}, usage = "&cUsage: /staff", inGameOnly = true)
    public void execute(CommandArgs command) {
        Player player = command.getPlayer();

        if (Main.getInstance().staffManager.isInStaffMode(player)) {
            Main.getInstance().staffManager.disableStaffMode(player);
            player.sendMessage(CC.translate("&aStaff mode disabled."));
        } else {
            Main.getInstance().staffManager.enableStaffMode(player);
            player.sendMessage(CC.translate("&aStaff mode enabled."));
        }
    }
}