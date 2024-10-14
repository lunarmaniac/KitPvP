package me.egomaniac.kitpvp.commands.misc;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import org.bukkit.entity.Player;

public class WebsiteCommand
{
    public WebsiteCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "website", aliases = {"site"}, permission = "kitpvp.player", inGameOnly = true)
    public boolean execute(CommandArgs cmd) {
        final Player player = cmd.getPlayer();
        player.sendMessage(CC.translate("&7&owww.senaticpvp.net"));
        return false;
    }
}
