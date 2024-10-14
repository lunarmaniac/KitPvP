package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class CombatCommand {
    public CombatCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "combat", aliases = {"ct", "spawntag"}, permission = "kitpvp.player", inGameOnly = true)
    public void execute(CommandArgs command) {
        Player player = command.getPlayer();

        if (Main.getInstance().combatManager.isCombat(player)) {
            player.sendMessage(CC.translate("&cYou are combat-tagged for " + Main.getInstance().combatManager.getCombatTime(player) + "s"));
        } else {
            player.sendMessage(CC.translate(("&aYou're not combat-tagged.")));
        }
    }
}