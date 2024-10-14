package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SuicideCommand {

    public SuicideCommand() {
        Main.getInstance().framework.registerCommands(this);

    }
    private final Map<UUID, Integer> confirmationMap = new HashMap<>();

    @Command(name = "suicide", permission = "kitpvp.player", inGameOnly = true)
    public void execute(CommandArgs args) {
        Player player = args.getPlayer();
        UUID playerId = player.getUniqueId();

        if (!confirmationMap.containsKey(playerId)) {
            confirmationMap.put(playerId, 1);
            player.sendMessage(CC.translate("&cAre you sure?, You will lose your items. Type /suicide again to confirm."));
        } else {
            int confirmationCount = confirmationMap.get(playerId);

            if (confirmationCount == 2) {
                player.setHealth(0);
                player.sendMessage(CC.translate("&cYou have committed suicide."));
                confirmationMap.remove(playerId);
            } else {
                confirmationMap.put(playerId, confirmationCount + 1);
                player.sendMessage(CC.translate("&cConfirmation required! Type /suicide again to confirm."));
            }
        }
    }
}
