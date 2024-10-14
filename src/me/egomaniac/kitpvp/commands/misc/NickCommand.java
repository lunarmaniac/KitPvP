package me.egomaniac.kitpvp.commands.misc;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class NickCommand {

    public NickCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "nick", permission = "kitpvp.admin", usage = "&cUsage: /nick <nickname>", inGameOnly = true)
    public void execute(CommandArgs args) {
        Player player = args.getPlayer();

        String nickname = args.getArgs(0);

        player.setDisplayName(nickname);
        player.setPlayerListName(nickname);
        Main.getInstance().profileManager.updateNameTag(player);
        player.sendMessage(CC.translate("&aYour nickname has been set to &e" + nickname));
    }
}
