package me.egomaniac.kitpvp.ui;


import me.egomaniac.kitpvp.ui.api.InventoryProvider;
import me.egomaniac.kitpvp.ui.api.ItemBuilder;
import me.egomaniac.kitpvp.utils.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class KillstreakUI extends InventoryProvider {
    public KillstreakUI() {
        super(CC.translate("&b&lSENATIC &8┃ &fKillstreaks"), 9);
    }

    @Override
    public void init(Player player) {
        ItemStack speedKillstreak = new ItemBuilder(Material.POTION)
                .setDurability((short) 8194) // speed 2
                .setName(CC.translate("&bSpeed Boost"))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .addLoreLine(CC.translate("&7Gives you Speed 3 for 30 seconds."))
                .addLoreLine(CC.translate("&fRequired killstreak: &b5"))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .toItemStack();

        ItemStack godApple = new ItemBuilder(Material.GOLDEN_APPLE, 1, (short) 1)
                .setName(CC.translate("&bGod Apple"))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .addLoreLine(CC.translate("&7Gives you 1 god apple."))
                .addLoreLine(CC.translate("&fRequired killstreak: &b10"))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .toItemStack();

        ItemStack extraCredits = new ItemBuilder(Material.GOLD_NUGGET)
                .setName(CC.translate("&bExtra Credits"))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .addLoreLine(CC.translate("&7Recieve a prize of 1000 credits."))
                .addLoreLine(CC.translate("&fRequired killstreak: &b15"))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .toItemStack();

        getInventory().setItem(0, speedKillstreak);
        getInventory().setItem(1, godApple);
        getInventory().setItem(2, extraCredits);
    }

    @Override
    public void onClick(ItemStack item, int slot, InventoryClickEvent event) {
        event.setCancelled(true);
    }
}