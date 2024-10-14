package me.egomaniac.kitpvp.commands.misc;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class DiscordCommand
{
    public DiscordCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "discord", aliases = {"dc"}, permission = "kitpvp.player", inGameOnly = true)
    public boolean execute(CommandArgs cmd) {
        final Player player = cmd.getPlayer();

        player.sendMessage(CC.translate("&7&ohttps://discord.gg/senaticpvp"));
        return false;
    }
}
