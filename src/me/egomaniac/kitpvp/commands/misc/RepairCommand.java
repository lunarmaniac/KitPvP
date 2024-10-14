package me.egomaniac.kitpvp.commands.misc;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RepairCommand {

    public RepairCommand() {
        Main.getInstance().framework.registerCommands(this);
    }
    @Command(name = "repair", permission = "kitpvp.admin", inGameOnly = true)
    public void execute(CommandArgs args) {
        Player player = args.getPlayer();

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                item.setDurability((short) 0);
            }
        }

        player.sendMessage(CC.translate("&aAll items in your inventory have been repaired."));
    }
}
