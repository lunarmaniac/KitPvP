package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import me.egomaniac.kitpvp.ui.EventUI;
import org.bukkit.entity.Player;

public class EventCommand {

    private static EventUI eventUI = new EventUI();

    public EventCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "event", aliases = {"events"}, usage = "&cUsage: /event join:start", permission = "kitpvp.player")
    public void execute(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length == 0) {
            eventUI.open(player);
            return;
        }

        switch (args[0]) {
            case "start":
                Main.getInstance().sumoManager.hostEvent(player);
                break;
            case "join":
                Main.getInstance().sumoManager.joinSumo(player);
                break;
            default:
                player.sendMessage(CC.translate(cmd.getCommand().getUsage()));
                break;
        }
    }

}
