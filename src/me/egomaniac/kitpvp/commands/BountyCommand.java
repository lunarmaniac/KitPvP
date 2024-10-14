package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.perks.perks.Incognito;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BountyCommand {

    private final Main plugin = Main.getInstance();

    public BountyCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "bounty", permission = "kitpvp.player", inGameOnly = true)
    public void execute(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.getArgs().length != 2) {
            sender.sendMessage(CC.RED + "Usage: /bounty <player> <amount>");
            return;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Player target = Main.getInstance().getServer().getPlayer(args.getArgs()[0]);
            if (target != null) {
                int amount;
                try {
                    amount = Integer.parseInt(args.getArgs()[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(CC.RED + "Invalid amount.");
                    return;
                }

                if (amount <= 0) {
                    sender.sendMessage(CC.RED + "The bounty amount must be greater than 0.");
                    return;
                }

                // Check if the player has enough credits
                int credits = Main.getInstance().profileManager.getPlayerCredits(player.getUniqueId());
                if (credits >= amount) {
                    // Deduct credits from the player
                    Main.getInstance().profileManager.takePlayerCredits(player.getUniqueId(), amount);

                    // Update the bounty for the target player
                    int targetBounty = Main.getInstance().profileManager.getProfile(target.getUniqueId()).getBounty();
                    Main.getInstance().profileManager.getProfile(target.getUniqueId()).addBounty(amount);

                    boolean hasIncognitoPerk = Main.getInstance().playerPerksManager.hasPerk(target, new Incognito());

                    if (targetBounty > 0) {
                        target.sendMessage(CC.RED + player.getName() + " has added " + amount + " to your bounty!");
                        sender.sendMessage(CC.GREEN + "You have added " + amount + " to " + target.getName() + "'s bounty. " + (hasIncognitoPerk ? "" : "\nTheir bounty is now " + (targetBounty + amount) + "."));
                    } else {
                        target.sendMessage(CC.RED + player.getName() + " has set a bounty of " + amount + " on your head!");
                        sender.sendMessage(CC.GREEN + "You have placed a bounty of " + amount + " on " + target.getName() + ".");
                    }
                    Main.getInstance().profileManager.updateNameTag(player);
                    Main.getInstance().profileManager.updateNameTag(target);

                } else {
                    sender.sendMessage(CC.RED + "You do not have enough credits.");
                }
            } else {
                sender.sendMessage(CC.RED + "Invalid player.");
            }
        }
    }

    @Command(name = "setbounty", permission = "kitpvp.admin", inGameOnly = true)
    public void setBounty(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.getArgs().length != 2) {
            sender.sendMessage(CC.RED + "Usage: /setbounty <player> <amount>");
            return;
        }

        String playerName = args.getArgs()[0];
        Player target = Bukkit.getPlayer(playerName);

        if (target == null) {
            sender.sendMessage(CC.RED + "Invalid player.");
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(args.getArgs()[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(CC.RED + "Invalid amount.");
            return;
        }

        if (amount < 0) {
            sender.sendMessage(CC.RED + "The bounty amount must be a non-negative value.");
            return;
        }

        Main.getInstance().profileManager.getProfile(target.getUniqueId()).setBounty(amount);
        sender.sendMessage(CC.GREEN + target.getName() + "'s bounty has been set to " + amount + ".");
    }
}
