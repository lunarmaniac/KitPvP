package me.egomaniac.kitpvp.events;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemListener implements Listener {

    @EventHandler
    public void onItemSpawn(PlayerDropItemEvent event) {
        Item item = event.getItemDrop();
        //item.remove();
    }
}
