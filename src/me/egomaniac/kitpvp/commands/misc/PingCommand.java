package me.egomaniac.kitpvp.commands.misc;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PingCommand
{
    public PingCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "ping", aliases = {"connection", "ms", "latency"}, permission = "kitpvp.player", inGameOnly = true)
    public boolean execute(CommandArgs cmd) {
        final Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&eYour ping is &a" + ((CraftPlayer) player).getHandle().ping) + "ms");
        }
        else {
            final Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
            player.sendMessage(CC.translate("&a" + target.getDisplayName() + "&e's ping is &a" + ((CraftPlayer) target).getHandle().ping) + "ms");
        } else {
                player.sendMessage(CC.translate("&cPlayer not found."));
            }
        }
        return false;
    }
}
