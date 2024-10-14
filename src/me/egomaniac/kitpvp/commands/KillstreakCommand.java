package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.ui.KillstreakUI;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class KillstreakCommand {
    public KillstreakCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "killstreaks", aliases = {"ks"}, permission = "kitpvp.player", inGameOnly = true)
    public boolean execute(CommandArgs cmd) {
        final Player player = cmd.getPlayer();
        KillstreakUI killstreakUI = new KillstreakUI();

        killstreakUI.open(player);
        return false;
    }

}
