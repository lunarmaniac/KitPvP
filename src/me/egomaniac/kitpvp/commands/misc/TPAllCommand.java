package me.egomaniac.kitpvp.commands.misc;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TPAllCommand {

    public TPAllCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "tpall", permission = "kitpvp.admin", inGameOnly = true)
    public void execute(CommandArgs args) {
        Player player = args.getPlayer();

        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target != player) {
                target.teleport(player.getLocation());
            }
        }

        player.sendMessage(CC.translate("&aTeleported all players to your location."));
    }
}
