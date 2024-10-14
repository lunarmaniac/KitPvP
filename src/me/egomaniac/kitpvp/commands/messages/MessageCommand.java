package me.egomaniac.kitpvp.commands.messages;

import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class MessageCommand {
    public MessageCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "message", aliases = {"msg", "pm", "whisper", "w", "tell"}, permission = "kitpvp.player", usage = "&cUsage: /message <player> <message>", inGameOnly = true)
    public void execute(CommandArgs args) {
        Player player = args.getPlayer();
        if (args.length() < 2) {
            player.sendMessage(CC.translate(args.getCommand().getUsage()));
            return;
        }

        if (!Main.getInstance().profileManager.getProfile(player.getUniqueId()).isTogglepm()) {
            player.sendMessage(CC.RED + "You have private messaging disabled.");
            return;
        }

        Player target = Main.getInstance().getServer().getPlayer(args.getArgs(0));

        if (target == null) {
            player.sendMessage(CC.RED + "Could not find player.");
            return;
        }

        if (!Main.getInstance().profileManager.getProfile(target.getUniqueId()).isTogglepm()) {
            player.sendMessage(CC.RED + target.getDisplayName() + " has private messaging disabled.");
            return;
        }

        Main.getInstance().profileManager.getProfile(player.getUniqueId()).setLastMessager(target);
        Main.getInstance().profileManager.getProfile(target.getUniqueId()).setLastMessager(player);

        StringBuilder message = new StringBuilder();
        for (int i = 1; i < args.getArgs().length; i++){
            message.append(args.getArgs()[i]).append(" ");
        }

        player.sendMessage(CC.translate("&7(To " + Main.getInstance().profileManager.getPlayerRank(target.getUniqueId()).getColor() + target.getDisplayName() + "&7) " + message));
        target.sendMessage(CC.translate("&7(From " + Main.getInstance().profileManager.getPlayerRank(player.getUniqueId()).getColor() + player.getDisplayName() + "&7) " + message));

    }
}
