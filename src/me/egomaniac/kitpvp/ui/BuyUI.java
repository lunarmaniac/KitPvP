package me.egomaniac.kitpvp.ui;


import me.egomaniac.kitpvp.ui.api.InventoryProvider;
import me.egomaniac.kitpvp.ui.api.ItemBuilder;
import me.egomaniac.kitpvp.utils.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class BuyUI extends InventoryProvider {
    public BuyUI() {
        super(CC.translate("&b&lSENATIC &8┃ &fPurchase ranks"), 27);
    }

    @Override
    public void init(Player player) {

        ItemStack kingItem = new ItemBuilder(Material.WOOL)
                .setDurability((short) 10)
                .setName(CC.translate("&6&lKing Rank"))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .addLoreLine(CC.translate("&7Rank information here."))
                .addLoreLine(CC.translate("&7 * &6/kit king &8- &7Access to a S1, diamond armor loadout."))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .toItemStack();

        ItemStack lordItem = new ItemBuilder(Material.WOOL)
                .setDurability((short) 10)
                .setName(CC.translate("&d&lLord Rank"))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .addLoreLine(CC.translate("&7Rank information here."))
                .addLoreLine(CC.translate("&7 * &d/kit lord &8- &7Access to a S1, P1 loadout."))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .toItemStack();

        ItemStack legendItem = new ItemBuilder(Material.WOOL)
                .setDurability((short) 14)
                .setName(CC.translate("&c&lLegend Rank"))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .addLoreLine(CC.translate("&7Rank information here."))
                .addLoreLine(CC.translate("&7 * &c/kit legend &8- &7Access to a S2, P2 loadout, best on the server."))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .toItemStack();

        getInventory().setItem(14, kingItem);
        getInventory().setItem(15, lordItem);
        getInventory().setItem(16, legendItem);

        ItemStack glassItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        for (int slot = 0; slot < getInventory().getSize(); slot++) {
            if (getInventory().getItem(slot) == null) {
                getInventory().setItem(slot, glassItem);
            }
        }
    }

    @Override
    public void onClick(ItemStack item, int slot, InventoryClickEvent event) {
        event.setCancelled(true);
    }
}