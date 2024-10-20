package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.ui.api.ItemBuilder;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomItemCommand {

    public CustomItemCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "customitem", permission = "kitpvp.admin", inGameOnly = true)
    public void onCustomItemCommand(CommandArgs args) {
        final Player player = args.getPlayer();
        if (args.length() == 0) {
            player.sendMessage(CC.translate("\n&7&m------------------------------------------- \n&6&lKitPvP &7┃ &fCustom Items \n&7&m------------------------------------------- \n"
                    + "&6/customitem legend &7┃ &fLegend Kit item \n"
                    + "&6/customitem creditnote500 &7┃ &fCredit Note (500) item  \n"
                    + "&6/customitem creditnote1000 &7┃ &fCredit Note (1000) item \n"
                    + "&6/customitem kingrank &7┃ &fKing Rank item \n"
                    + "&6/customitem repairtoken &7┃ &fRepair Token item \n"
                    + "&7&m------------------------------------------- \n"));
        } else if (args.length() == 1) {
            String choice = args.getArgs(0).toLowerCase();
            switch (choice) {
                case "legend":
                    giveCustomItem(player, Material.BOOK, "&c&lLegend &fKit", "&7Right click to receive Legend Kit", "kit legend %player%");
                    break;
                case "creditnote500":
                    giveCustomItem(player, Material.PAPER, "&6Credit Note &7(500)", "&7Right click to redeem 500 credits", "profile addcredits %player% 500");
                    break;
                case "creditnote1000":
                    giveCustomItem(player, Material.PAPER, "&6Credit Note &7(1000)", "&7Right click to redeem 1000 credits", "profile addcredits %player% 1000");
                    break;
                case "kingrank":
                    giveCustomItem(player, Material.NETHER_STAR, "&6King &fRank", "&7Right click to activate King Rank", "profile setrank %player% King");
                    break;
                case "repairtoken":
                    giveCustomItem(player, Material.FIREWORK_CHARGE, "&bRepair Token", "&7Repair all armour & tools in inventory", "repair %player%");
                    break;
                default:
                    player.sendMessage(CC.translate("&cInvalid item choice. Use /customitem to see available options."));
                    break;
            }
        }
    }

    private void giveCustomItem(Player player, Material material, String itemName, String lore, String command) {
        ItemBuilder itemBuilder = new ItemBuilder(material)
                .setName(CC.translate("&6" + itemName))
                .setLore(CC.translate("&7" + lore));

        ItemStack item = Main.getInstance().itemManager.createCustomItem(itemBuilder, command);
        player.getInventory().addItem(item);
        player.sendMessage(CC.translate("&aYou have received the " + itemName + "!"));
    }
}
