package me.egomaniac.kitpvp.commands.misc;

import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class BroadcastCommand {
    public BroadcastCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "broadcast", aliases = {"bc", "alert", "announce"}, permission = "kitpvp.admin", usage = "&cUsage: /broadcast <message>")
    public void execute(CommandArgs cmd) {
        final Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&c" + cmd.getCommand().getUsage()));
            return;
        }

        StringBuilder message = new StringBuilder();
        for (int i = 0; i < args.length; i++){
            message.append(args[i]).append(" ");
        }

        Main.getInstance().getServer().broadcastMessage("");
        Main.getInstance().getServer().broadcastMessage(CC.translate("&8[&4Alert&8] &7" + message));
        Main.getInstance().getServer().broadcastMessage("");
    }
}
