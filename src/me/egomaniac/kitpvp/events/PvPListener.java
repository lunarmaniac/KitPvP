package me.egomaniac.kitpvp.events;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.perks.Perk;
import me.egomaniac.kitpvp.perks.perks.Distortion;
import me.egomaniac.kitpvp.perks.perks.QuickFix;
import me.egomaniac.kitpvp.perks.perks.SecondChance;
import me.egomaniac.kitpvp.perks.perks.Taunt;
import me.egomaniac.kitpvp.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PvPListener implements Listener {

    @EventHandler
    public void onPvP(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
            // Additional check to exclude NPCs from PvP interactions
            return;
        }


        Player victim = (Player) event.getEntity();
        Player attacker = (Player) event.getDamager();

        if (Main.getInstance().sumoManager.isParticipant(victim) || Main.getInstance().sumoManager.isParticipant(attacker)) {
            if (Main.getInstance().sumoManager.isFightPair(victim) || Main.getInstance().sumoManager.isFightPair(attacker)) {
                event.setCancelled(false);
                return;
            } else {
                event.setCancelled(true);
                return;
            }
        }


        if (Main.getInstance().spawnManager.getCuboid().isIn(victim) && Main.getInstance().spawnManager.getCuboid().isIn(attacker) ||
                !Main.getInstance().spawnManager.getCuboid().isIn(victim) && Main.getInstance().spawnManager.getCuboid().isIn(attacker) ||
                Main.getInstance().spawnManager.getCuboid().isIn(victim) && !Main.getInstance().spawnManager.getCuboid().isIn(attacker)) {

            event.setCancelled(true);
        } else {

            // Check if the attacker has the "distortion" perk
            Distortion distortion = new Distortion();
            boolean hasDistortionPerk = Main.getInstance().playerPerksManager.hasPerk(attacker, distortion);

            // 5% chance for the attacker to give the victim blindness for 10 seconds
            if (hasDistortionPerk && Math.random() <= 0.05) { //0.05
                victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10 * 20, 0));
                attacker.sendMessage("");
                attacker.sendMessage(CC.translate(Perk.getTierColorByTier(distortion.getTier()) + "&lDISTORTION!"));
                attacker.sendMessage(CC.translate("You have inflicted blindness on " + victim.getDisplayName() + "!"));
                attacker.sendMessage("");
            }

            SecondChance secondChance = new SecondChance();
            boolean hasSecondChancePerk = Main.getInstance().playerPerksManager.hasPerk(victim, secondChance);

            // If the attacker has the "Second Chance" perk and dies, give them 5 extra hearts
            if (hasSecondChancePerk&& Math.random() <= 0.05) {
                if (victim.getHealth() - event.getFinalDamage() <= 0) {
                    event.setCancelled(true);
                    victim.setHealth(10);
                    victim.sendMessage("");
                    victim.sendMessage(CC.translate(Perk.getTierColorByTier(secondChance.getTier()) + "&lSECOND CHANCE!"));
                    victim.sendMessage(CC.translate("&7You have been granted an extra 5 hearts!"));
                    victim.sendMessage("");
                }
            }

            Bukkit.getPluginManager().callEvent(new CombatTag(victim, attacker));
            Main.getInstance().combatManager.setCombatTime(victim, 20);
            Main.getInstance().combatManager.setCombatTime(attacker, 20);
            Main.getInstance().combatManager.setCombatSet(victim, true);
            Main.getInstance().combatManager.setCombatSet(attacker, true);

            try {
                Main.getInstance().combatManager.getStartCombatTimer().runTaskTimer(Main.getInstance(), 0L, 20L);
            } catch (IllegalStateException ignored) {
            }
        }
    }

    @EventHandler
    public void onPearl(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().getType() == Material.ENDER_PEARL) {
            Player player = event.getPlayer();
            if (Main.getInstance().enderpearlManager.isCooldown(player)) {
                event.setCancelled(true);
                int cooldownTime = Main.getInstance().enderpearlManager.getCooldownTime(player);
                player.sendMessage(CC.RED + "You're on enderpearl cooldown for another " + cooldownTime + "s.");
            } else {
                Bukkit.getPluginManager().callEvent(new PearlCooldown(event.getPlayer()));
                Main.getInstance().enderpearlManager.setCooldownTime(event.getPlayer(), 20); // Replace 20 with the cooldown time in ticks (1 second = 20 ticks)
                Main.getInstance().enderpearlManager.setEnderpearlSet(event.getPlayer(), true);

                try {
                    Main.getInstance().enderpearlManager.getStartCooldownTimer().runTaskTimer(Main.getInstance(), 0L, 20L);

                } catch (IllegalStateException ignored) {
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            player.spigot().respawn();
        }, 1);

        if (event.getEntity().getKiller() == null) {
            resetKillstreak(event.getEntity());
            return;
        }

        if (player.getKiller() == player) {
            resetKillstreak(event.getEntity());
            return;
        }

        Player killer = player.getKiller();
        killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 2.0f, 1.0f);
        handleKillstreak(killer, player);

        Main.getInstance().combatManager.setCombatSet(player, false);

        if (player.isDead() && killer != null) {
            Main.getInstance().playerPerksManager.clearPerks(player);

            killer.giveExpLevels(3);
            killer.giveExpLevels(3);

            List<String> tauntMessages = new ArrayList<>();
            tauntMessages.add("you are fuckin terrible kid.");
            tauntMessages.add("LOLOL YOU'RE SHIT!");
            tauntMessages.add("did u really just quickie dude.");
            tauntMessages.add("imagine dying like that.");
            tauntMessages.add("quit the game u are dog water");

            Taunt taunt = new Taunt();
            boolean hasTaunt = Main.getInstance().playerPerksManager.hasPerk(killer, taunt);

            // If the attacker has the "Second Chance" perk and dies, give them 5 extra hearts
            if (hasTaunt) {
                Random random = new Random();
                int randomIndex = random.nextInt(tauntMessages.size());
                String message = tauntMessages.get(randomIndex);

                player.sendMessage(CC.translate("&7(From " + Main.getInstance().profileManager.getPlayerRank(killer.getUniqueId()).getColor() + killer.getDisplayName() + "&7) " + message));
                killer.sendMessage(CC.translate("&7(To " + Main.getInstance().profileManager.getPlayerRank(player.getUniqueId()).getColor() + player.getDisplayName() + "&7) " + message));
            }

            int playerBounty = Main.getInstance().profileManager.getProfile(player.getUniqueId()).getBounty();
            if (playerBounty > 0) {
                // Reward the killer with the player's bounty
                Main.getInstance().profileManager.addPlayerCredits(killer.getUniqueId(), playerBounty);
                //killer.sendMessage(CC.translate("&aYou have claimed the bounty of &e" + player.getDisplayName() + "&a. You received " + playerBounty + " credits as a reward."));
                // Deduct the bounty from the killed player
                Main.getInstance().profileManager.getProfile(player.getUniqueId()).setBounty(0);
                //player.sendMessage(CC.translate("&cYour bounty has been claimed by &e" + killer.getDisplayName() + "&c."));

                Bukkit.broadcastMessage(CC.translate(Main.getInstance().profileManager.getPlayerRank(killer.getUniqueId()).getColor() + killer.getDisplayName() + " &ehas claimed the &a" + playerBounty + " &ecredit bounty for " + Main.getInstance().profileManager.getPlayerRank(player.getUniqueId()).getColor() + player.getDisplayName()));

                Main.getInstance().profileManager.updateNameTag(player);
            }

            Main.getInstance().profileManager.addPlayerCredits(killer.getUniqueId(), 100);
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 2.0f, 1.0f);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "crate give " + killer.getDisplayName() + " kill 1");
            killer.sendMessage(CC.translate("&aYou have been rewarded &e100 credits &afor killing &e" + player.getDisplayName()));
            player.sendMessage(CC.translate("&cYou have been killed by &e" + killer.getDisplayName()));

            QuickFix quickFix = new QuickFix();
            boolean hasQuickFixPerk = Main.getInstance().playerPerksManager.hasPerk(killer, quickFix);

            if (hasQuickFixPerk) {
                killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5 * 20, 0));
                killer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5 * 20, 0));
                killer.sendMessage("");
                killer.sendMessage(CC.translate(Perk.getTierColorByTier(quickFix.getTier()) + "&lQUICK FIX!"));
                killer.sendMessage(CC.translate("&7You now have regeneration and resistance!"));
                killer.sendMessage("");
            }

            resetKillstreak(player);
        }
    }

    private void handleKillstreak(Player attacker, Player victim) {
        Main.getInstance().profileManager.updateKillstreak(attacker.getUniqueId());
        int killstreak = Main.getInstance().profileManager.getProfile(attacker.getUniqueId()).getKillstreak();

        if (killstreak == 5) {

            PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, 20 * 30, 2);
            attacker.addPotionEffect(speedEffect);

            Bukkit.broadcastMessage(CC.translate("&e" + attacker.getDisplayName() + " &ahas reached a killstreak of " + killstreak + "!"));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "crate give " + attacker.getDisplayName() + " epic 1");
            attacker.sendMessage(CC.translate("&aYou've recieved a &eSpeed Boost &afor reaching a killstreak of " + killstreak + "!"));

        } else if (killstreak == 10) {

            ItemStack godApple = new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1);
            attacker.getInventory().addItem(godApple);

            Bukkit.broadcastMessage(CC.translate("&e" + attacker.getDisplayName() + " &ahas reached a killstreak of " + killstreak + "!"));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "crate give " + attacker.getDisplayName() + " epic 5");
            attacker.sendMessage(CC.translate("&aYou've recieved a &eGod Apple &afor reaching a killstreak of " + killstreak + "!"));


        } else if (killstreak == 15) {

            Main.getInstance().profileManager.addPlayerCredits(attacker.getUniqueId(), 1000);

            Bukkit.broadcastMessage(CC.translate("&e" + attacker.getDisplayName() + " &ahas reached a killstreak of " + killstreak + "!"));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "crate give " + attacker.getDisplayName() + " mega 1");
            attacker.sendMessage(CC.translate("&aYou've recieved the &e1000 Credits &afor reaching a killstreak of " + killstreak + "!"));

        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;

            if (Main.getInstance().sumoManager.isParticipant(player)) {
                if (Main.getInstance().sumoManager.isFightPair(player)) {
                    event.setCancelled(false);
                    player.setHealth(20);
                }

                event.setCancelled(true);
            }
        }
    }

    private void resetKillstreak(Player player) {
        Main.getInstance().profileManager.resetKillstreak(player.getUniqueId());
    }

    @EventHandler
    public void onArrowHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player) {
            Arrow arrow = (Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player) {
                Player shooter = (Player) arrow.getShooter();
                Player damagedPlayer = (Player) event.getEntity();

                if (shooter == damagedPlayer) {
                    return;
                }

                double trueDistance = shooter.getLocation().distance(damagedPlayer.getLocation());
                double limitedDistance = trueDistance;

                if (limitedDistance > 25) {
                    limitedDistance = 25;
                }

                double additionalDamage = Math.floor(limitedDistance / 5) * 1.5;
                double originalDamage = event.getDamage();
                double newDamage = originalDamage + additionalDamage;

                event.setDamage(newDamage);

                shooter.sendMessage("");
                shooter.sendMessage(CC.RED + "You hit " + damagedPlayer.getName() + " from " + Math.round(trueDistance) + " blocks away.");
                shooter.sendMessage(CC.RED + "You have dealt " + newDamage + " hearts of damage.");
                shooter.sendMessage("");
            }
        }
    }
}
