package me.egomaniac.kitpvp.ui;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.perks.Perk;
import me.egomaniac.kitpvp.ui.api.InventoryProvider;
import me.egomaniac.kitpvp.ui.api.ItemBuilder;
import me.egomaniac.kitpvp.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerksUI extends InventoryProvider {


    private static final Map<Player, PerksUI> playerPerksUIMap = new HashMap<>();
    private static final Map<Player, Tier1UI> playerTier1UIMap = new HashMap<>();
    private static final Map<Player, Tier2UI> playerTier2UIMap = new HashMap<>();
    private static final Map<Player, Tier3UI> playerTier3UIMap = new HashMap<>();

    private Tier1UI getTier1UI(Player player) {
        return playerTier1UIMap.computeIfAbsent(player, p -> new Tier1UI());
    }

    private Tier2UI getTier2UI(Player player) {
        return playerTier2UIMap.computeIfAbsent(player, p -> new Tier2UI());
    }

    private Tier3UI getTier3UI(Player player) {
        return playerTier3UIMap.computeIfAbsent(player, p -> new Tier3UI());
    }

    public static PerksUI getPlayerPerksUI(Player player) {
        return playerPerksUIMap.computeIfAbsent(player, p -> new PerksUI());
    }

    public void onClose(Player player) {
        playerPerksUIMap.remove(player);
        Bukkit.broadcastMessage(playerPerksUIMap.values().toString());
    }
    public PerksUI() {
        super(CC.translate("&b&lSENATIC &8┃ &fPerks"), 27);
    }

    @Override
    public void init(Player player) {
        ItemStack tier1 = createPerkItem(player, 1);
        ItemStack tier2 = createPerkItem(player, 2);
        ItemStack tier3 = createPerkItem(player, 3);

        getInventory().setItem(11, tier1);
        getInventory().setItem(13, tier2);
        getInventory().setItem(15, tier3);

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
        event.setCancelled(true);

        switch (slot) {
            case 11:
                Tier1UI tier1UI = getTier1UI(player);
                tier1UI.open(player);
                break;
            case 13:
                Tier2UI tier2UI = getTier2UI(player);
                tier2UI.open(player);
                break;
            case 15:
                Tier3UI tier3UI = getTier3UI(player);
                tier3UI.open(player);
                break;
            default:
                break;
        }
    }

    private ItemStack createPerkItem(Player player, int tier) {

        List<Perk> playerPerks = Main.getInstance().playerPerksManager.getPlayerPerks(player);

        for (Perk perk : playerPerks) {
            //Bukkit.broadcastMessage(String.valueOf(perk));
            if (perk.getTier() == tier) {
                return new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5)
                        .setName(CC.translate(Perk.getTierColorByTier(tier) + perk.getName()))
                        .addLoreLine(CC.translate("&7Your active Tier " + tier + " perk."))
                        .addLoreLine("")
                        .addLoreLine(CC.translate(Perk.getTierColorByTier(tier) + perk.getDescription())).toItemStack();
            }

        }

        // If no active perk found for this tier, return the empty perk slot
        ItemStack emptyPerkSlot = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 0)
                .setName(CC.translate("&cEmpty Perk Slot"))
                .addLoreLine(CC.translate("&eClick here to select a Tier " + tier + " Perk"))
                .toItemStack();

        return emptyPerkSlot;
    }

}
