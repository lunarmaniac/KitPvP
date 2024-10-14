package me.egomaniac.kitpvp.commands.messages;

import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class TogglePMCommand {
    public TogglePMCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "togglepm", aliases = {"tpm", "tpms", "togglepms", "dnd"}, permission = "kitpvp.player", inGameOnly = true)
    public void execute(CommandArgs args) {
        Player player = args.getPlayer();
        Main.getInstance().profileManager.getProfile(player.getUniqueId()).setTogglepm(!Main.getInstance().profileManager.getProfile(player.getUniqueId()).isTogglepm());
        args.getSender().sendMessage(Main.getInstance().profileManager.getProfile(player.getUniqueId()).isTogglepm() ? CC.GREEN + "Private messages have been enabled." : CC.RED + "Private messages have been disabled.");
    }
}