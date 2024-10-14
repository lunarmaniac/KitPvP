package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.MOTD;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class SetMotdCommand {
    public SetMotdCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "setmotd", aliases = {"motd"}, permission = "kitpvp.admin", usage = "&cUsage: /setmotd <message>")
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

        MOTD.setMotd(message.toString());
        player.sendMessage(CC.translate("&ASuccesfully changed the MOTD to: " + message));
    }
}
