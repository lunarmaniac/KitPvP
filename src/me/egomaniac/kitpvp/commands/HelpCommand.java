package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.ui.HowToPlayUI;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class HelpCommand {
    public HelpCommand() {
        Main.getInstance().framework.registerCommands(this);

    }

    @Command(name = "help", aliases = {"howtoplay"}, permission = "kitpvp.player", inGameOnly = true)
    public boolean execute(CommandArgs cmd) {
        final Player player = cmd.getPlayer();

        HowToPlayUI howToPlayUI = new HowToPlayUI();
        howToPlayUI.open(player);

        return false;
    }
}
