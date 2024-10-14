package me.egomaniac.kitpvp.commands.misc;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class InvseeCommand {

    public InvseeCommand() {
        Main.getInstance().framework.registerCommands(this);
    }
    @Command(name = "invsee", permission = "kitpvp.admin", usage = "&cUsage: /invsee <player>",  inGameOnly = true)
    public void execute(CommandArgs command) {

        Player player = command.getPlayer();
        String[] args = command.getArgs();


        if (args.length < 1) {
            command.getSender().sendMessage(CC.translate(command.getCommand().getUsage()));
            return;
        }

        OfflinePlayer target = Bukkit.getPlayerExact(args[0]);

        if (target.isOnline()) {
            player.openInventory(target.getPlayer().getInventory());
            player.sendMessage(CC.translate("&aOpening " + target.getName() + "'s inventory"));
        } else {
            player.sendMessage(CC.RED + "This player couldn't be found");
        }
    }
}