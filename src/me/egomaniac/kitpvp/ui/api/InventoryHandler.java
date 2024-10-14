package me.egomaniac.kitpvp.ui.api;

import me.egomaniac.kitpvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryHandler implements Listener
{
    private List<InventoryProvider> inventories;
    
    public InventoryHandler init() {
        this.inventories = new ArrayList<InventoryProvider>();
        Bukkit.getPluginManager().registerEvents(this, Main.instance);
        return this;
    }
    
    public void registerInventory(final InventoryProvider inventory) {
        this.inventories.add(inventory);
    }
    
    @EventHandler
    private void onEvent(final InventoryClickEvent event) {
        try {
            final Inventory inv = event.getClickedInventory();
            final ItemStack item = event.getCurrentItem();
            for (final InventoryProvider provider : this.inventories) {
                if (provider.getTitle().equals(inv.getTitle()) && inv.getType() == InventoryType.CHEST) {
                    provider.onClick(item, event.getRawSlot(), event);
                }
            }
        }
        catch (Exception ignored) {}
    }
}
