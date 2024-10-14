package me.egomaniac.kitpvp.ui;


import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.perks.Perk;
import me.egomaniac.kitpvp.perks.perks.Incognito;
import me.egomaniac.kitpvp.perks.perks.SecondChance;
import me.egomaniac.kitpvp.ui.api.InventoryProvider;
import me.egomaniac.kitpvp.ui.api.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Tier3UI extends InventoryProvider {
    public static int tier = 3;

    public Tier3UI() {
        super(CC.translate(Perk.getTierColorByTier(tier) + "Tier 3 Perks"), 27);
    }

    Incognito incognito = new Incognito();
    SecondChance secondChance = new SecondChance();

    @Override
    public void init(Player player) {

        ItemStack distortionItem = new ItemBuilder(Material.DIAMOND_SWORD)
                .setName(CC.translate(Perk.getTierColorByTier(tier) + incognito.getName()))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .addLoreLine(CC.translate("&7" + incognito.getDescription()))
                .addLoreLine(CC.translate(""))
                .addLoreLine(CC.translate("&fCost: &a" + incognito.getCost() + " credits"))
                .addLoreLine(CC.translate(""))
                .addLoreLine(CC.translate("&eClick here to purchase this perk."))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .toItemStack();

        ItemStack secondChanceItem = new ItemBuilder(Material.REDSTONE)
                .setName(CC.translate(Perk.getTierColorByTier(tier) + secondChance.getName()))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .addLoreLine(CC.translate("&7" + secondChance.getDescription()))
                .addLoreLine(CC.translate(""))
                .addLoreLine(CC.translate("&fCost: &a" + secondChance.getCost() + " credits"))
                .addLoreLine(CC.translate(""))
                .addLoreLine(CC.translate("&eClick here to purchase this perk."))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .toItemStack();

        getInventory().setItem(10, distortionItem);
        getInventory().setItem(11, secondChanceItem);

        ItemStack glassItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        for (int slot = 0; slot < getInventory().getSize(); slot++) {
            if (getInventory().getItem(slot) == null) {
                getInventory().setItem(slot, glassItem);
            }
        }

    }

    @Override
    public void onClick(ItemStack item, int slot, InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked(); // Retrieve the player from the event
        int credits = Main.getInstance().economyManager.getCredits(event.getWhoClicked().getUniqueId());

        switch (slot) {
            case 10:
                if (!Main.getInstance().playerPerksManager.hasPerk(player, incognito)) {

                    if (credits >= incognito.getCost()) {

                        List<Perk> playerPerks = Main.getInstance().playerPerksManager.getPlayerPerks(player);

                        // Check if the player already has a tier 1 perk equipped
                        for (Perk perk : playerPerks) {
                            if (perk.getTier() == tier) {
                                player.sendMessage(CC.translate("&cYou can only have one Tier 3 perk equipped at a time."));
                                event.setCancelled(true);
                                return;
                            }
                        }

                        Main.getInstance().profileManager.takePlayerCredits(event.getWhoClicked().getUniqueId(), incognito.getCost());
                        Main.getInstance().playerPerksManager.addPerk(player, incognito);
                        ///Bukkit.broadcastMessage(Main.getInstance().playerPerksManager.playerPerks.toString());
                        event.setCancelled(true);
                        PerksUI.getPlayerPerksUI(player).onClose(player);
                        player.sendMessage("");
                        player.sendMessage(CC.translate(Perk.getTierColorByTier(incognito.getTier()) + "&lINCOGNITO!"));
                        player.sendMessage(CC.translate("&7Your bounty is now hidden from all players."));
                        player.sendMessage("");
                    } else {
                        event.getWhoClicked().sendMessage(CC.translate("&cYou do not have enough credits to purchase this perk."));
                        event.setCancelled(true);
                    }
                } else {
                    player.sendMessage(CC.translate("&cYou already have the " + incognito.getName() + " perk equipped."));
                    event.setCancelled(true);
                }

                break;
            case 11:
                if (!Main.getInstance().playerPerksManager.hasPerk(player, secondChance)) {
                    if (credits >= secondChance.getCost()) {

                        List<Perk> playerPerks = Main.getInstance().playerPerksManager.getPlayerPerks(player);

                        // Check if the player already has a tier 1 perk equipped
                        for (Perk perk : playerPerks) {
                            if (perk.getTier() == tier) {
                                player.sendMessage(CC.translate("&cYou can only have one Tier 3 perk equipped at a time."));
                                event.setCancelled(true);
                                return;
                            }
                        }

                        Main.getInstance().profileManager.takePlayerCredits(event.getWhoClicked().getUniqueId(), secondChance.getCost());
                        Main.getInstance().playerPerksManager.addPerk(player, secondChance);
                        ///Bukkit.broadcastMessage(Main.getInstance().playerPerksManager.playerPerks.toString());
                        event.setCancelled(true);
                        PerksUI perksUI = PerksUI.getPlayerPerksUI(player);
                        perksUI.open(player);
                    } else {
                        event.getWhoClicked().sendMessage(CC.translate("&cYou do not have enough credits to purchase this perk."));
                        event.setCancelled(true);
                    }
                } else {
                    player.sendMessage(CC.translate("&cYou already have the " + secondChance.getName() + " perk equipped."));
                    event.setCancelled(true);
                }
                // implement here
                break;
            case 15:
                break;
            default:
                event.setCancelled(true);
                break;
        }
    }
}