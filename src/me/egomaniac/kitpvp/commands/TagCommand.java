package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.ui.TagUI;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class TagCommand {
    public TagCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "tag", permission = "kitpvp.player", inGameOnly = true)
    public boolean execute(CommandArgs cmd) {
        final Player player = cmd.getPlayer();
        TagUI tagMenu = new TagUI();

        tagMenu.open(player);
        return false;
    }

}
