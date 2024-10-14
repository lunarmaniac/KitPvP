package me.egomaniac.kitpvp.perks;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerPerksManager {
    public Map<Player, List<Perk>> playerPerks;

    public PlayerPerksManager() {
        this.playerPerks = new HashMap<>();
    }

    public void addPerk(Player player, Perk perk) {
        playerPerks.computeIfAbsent(player, k -> new ArrayList<>()).add(perk);
    }

    public void removePerk(Player player, Perk perk) {
        playerPerks.getOrDefault(player, new ArrayList<>()).remove(perk);
    }

    public boolean hasPerk(Player player, Perk perk) {
        List<Perk> playerPerkList = playerPerks.get(player);
        return playerPerkList != null && playerPerkList.contains(perk);
    }

    public void clearPerks(Player player) {
        playerPerks.getOrDefault(player, new ArrayList<>()).clear();
    }

    public List<Perk> getPlayerPerks(Player player) {
        return playerPerks.getOrDefault(player, new ArrayList<>());
    }

}
