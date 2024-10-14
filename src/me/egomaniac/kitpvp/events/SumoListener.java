package me.egomaniac.kitpvp.events;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.managers.ProfileManager;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class SumoListener implements Listener {

    @EventHandler
    public void onPlayerMoveEventWater(PlayerMoveEvent event){
        Player player = event.getPlayer();
        ProfileManager profile = Main.getInstance().profileManager;

        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            if (profile.getProfile(player.getUniqueId()).isSumoEvent()){
                Material material = player.getLocation().getBlock().getType();
                if (material == Material.STATIONARY_WATER || material == Material.WATER) {
                    Main.getInstance().sumoManager.handleDeath(player);

                }
            }
        }
    }

}
