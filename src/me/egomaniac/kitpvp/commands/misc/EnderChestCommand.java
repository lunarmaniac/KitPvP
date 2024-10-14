package me.egomaniac.kitpvp.commands.misc;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EnderChestCommand {

    public EnderChestCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "ec", permission = "kitpvp.admin", usage = "&cUsage: /enderchest <player>", inGameOnly = true)
    public void execute(CommandArgs args) {
        Player target;

        if (args.length() == 0) {
            target = args.getPlayer();
        } else {
            target = Bukkit.getPlayer(args.getArgs(0));
        }

        if (target == null) {
            args.getPlayer().sendMessage(CC.translate("&cPlayer not found."));
            return;
        }

        args.getPlayer().openInventory(target.getEnderChest());
    }
}
