package me.egomaniac.kitpvp.commands.misc;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RepairCommand {

    public RepairCommand() {
        Main.getInstance().framework.registerCommands(this);
    }


    @Command(name = "repair", permission = "kitpvp.legend", inGameOnly = false)
    public void execute(CommandArgs args) {
        Player target;

        if (args.length() == 1) {
            if (!args.getSender().hasPermission("kitpvp.admin")) {
                args.getSender().sendMessage(CC.translate("&cYou do not have permission to repair someone else's items."));
                return;
            }

            target = Bukkit.getPlayer(args.getArgs(0));

            if (target == null) {
                args.getSender().sendMessage(CC.translate("&cPlayer not found or not online."));
                return;
            }
        } else if (args.isPlayer()) {
            target = args.getPlayer();

            if (!target.hasPermission("kitpvp.legend")) {
                target.sendMessage(CC.translate("&cYou do not have permission to repair your own items."));
                return;
            }
        } else {
            args.getSender().sendMessage(CC.translate("&cUsage: /repair <player>"));
            return;
        }

        for (ItemStack item : target.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                item.setDurability((short) 0);
            }
        }

        target.sendMessage(CC.translate("&aAll items in your inventory have been repaired."));

        if (!target.equals(args.getSender())) {
            args.getSender().sendMessage(CC.translate("&aYou have repaired " + target.getName() + "'s inventory."));
        }
    }
}
