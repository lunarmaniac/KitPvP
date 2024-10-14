package me.egomaniac.kitpvp.commands.misc;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class FeedCommand {

    public FeedCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "feed", permission = "kitpvp.admin", inGameOnly = true)
    public void execute(CommandArgs args) {
        if (args.length() == 0) {
            Player player = args.getPlayer();

            player.setFoodLevel(20);
            args.getSender().sendMessage(CC.GOLD + "You have been fed.");
            return;
        }
        Player target = Main.getInstance().getServer().getPlayer(args.getArgs(0));

        target.setFoodLevel(20);
        target.sendMessage(CC.GOLD + "You have been fed.");
        args.getSender().sendMessage(CC.GOLD + "You have fed " + CC.RESET + target.getName() + CC.GOLD + ".");
    }
}