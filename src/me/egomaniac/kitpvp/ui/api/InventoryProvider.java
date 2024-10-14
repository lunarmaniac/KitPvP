package me.egomaniac.kitpvp.ui.api;

import me.egomaniac.kitpvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class InventoryProvider
{
    private final String title;
    private final int size;
    private Inventory inventory;
    
    public InventoryProvider(final String title, final int size) {
        this.title = title;
        this.size = size;
        Main.getInventoryHandler().registerInventory(this);
    }
    
    public abstract void init(final Player p0);
    
    public abstract void onClick(final ItemStack p0, final int p1, final InventoryClickEvent p2);
    
    public void open(final Player player) {
        this.inventory = Bukkit.createInventory(null, this.size, this.title);
        this.init(player);
        player.openInventory(this.inventory);
    }
    
    public void fill(final ItemStack item) {
        for (int i = 0; i <= this.size - 1; ++i) {
            this.inventory.setItem(i, item);
        }
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
}
