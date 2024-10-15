package me.egomaniac.kitpvp.events;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.MOTD;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.util.Vector;

public class ServerListener implements Listener {

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        //                &7▪ &b&lMINEHEAVEN &7&m--&r &fKitPvP &7▪
        event.setMotd(CC.translate("&b&lSENATIC \n&7&o" + MOTD.motdString));
    }

    @EventHandler
    public void format(AsyncPlayerChatEvent event) {
        event.setMessage(event.getMessage());

//        for (String entry : filter) {
//            if (event.getMessage().contains(entry.toLowerCase())) {    // code doesnt work.
//                event.getPlayer().sendMessage(CC.RED + "Your messaged has been canceled due to containing a filtered word.");
//                event.setCancelled(true);
//            }
//
//        }
        String prefix = Main.getInstance().profileManager.getPlayerRank(event.getPlayer().getUniqueId()).getPrefix();
        String tag = Main.getInstance().profileManager.getPlayerTag(event.getPlayer().getUniqueId()).getPrefix();
        event.setFormat(CC.translate(tag + prefix + event.getPlayer().getDisplayName() + "&7: &f") + "%2$s");
    }

    @EventHandler
    public void onPlayerAdvancementDone(PlayerAchievementAwardedEvent event) { // stop achievements
        event.setCancelled(true);
    }

    // skidded from bow boost
    @EventHandler
    public void onBow(final EntityShootBowEvent e) {
        final LivingEntity entity = e.getEntity();
        final Vector dir = entity.getLocation().getDirection();
        final Arrow a = (Arrow)e.getProjectile();
        final double speed = a.getVelocity().length();
        final Vector vel = dir.multiply(speed);
        a.setVelocity(vel);
    }

    // skidded from bow boost
    @EventHandler
    public void onVelo(final PlayerVelocityEvent e) {
        final Player p = e.getPlayer();
        final Vector velo = e.getVelocity();
        final EntityDamageEvent event = p.getLastDamageCause();
        final Entity damager;
        final Arrow a;
        if (event != null && !event.isCancelled() && event instanceof EntityDamageByEntityEvent && (damager = ((EntityDamageByEntityEvent) event).getDamager()) instanceof Arrow && (a = (Arrow) damager).getShooter().equals(p)) {
            final double speed = Math.sqrt(velo.getX() * velo.getX() + velo.getZ() * velo.getZ());
            final Vector dir = a.getLocation().getDirection().normalize();
            final Vector newvelo = new Vector(dir.getX() * speed * -2.0, velo.getY(), dir.getZ() * speed * 2.0);
            e.setVelocity(newvelo);
        }
    }

// skidded from fastpots
    @EventHandler
    void onProjectileLaunch(final ProjectileLaunchEvent event) {
        if (event.getEntityType() == EntityType.SPLASH_POTION) {
            final Projectile projectile = event.getEntity();
            if (projectile.getShooter() instanceof Player && ((Player)projectile.getShooter()).isSprinting()) {
                final Vector velocity = projectile.getVelocity();
                velocity.setY(velocity.getY() - 0.7);
                projectile.setVelocity(velocity);
            }
        }
    }

    // skidded from fastpots
    @EventHandler
    void onPotionSplash(final PotionSplashEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            final Player shooter = (Player)event.getEntity().getShooter();
            if (shooter.isSprinting() && event.getIntensity(shooter) > 2.0) {
                event.setIntensity(shooter, 1.0);
            }
        }
    }

    // idk why im putting these soup things here i just want to test a temp kit

    @EventHandler
    public void onSoupConsumption(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getItemInHand().getType() == Material.MUSHROOM_SOUP && player.getHealth() < 19.5) {
            player.setHealth(Math.min(player.getHealth() + 7.0, 20.0));
            player.getItemInHand().setType(Material.BOWL);
            player.updateInventory();
        }
    }

    @EventHandler()
    public void onLoseFood(FoodLevelChangeEvent event) {
        if (Main.getInstance().spawnManager.getCuboid().isIn((Player) event.getEntity())) {
            event.setCancelled(true);
        }
        if (Main.getInstance().sumoManager.isParticipant(((Player) event.getEntity()).getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(e.toWeatherState());
    }

    @EventHandler
    public void onBowlDrop(PlayerDropItemEvent event) {
        Material drop = event.getItemDrop().getItemStack().getType();

        switch (drop) {
            case BOWL:
            case COOKED_BEEF:
                event.getItemDrop().remove();
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }
}
