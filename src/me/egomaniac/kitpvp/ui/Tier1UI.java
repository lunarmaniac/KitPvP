package me.egomaniac.kitpvp.ui;


import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.perks.Perk;
import me.egomaniac.kitpvp.perks.perks.QuickFix;
import me.egomaniac.kitpvp.ui.api.InventoryProvider;
import me.egomaniac.kitpvp.ui.api.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Tier1UI extends InventoryProvider {
    QuickFix quickFix = new QuickFix();

    public static int tier = 1;

    public Tier1UI() {
        super(CC.translate(Perk.getTierColorByTier(tier) + "Tier 1 Perks"), 27);
    }



    @Override
    public void init(Player player) {

        ItemStack quickFixItem = new ItemBuilder(Material.DIAMOND_SWORD)
                .setName(CC.translate(Perk.getTierColorByTier(tier) + quickFix.getName()))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .addLoreLine(CC.translate("&7" + quickFix.getDescription()))
                //.addLoreLine(CC.translate("&fStatus: " + (bountyHunterPerk.isActive(player) ? "Active" : "Locked")))
                .addLoreLine(CC.translate(""))
                .addLoreLine(CC.translate("&fCost: &a" + quickFix.getCost() + " credits"))
                .addLoreLine(CC.translate(""))
                .addLoreLine(CC.translate("&eClick here to purchase this perk."))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .toItemStack();

        getInventory().setItem(10, quickFixItem);

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
        int credits = Main.getInstance().economyManager.getCredits(event.getWhoClicked().getUniqueId());

        switch (slot) {
            case 10:
                if (!Main.getInstance().playerPerksManager.hasPerk(player, quickFix)) {
                    if (credits >= quickFix.getCost()) {
                        List<Perk> playerPerks = Main.getInstance().playerPerksManager.getPlayerPerks(player);

                        // Check if the player already has a tier 1 perk equipped
                        for (Perk perk : playerPerks) {
                            if (perk.getTier() == tier) {
                                player.sendMessage(CC.translate("&cYou can only have one Tier 1 perk equipped at a time."));
                                event.setCancelled(true);
                                return;
                            }
                        }

                        Main.getInstance().profileManager.takePlayerCredits(player.getUniqueId(), quickFix.getCost());
                        Main.getInstance().playerPerksManager.addPerk(player, quickFix);
                        //Bukkit.broadcastMessage(Main.getInstance().playerPerksManager.playerPerks.toString());
                        event.setCancelled(true);
                        final PerksUI perksUI = new PerksUI();

                        perksUI.open(player);
                    } else {
                        player.sendMessage(CC.translate("&cYou do not have enough credits to purchase this perk."));
                        event.setCancelled(true);
                    }
                } else {
                    player.sendMessage(CC.translate("&cYou already have the " + quickFix.getName() + " perk equipped."));
                    event.setCancelled(true);
                }
                break;
            // Handle other slots and perks similarly for other tiers...
            case 13:
                event.setCancelled(true);

                break;
            case 15:
                event.setCancelled(true);

                break;
            default:
                event.setCancelled(true);
                break;
        }
    }
}