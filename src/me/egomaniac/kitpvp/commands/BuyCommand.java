package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.ui.BuyUI;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class BuyCommand {
    public BuyCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "buy", aliases = {"store"}, permission = "kitpvp.player", inGameOnly = true)
    public boolean execute(CommandArgs cmd) {
        final Player player = cmd.getPlayer();
        BuyUI buyUI = new BuyUI();

        buyUI.open(player);
        return false;
    }

}
