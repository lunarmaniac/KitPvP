package me.egomaniac.kitpvp.ui;


import me.egomaniac.kitpvp.perks.perks.Distortion;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.perks.Perk;
import me.egomaniac.kitpvp.perks.perks.Taunt;
import me.egomaniac.kitpvp.ui.api.InventoryProvider;
import me.egomaniac.kitpvp.ui.api.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Tier2UI extends InventoryProvider {

    public static int tier = 2;

    public Tier2UI() {
        super(CC.translate(Perk.getTierColorByTier(tier) + "Tier 2 Perks"), 27);
    }

    Distortion distortionPerk = new Distortion();
    Taunt tauntPerk = new Taunt();

    @Override
    public void init(Player player) {

        ItemStack distortionItem = new ItemBuilder(Material.COAL)
                .setName(CC.translate(Perk.getTierColorByTier(tier) + distortionPerk.getName()))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .addLoreLine(CC.translate("&7" + distortionPerk.getDescription()))
                //.addLoreLine(CC.translate("&fStatus: " + (bountyHunterPerk.isActive(player) ? "Active" : "Locked")))
                .addLoreLine(CC.translate(""))
                .addLoreLine(CC.translate("&fCost: &a" + distortionPerk.getCost() + " credits"))
                .addLoreLine(CC.translate(""))
                .addLoreLine(CC.translate("&eClick here to purchase this perk."))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .toItemStack();

        ItemStack tauntItem = new ItemBuilder(Material.POISONOUS_POTATO)
                .setName(CC.translate(Perk.getTierColorByTier(tier) + tauntPerk.getName()))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .addLoreLine(CC.translate("&7" + tauntPerk.getDescription()))
                //.addLoreLine(CC.translate("&fStatus: " + (bountyHunterPerk.isActive(player) ? "Active" : "Locked")))
                .addLoreLine(CC.translate(""))
                .addLoreLine(CC.translate("&fCost: &a" + tauntPerk.getCost() + " credits"))
                .addLoreLine(CC.translate(""))
                .addLoreLine(CC.translate("&eClick here to purchase this perk."))
                .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                .toItemStack();

        getInventory().setItem(10, distortionItem);
        getInventory().setItem(11, tauntItem);

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
        //PlayerPerksManager playerPerksManager = new PlayerPerksManager();
        switch (slot) {
            case 10:
                if (!Main.getInstance().playerPerksManager.hasPerk(player, distortionPerk)) {

                    if (credits >= distortionPerk.getCost()) {

                        List<Perk> playerPerks = Main.getInstance().playerPerksManager.getPlayerPerks(player);

                        // Check if the player already has a tier 1 perk equipped
                        for (Perk perk : playerPerks) {
                            if (perk.getTier() == tier) {
                                player.sendMessage(CC.translate("&cYou can only have one Tier 2 perk equipped at a time."));
                                event.setCancelled(true);
                                return;
                            }
                        }

                        Main.getInstance().profileManager.takePlayerCredits(event.getWhoClicked().getUniqueId(), distortionPerk.getCost());
                        Main.getInstance().playerPerksManager.addPerk(player, distortionPerk); // Add the perk to the player
                        ///Bukkit.broadcastMessage(Main.getInstance().playerPerksManager.playerPerks.toString());
                        event.setCancelled(true);
                        PerksUI perksUI = PerksUI.getPlayerPerksUI(player);
                        perksUI.open(player);
                        Main.getInstance().profileManager.updateNameTag(player);
                    } else {
                        event.getWhoClicked().sendMessage(CC.translate("&cYou do not have enough credits to purchase this perk."));
                        event.setCancelled(true);
                    }
                } else {
                    player.sendMessage(CC.translate("&cYou already have the " + distortionPerk.getName() + " perk equipped."));
                    event.setCancelled(true);
                }
                break;
            case 11:
                if (!Main.getInstance().playerPerksManager.hasPerk(player, tauntPerk)) {
                    if (credits >= tauntPerk.getCost()) {

                        List<Perk> playerPerks = Main.getInstance().playerPerksManager.getPlayerPerks(player);

                        // Check if the player already has a tier 1 perk equipped
                        for (Perk perk : playerPerks) {
                            if (perk.getTier() == tier) {
                                player.sendMessage(CC.translate("&cYou can only have one Tier 2 perk equipped at a time."));
                                event.setCancelled(true);
                                return;
                            }
                        }

                        Main.getInstance().profileManager.takePlayerCredits(event.getWhoClicked().getUniqueId(), tauntPerk.getCost());
                        Main.getInstance().playerPerksManager.addPerk(player, tauntPerk); // Add the perk to the player
                        //Bukkit.broadcastMessage(Main.getInstance().playerPerksManager.playerPerks.toString());
                        event.setCancelled(true);
                        PerksUI.getPlayerPerksUI(player).onClose(player);
                        Main.getInstance().profileManager.updateNameTag(player);
                    } else {
                        event.getWhoClicked().sendMessage(CC.translate("&cYou do not have enough credits to purchase this perk."));
                        event.setCancelled(true);
                    }
                } else {
                    player.sendMessage(CC.translate("&cYou already have the " + tauntPerk.getName() + " perk equipped."));
                    event.setCancelled(true);
                }
                break;
            case 15:
                break;
            default:
                event.setCancelled(true);
                break;
        }
    }
}