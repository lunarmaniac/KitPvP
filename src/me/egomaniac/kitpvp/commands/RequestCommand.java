package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class RequestCommand {

    public RequestCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "request", permission = "kitpvp.player", usage = "&cUsage: /request <message>", inGameOnly = true)
    public void request(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 1) {
            args.getSender().sendMessage(CC.translate(args.getCommand().getUsage()));
            return;
        }

        StringBuilder message = new StringBuilder();
        for (int i = 0; i < args.getArgs().length; i++){
            message.append(args.getArgs()[i]).append(" ");
        }

        Main.getInstance().playerManager.sendRequest(player, message.toString());
        player.sendMessage(CC.GREEN + "Your request has been received.");

    }
}
