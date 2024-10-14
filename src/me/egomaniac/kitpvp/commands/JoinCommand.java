package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class JoinCommand {

    public JoinCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "join", permission = "kitpvp.player", inGameOnly = true)
    public void execute(CommandArgs args) {
        Player player = args.getPlayer();
        if(Main.getInstance().sumoManager.isRunning()) {
            Main.getInstance().sumoManager.joinSumo(player);
            player.sendMessage(CC.translate("&aYou have joined the Sumo event."));
        } else {
            player.sendMessage(CC.RED + "There is currently no sumo event running.");
        }
    }
}
