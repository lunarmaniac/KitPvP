package me.egomaniac.kitpvp.commands.messages;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class ReplyCommand {
    public ReplyCommand() {
        Main.getInstance().framework.registerCommands(this);
    }
    @Command(name = "reply", aliases = {"r"}, permission = "kitpvp.player", usage = "&cUsage: /reply <message>", inGameOnly = true)
    public void execute(CommandArgs args) {
        Player player = args.getPlayer();
        if (args.length() < 1) {
            args.getSender().sendMessage(CC.translate(args.getCommand().getUsage()));
            return;
        }

        if (!Main.getInstance().profileManager.getProfile(player.getUniqueId()).isTogglepm()) {
            args.getSender().sendMessage(CC.RED + "You have private messaging disabled.");
            return;
        }

        Player target = Main.getInstance().profileManager.getProfile(player.getUniqueId()).getLastMessager();

        if (target == null) {
            args.getSender().sendMessage(CC.RED + "Could not find player.");
            return;
        }

        if (!Main.getInstance().profileManager.getProfile(target.getUniqueId()).isTogglepm()) {
            args.getSender().sendMessage(CC.RED + target.getDisplayName() + " has private messaging disabled.");
            return;
        }

        Main.getInstance().profileManager.getProfile(player.getUniqueId()).setLastMessager(target);
        Main.getInstance().profileManager.getProfile(target.getUniqueId()).setLastMessager(player);
        String message = args.getArgs(0);

        args.getPlayer().sendMessage(CC.translate("&7(To " + Main.getInstance().profileManager.getPlayerRank(target.getUniqueId()).getColor() + target.getDisplayName() + "&7) " + message));
        target.sendMessage(CC.translate("&7(From " + Main.getInstance().profileManager.getPlayerRank(player.getUniqueId()).getColor() + player.getDisplayName() + "&7) " + message));

    }
}

