package me.egomaniac.kitpvp.managers;

import me.egomaniac.kitpvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class StaffManager {
    private final HashSet<UUID> staffModePlayers = new HashSet<>();
    private final HashSet<UUID> vanishedPlayers = new HashSet<>();

    public StaffManager() {}

    public void enableStaffMode(Player player) {
        UUID playerUUID = player.getUniqueId();
        staffModePlayers.add(playerUUID);

        saveInventoryToFile(player);

        player.setGameMode(GameMode.CREATIVE);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        initializeModItems(player);

        //TODO: for some super weird reason this method just isnt working here. so using isvanished instead. gay i know.
        //updatePlayerVisibility(player, false);
        Main.getInstance().profileManager.updateNameTag(player);

        if (!isVanished(player)) {
            toggleVanish(player);
        }

    }

    public void disableStaffMode(Player player) {
        UUID playerUUID = player.getUniqueId();
        staffModePlayers.remove(playerUUID);

        restoreInventoryFromFile(player);

        player.setGameMode(GameMode.SURVIVAL);
        Main.getInstance().profileManager.updateNameTag(player);

        if (isVanished(player)) {
            toggleVanish(player);
        }

    }

    public void toggleVanish(Player player) {
        UUID playerUUID = player.getUniqueId();
        if (vanishedPlayers.contains(playerUUID)) {
            vanishedPlayers.remove(playerUUID);
            updatePlayerVisibility(player, true);
        } else {
            vanishedPlayers.add(playerUUID);
            updatePlayerVisibility(player, false);
        }
    }

    private void updatePlayerVisibility(Player player, boolean visible) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            boolean canSeeVanished = onlinePlayer.hasPermission("kitpvp.staff");

            if (visible || !vanishedPlayers.contains(player.getUniqueId()) || canSeeVanished) {
                onlinePlayer.showPlayer(player);
            } else {
                onlinePlayer.hidePlayer(player);
            }
        }
    }

    public boolean isVanished(Player player) {
        return vanishedPlayers.contains(player.getUniqueId());
    }

    public boolean isInStaffMode(Player player) {
        return staffModePlayers.contains(player.getUniqueId());
    }

    public HashSet<UUID> getStaffModePlayers() {
        return staffModePlayers;
    }

    private void initializeModItems(Player player) {
        ItemStack[] items = new ItemStack[9];
        items[2] = createRandomTeleportItem();
        items[6] = createInspectInventoryItem();
        player.getInventory().setContents(items);
    }

    private ItemStack createInspectInventoryItem() {
        ItemStack book = new ItemStack(Material.BOOK);
        ItemMeta meta = book.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§bInspect Inventory");
            book.setItemMeta(meta);
        }
        return book;
    }

    private ItemStack createRandomTeleportItem() {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§bRandom Teleport");
            compass.setItemMeta(meta);
        }
        return compass;
    }


    private void saveInventoryToFile(Player player) {
        UUID playerUUID = player.getUniqueId();
        File file = new File(Main.getInstance().getDataFolder(), playerUUID + "-staff-inventory.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        ItemStack[] inventoryContents = player.getInventory().getContents();
        ItemStack[] armorContents = player.getInventory().getArmorContents();

        config.set("inventory", inventoryContents);
        config.set("armor", armorContents);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void restoreInventoryFromFile(Player player) {
        UUID playerUUID = player.getUniqueId();
        File file = new File(Main.getInstance().getDataFolder(), playerUUID + "-staff-inventory.yml");

        if (file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            List<?> inventoryList = config.getList("inventory");
            List<?> armorList = config.getList("armor");

            if (inventoryList != null && armorList != null) {
                ItemStack[] inventory = inventoryList.stream()
                        .filter(item -> item instanceof ItemStack)
                        .map(item -> (ItemStack) item)
                        .toArray(ItemStack[]::new);

                ItemStack[] armor = armorList.stream()
                        .filter(item -> item instanceof ItemStack)
                        .map(item -> (ItemStack) item)
                        .toArray(ItemStack[]::new);

                player.getInventory().setContents(inventory);
                player.getInventory().setArmorContents(armor);

                file.delete();
            }
        }
    }
}
