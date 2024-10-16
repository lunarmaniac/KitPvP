package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.managers.ProfileManager;
import me.egomaniac.kitpvp.managers.ProfileManager.Rank;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.DiscordWebhook;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import me.egomaniac.kitpvp.utils.commandFramework.Completer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileCommand {

    public ProfileCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    private final static String[] HELP_MESSAGE = new String[] {
            CC.translate("&7&m-------------------------------------------"),
            CC.translate("&6&lKitPvP &7┃ &fCommand Information"),
            CC.translate("&7&m-------------------------------------------"),
            CC.translate("&6/profile setrank <player> <rank> &7┃ &fSet a players rank"),
            CC.translate("&6/profile setcredits <player> <amount> &7┃ &fSet a players credits"),
            CC.translate("&6/profile addcredits <player> <amount> &7┃ &fAdd to players credits"),
            CC.translate("&6/profile show <player> &7\u2503 &fView a players profile"),
            CC.translate("&7&m-------------------------------------------")
    };

    @Command(name = "profile", permission = "kitpvp.admin")
    public void execute(CommandArgs args) {
        CommandSender sender = args.getSender();
        sender.sendMessage(HELP_MESSAGE);
    }

    @Command(name = "profile.setrank", permission = "kitpvp.admin")
    public void setRank(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.getArgs().length < 2) {
            sender.sendMessage(CC.RED + "Usage: /profile setrank <player> <rank>");
            return;
        }

        String playerName = args.getArgs()[0];
        Player onlinePlayer = Bukkit.getPlayerExact(playerName);

        if (onlinePlayer != null) {
            String rankName = args.getArgs()[1];
            Rank rank = null;

            try {
                rank = Rank.valueOf(rankName.toUpperCase());
            } catch (IllegalArgumentException e) {
                sender.sendMessage(CC.RED + "This rank doesn't exist.");
                return;
            }

            Main.getInstance().profileManager.setPlayerRank(onlinePlayer.getUniqueId(), rank);
            Main.getInstance().playerManager.updatePermissions(onlinePlayer);
            Main.getInstance().profileManager.updateNameTag(onlinePlayer);

            onlinePlayer.sendMessage(CC.GREEN + "Your rank has been updated to " + rank.getDisplayName());
            sender.sendMessage(CC.GREEN + "Successfully set the rank of " + onlinePlayer.getName() + " to " + rank.getDisplayName());
            DiscordWebhook.send("Rank Update", onlinePlayer.getDisplayName() + " has been granted " + rank.getDisplayName() + " by " + sender.getName(), Color.ORANGE, DiscordWebhook.Type.PROFILE);

        } else {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);

            if (!offlinePlayer.hasPlayedBefore()) {
                sender.sendMessage(CC.RED + "This player has never logged on.");
                return;
            }

            String rankName = args.getArgs()[1];
            Rank rank = null;

            try {
                rank = Rank.valueOf(rankName.toUpperCase());
            } catch (IllegalArgumentException e) {
                sender.sendMessage(CC.RED + "This rank doesn't exist.");
                return;
            }

            Main.getInstance().profileManager.setPlayerRank(offlinePlayer.getUniqueId(), rank);
            sender.sendMessage(CC.GREEN + "Successfully set the rank of " + offlinePlayer.getName() + " to " + rank.getDisplayName());
            DiscordWebhook.send("Rank Update", offlinePlayer.getName() + " has been granted " + rank.getDisplayName() + " by " + sender.getName(), Color.ORANGE, DiscordWebhook.Type.PROFILE);
        }
    }

    @Completer(name = "profile.setrank")
    public List<String> setRankTabComplete(CommandArgs args) {
        if (args.getArgs().length == 1) {
            List<String> playerNames = new ArrayList<>();
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                playerNames.add(player.getName());
            }
            return playerNames;
        } else if (args.getArgs().length == 2) {
            List<String> rankNames = new ArrayList<>();
            for (Rank rank : Rank.values()) {
                rankNames.add(rank.name().toLowerCase());
            }
            return rankNames;
        }
        return Collections.emptyList();
    }

    @Command(name = "profile.setcredits", permission = "kitpvp.admin")
    public void setCredits(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.getArgs().length != 2) {
            sender.sendMessage(CC.RED + "Usage: /setcredits <player> <amount>");
            return;
        }

        String playerName = args.getArgs()[0];
        OfflinePlayer offlinePlayer = Bukkit.getPlayerExact(playerName);

        if (!offlinePlayer.hasPlayedBefore()) {
            sender.sendMessage(CC.RED + "This player has never logged on.");
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(args.getArgs()[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(CC.RED + "Please provide a valid number for the amount.");
            return;
        }

        Main.getInstance().economyManager.setCredits(offlinePlayer.getUniqueId(), amount);

        if (offlinePlayer.isOnline()) {
            Player target = offlinePlayer.getPlayer();
            sender.sendMessage(CC.GREEN + "Successfully set " + target.getName() + "'s credits to " + amount);
            target.sendMessage(CC.GREEN + "Your credits have been updated to " + amount);
            DiscordWebhook.send("Credit Update", target.getDisplayName() + "'s credits have been updated to " + amount + " by " + sender.getName(), Color.ORANGE, DiscordWebhook.Type.PROFILE);
        } else {
            DiscordWebhook.send("Credit Update", offlinePlayer.getName() + "'s credits have been updated to " + amount + " by " + sender.getName(), Color.ORANGE, DiscordWebhook.Type.PROFILE);
            sender.sendMessage(CC.GREEN + "Successfully set " + offlinePlayer.getName() + "'s credits to " + amount);
        }
    }

    @Command(name = "profile.addcredits", permission = "kitpvp.admin")
    public void addCredits(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.getArgs().length != 2) {
            sender.sendMessage(CC.RED + "Usage: /addcredits <player> <amount>");
            return;
        }

        String playerName = args.getArgs()[0];
        OfflinePlayer offlinePlayer = Bukkit.getPlayerExact(playerName);

        if (offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
            sender.sendMessage(CC.RED + "This player has never logged on.");
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(args.getArgs()[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(CC.RED + "Please provide a valid number for the amount.");
            return;
        }

        if (amount <= 0) {
            sender.sendMessage(CC.RED + "Please provide a positive number to add.");
            return;
        }

        int currentCredits = Main.getInstance().economyManager.getCredits(offlinePlayer.getUniqueId());
        int newCredits = currentCredits + amount;
        Main.getInstance().economyManager.setCredits(offlinePlayer.getUniqueId(), newCredits);

        if (offlinePlayer.isOnline()) {
            Player target = offlinePlayer.getPlayer();
            sender.sendMessage(CC.GREEN + "Successfully added " + amount + " credits to " + target.getName() + ". Total: " + newCredits);
            target.sendMessage(CC.GREEN + "You have received " + amount + " additional credits. \nYour total is now " + newCredits);
            DiscordWebhook.send("Credit Update", target.getDisplayName() + " has been credited with " + amount + " credits by " + sender.getName() + ". New total: " + newCredits, Color.ORANGE, DiscordWebhook.Type.PROFILE);
        } else {
            DiscordWebhook.send("Credit Update", offlinePlayer.getName() + " has been credited with " + amount + " credits by " + sender.getName() + ". New total: " + newCredits, Color.ORANGE, DiscordWebhook.Type.PROFILE);
            sender.sendMessage(CC.GREEN + "Successfully added " + amount + " credits to " + offlinePlayer.getName() + ". New total: " + newCredits);
        }
    }

    @Command(name = "profile.show", permission = "kitpvp.admin", inGameOnly = true)
    public void showProfile(CommandArgs args) {
        CommandSender sender = args.getSender();



        if (args.getArgs().length != 1) {
            sender.sendMessage(CC.RED + "Usage: /profile show <player>");
            return;
        }

        Player target = Bukkit.getPlayer(args.getArgs()[0]);
        if (target == null) {
            sender.sendMessage(CC.RED + "Invalid player.");
            return;
        }

        ProfileManager profile = Main.getInstance().profileManager;

        final String[] SHOW_MESSAGE = new String[] {
                CC.translate("&eThis command isn't finished."),
                CC.translate("&7&m-------------------------------------------"),
                CC.translate("&b&l" + target.getDisplayName() + "'s profile"),
                CC.translate("&7&m-------------------------------------------"),
                CC.translate("&7Rank: " + profile.getProfile(target.getUniqueId()).getRank().getColor() + profile.getProfile(target.getUniqueId()).getRank().getDisplayName()),
                CC.translate("&7Credits: &b" + profile.getProfile(target.getUniqueId()).getCredits()),
                CC.translate("&7Tag: &b" + profile.getProfile(target.getUniqueId()).getTag()),
                CC.translate("&7Private Messages: " + (profile.getProfile(target.getUniqueId()).isTogglepm() ? "&aEnabled" : "&cDisabled")),
                CC.translate("&7&m-------------------------------------------")
        };

        sender.sendMessage(SHOW_MESSAGE);
    }

}


 // see it resets when anything else is fahnged