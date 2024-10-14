package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class KitPvPCommand
{

    public KitPvPCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "kitpvp", permission = "kitpvp.admin", inGameOnly = true)
    public boolean execute(CommandArgs cmd) {
        final Player player = cmd.getPlayer();
        player.sendMessage(CC.translate("\n&7&m------------------------------------------- \n&6&lKitPvP &7┃ &fMain Page \n&7&m------------------------------------------- \n&6/kitpvp profile &7┃ &fAll help reguarding profile commands \n&6/kitpvp setlocation &7┃ &fAll help reguarding setlocation commands \n&7&m------------------------------------------- \n"));
        return false;
    }


    @Command(name = "kitpvp.profile", permission = "kitpvp.admin", inGameOnly = true)
    public void profileSubCommand(CommandArgs args) {
        final Player player = args.getPlayer();
        player.sendMessage(CC.translate("\n&7&m------------------------------------------- \n&6&lKitPvP &7┃ &fCommand Information \n&7&m------------------------------------------- \n&6/profile setrank <player> <rank> &7┃ &fSet a players rank \n&7&m------------------------------------------- \n"));
    }


    @Command(name = "kitpvp.setlocation", permission = "kitpvp.admin", inGameOnly = true)
    public void setlocationSubCommand(CommandArgs args) {
        final Player player = args.getPlayer();
        player.sendMessage(CC.translate("\n&7&m------------------------------------------- \n&6&lKitPvP &7┃ &fCommand Information \n&7&m------------------------------------------- \n&6/setlocation min &7┃ &fSet safe zone cuboid min position \n&6/setlocation max &7┃ &fSet safe zone cuboid max position \n&6/setlocation spawn &7┃ &fSet safe zone spawn point \n&7&m------------------------------------------- \n"));

    }
}

