package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClearCommand {

    public ClearCommand() {
        Main.getInstance().framework.registerCommands(this);

    }
    private final Map<UUID, Integer> confirmationMap = new HashMap<>();

    @Command(name = "clear", permission = "kitpvp.player", inGameOnly = true)
    public void execute(CommandArgs args) {
        Player player = args.getPlayer();
        UUID playerId = player.getUniqueId();

        if (!confirmationMap.containsKey(playerId)) {
            confirmationMap.put(playerId, 1);
            player.sendMessage(CC.translate("&cAre you sure?, You will lose ALL your items. \n&cType /clear again to confirm."));
        } else {
            int confirmationCount = confirmationMap.get(playerId);

            if (confirmationCount == 1) {
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);
                player.sendMessage(CC.translate("&cYou have cleared your inventory."));
                confirmationMap.remove(playerId);
            }
        }
    }
}
