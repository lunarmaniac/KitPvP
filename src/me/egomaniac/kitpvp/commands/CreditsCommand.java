package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import me.egomaniac.kitpvp.utils.creditConversionUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreditsCommand {

    private final Main plugin = Main.getInstance();

    public CreditsCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "credits", permission = "kitpvp.player")
    public void execute(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.getArgs().length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                int credits = Main.getInstance().economyManager.getCredits(player.getUniqueId());
                String creditsDisplay = creditConversionUtil.formatNumber(credits);

                sender.sendMessage(CC.translate("&aYour credits: " + credits + " &7(" + creditsDisplay + ")"));
            } else {
                sender.sendMessage(CC.RED + "This command can only be executed by players.");
            }
        } else if (args.getArgs().length == 1) {
            Player target = Main.getInstance().getServer().getPlayer(args.getArgs()[0]);
            if (target != null) {
                int credits = Main.getInstance().economyManager.getCredits(target.getUniqueId());
                String creditsDisplay = creditConversionUtil.formatNumber(credits);
                sender.sendMessage(CC.GREEN + target.getName() + "'s credits: " + credits + " &7(" + creditsDisplay + ")");
            } else {
                sender.sendMessage(CC.RED + "Invalid player.");
            }
        }
    }
}