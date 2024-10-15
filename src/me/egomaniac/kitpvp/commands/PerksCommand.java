package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.ui.PerksUI;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class PerksCommand {
    public PerksCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "perks", permission = "kitpvp.player", inGameOnly = true)
    public boolean execute(CommandArgs cmd) {
        final Player player = cmd.getPlayer();
        if( !player.hasPermission("kitpvp.admin")){
            if (!Main.getInstance().spawnManager.getCuboid().isIn(player)) {
                player.sendMessage(CC.translate("&cThis command cannot be used outside of spawn"));
                return true;
            }
        }


        PerksUI perksUI = PerksUI.getPlayerPerksUI(player);
        perksUI.open(player);
        return false;
    }
}
