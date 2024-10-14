package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

public class ReportCommand {

    public ReportCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "report", permission = "kitpvp.player", usage = "&cUsage: /report <player> <reason>", inGameOnly = true)
    public void execute(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 1) {
            args.getSender().sendMessage(CC.translate(args.getCommand().getUsage()));
            return;
        }

        Player target = Main.getInstance().getServer().getPlayer(args.getArgs(0));

        if (target == null) {
            args.getSender().sendMessage(CC.RED + "Could not find player.");
            return;
        }



        StringBuilder reason = new StringBuilder();
        for (int i = 1; i < args.getArgs().length; i++){
            reason.append(args.getArgs()[i]).append(" ");
        }

        Main.getInstance().playerManager.sendReport(player, target, reason.toString());
        player.sendMessage(CC.GREEN + "Your report has been received.");

    }
}
