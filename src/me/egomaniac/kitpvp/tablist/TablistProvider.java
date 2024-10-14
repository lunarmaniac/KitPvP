package me.egomaniac.kitpvp.tablist;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.tablist.impl.adapter.TabAdapter;
import me.egomaniac.kitpvp.tablist.impl.entry.TabEntry;
import me.egomaniac.kitpvp.tablist.impl.skin.Skin;
import me.egomaniac.kitpvp.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TablistProvider implements TabAdapter {

    @Override
    public String getHeader(Player player) {
        return "&b&lSENATIC \n &7KitPvP";
    }

    @Override
    public String getFooter(Player player) {
        return "&7www.senaticpvp.net";
    }

    @Override
    public List<TabEntry> getLines(Player player) {
        List<TabEntry> lines = new ArrayList<>();


        // First column
        lines.add(new TabEntry(0, 0, CC.translate("     &b&lSENATIC"), 0, null));
        lines.add(new TabEntry(0, 3, CC.translate("&bPlayers"), 0, null));
        lines.add(new TabEntry(0, 4, CC.WHITE + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(), 0, null));


        // Sort the online players based on their rank weight
        List<Player> sortedPlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
        sortedPlayers.sort((p1, p2) -> Integer.compare(Main.getInstance().profileManager.getProfile(p2.getUniqueId()).getRank().getWeight(),
                Main.getInstance().profileManager.getProfile(p1.getUniqueId()).getRank().getWeight()));

        // Calculate the number of rows needed for the online players
        int rowsNeeded = (int) Math.ceil((double) sortedPlayers.size() / 3);

        int lineIndex = 6;
        int rowIndex = 0;
        int playersAdded = 0;

        for (Player onlinePlayer : sortedPlayers) {
            Skin playerSkin = Skin.getPlayer(onlinePlayer);
            int ping = ((CraftPlayer) player).getHandle().ping;
            lines.add(new TabEntry(rowIndex, lineIndex, Main.getInstance().profileManager.getPlayerRank(onlinePlayer.getUniqueId()).getColor() + onlinePlayer.getDisplayName(), ping, Skin.getPlayer(player)));

            lineIndex++;
            playersAdded++;

            // Move to the next row if necessary
            if (playersAdded >= rowsNeeded) {
                playersAdded = 0;
                lineIndex = 6;
                rowIndex++;
            }
        }

        lines.add(new TabEntry(1, 0, CC.translate("       &b&lSEASON I"), 0, null));
        lines.add(new TabEntry(1, 1, CC.GRAY + "         (" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ")", 0, null));
        lines.add(new TabEntry(1, 3, CC.translate("&bYour credits"), 0, null));
        lines.add(new TabEntry(1, 4, CC.WHITE + Main.getInstance().profileManager.getPlayerCredits(player.getUniqueId()), 0, null));

        lines.add(new TabEntry(2, 0, CC.translate("     &b&lSENATIC"), 0, null));
        lines.add(new TabEntry(2, 3, CC.translate("&bYour rank"), 0, null));
        lines.add(new TabEntry(2, 4, Main.getInstance().profileManager.getPlayerRank(player.getUniqueId()).getColor() + Main.getInstance().profileManager.getPlayerRank(player.getUniqueId()).getDisplayName(), 0, null));
        return lines;
    }
}
