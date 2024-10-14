package me.egomaniac.kitpvp.ui;


import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.ui.api.InventoryProvider;
import me.egomaniac.kitpvp.ui.api.ItemBuilder;
import me.egomaniac.kitpvp.utils.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class EventUI extends InventoryProvider {
    public EventUI() {
        super(CC.translate("&b&lSENATIC &8┃ &fHost an event"), 27);
    }

    @Override
    public void init(Player player) {

        ItemStack juggernautItem = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .setName(CC.translate("&5&lLMS"))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .addLoreLine(CC.translate("&7Last Man Standing ."))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .toItemStack();

        ItemStack sumoItem = new ItemBuilder(Material.LEASH)
                .setName(CC.translate("&d&lSumo Event"))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .addLoreLine(CC.translate("&7Fat people punching."))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .toItemStack();

        ItemStack kothItem = new ItemBuilder(Material.DIAMOND_SWORD)
                .setName(CC.translate("&c&lKoth Event"))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .addLoreLine(CC.translate("&7King of the hill."))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .toItemStack();

        getInventory().setItem(10, juggernautItem);
        getInventory().setItem(13, sumoItem);
        getInventory().setItem(16, kothItem);

        ItemStack glassItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        for (int slot = 0; slot < getInventory().getSize(); slot++) {
            if (getInventory().getItem(slot) == null) {
                getInventory().setItem(slot, glassItem);
            }
        }
    }

    @Override
    public void onClick(ItemStack item, int slot, InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        switch (slot) {
            case 10:
                player.sendMessage(CC.translate("&eThe &cJuggernaut &eevent is currently under development."));
                event.setCancelled(true);
                break;

            case 13:
                Main.getInstance().sumoManager.hostEvent(player);
                event.setCancelled(true);
                break;

            case 16:
                player.sendMessage(CC.translate("&eThe &cKoth &eevent is currently under development."));
                event.setCancelled(true);
                break;

            default:
                event.setCancelled(true);
                break;
        }
    }
}