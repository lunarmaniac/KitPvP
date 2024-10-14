package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class SpawnCommand {
    public SpawnCommand() {
        Main.getInstance().framework.registerCommands(this);
    }


    @Command(name = "spawn", aliases = {"lobby", "leave"}, permission = "kitpvp.player", inGameOnly = true)
    public boolean execute(CommandArgs cmd) {
        final Player player = cmd.getPlayer();

        if (Main.getInstance().combatManager.isCombat(player)) {
            player.sendMessage(CC.RED + "You're currently combat-tagged.");
            return true;
        }

        if (Main.getInstance().profileManager.getProfile(player.getUniqueId()).isTeleporting()) {
            player.sendMessage(CC.RED + "You are already teleporting to spawn.");
            return true;
        }

        Main.getInstance().profileManager.getProfile(player.getUniqueId()).setTeleporting(true);
        player.sendMessage(CC.YELLOW + "Teleporting to spawn in 5 seconds...");

        // Use the TeleportManager to handle the teleportation
        Main.getInstance().teleportManager.teleportSpawnAfterDelay(player, 5);

        return true;
    }
}