package me.egomaniac.kitpvp.managers;

import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.DiscordWebhook;
import me.egomaniac.kitpvp.Main;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.awt.*;
import java.util.UUID;

public class PlayerManager {

    public void sendStaffChatMessage(Player player, String message) {
        Main.getInstance().getServer().getOnlinePlayers()
                .stream().filter(player1 -> player1.hasPermission("kitpvp.admin"))
                .forEach(p -> p.sendMessage(CC.translate("&7[&bSC&7] " + Main.getInstance().profileManager.getPlayerRank(player.getUniqueId()).getPrefix()) + player.getDisplayName() + ": &f" + message));
    }

    public void sendRequest(Player player, String message) {
        Main.getInstance().getServer().getOnlinePlayers()
                .stream().filter(player1 -> player1.hasPermission("kitpvp.admin"))
                .forEach(p -> p.sendMessage(CC.translate("&7[&bRequest&7] &b" + player.getDisplayName() + "&7 has requested: &b" + message + "&7")));
        DiscordWebhook.send("Player Request", player.getDisplayName() + " has requested  '" + message + "'", Color.ORANGE, DiscordWebhook.Type.PROFILE);

    }

    public void sendReport(Player player, Player target, String reason) {
        Main.getInstance().getServer().getOnlinePlayers()
                .stream().filter(player1 -> player1.hasPermission("kitpvp.admin"))
                .forEach(p -> p.sendMessage(CC.translate("&7[&bReport&7] &b" + player.getDisplayName() + "&7 has reported &b" + target.getDisplayName() + " &7for: &b" + reason + "&7")));
        DiscordWebhook.send("Player Report", target.getDisplayName() + " has been reported by  " + player.getDisplayName() + " for '" + reason + "'", Color.ORANGE, DiscordWebhook.Type.PROFILE);

    }

    public void updatePermissions(Player player) {

        UUID playerId = player.getUniqueId();
        ProfileManager.Rank rank = Main.getInstance().profileManager.getProfile(playerId).getRank();

        for (PermissionAttachmentInfo attachmentInfo : player.getEffectivePermissions()) {
            if (attachmentInfo.getAttachment() == null || attachmentInfo.getAttachment().getPlugin() == null ||
                    !attachmentInfo.getAttachment().getPlugin().equals(Main.getInstance())) {
                continue;
            }

            attachmentInfo.getAttachment().getPermissions().forEach((permission, value) -> {
                attachmentInfo.getAttachment().unsetPermission(permission);
            });
        }

        PermissionAttachment attachment = player.addAttachment(Main.getInstance());

        for (ProfileManager.Rank lowerRank : ProfileManager.Rank.values()) {
            if (lowerRank.getWeight() < rank.getWeight()) {
                for (String permission : lowerRank.getPermissions()) {
                    attachment.setPermission(permission, true);
                }
            }
            player.recalculatePermissions();
        }

        for (String permission : rank.getPermissions()) {
            player.addAttachment(Main.getInstance(), permission, true);
        }
    }
}
