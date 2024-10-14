package me.egomaniac.kitpvp.ui;

import me.egomaniac.kitpvp.ui.api.InventoryProvider;
import me.egomaniac.kitpvp.ui.api.ItemBuilder;
import me.egomaniac.kitpvp.utils.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class HowToPlayUI extends InventoryProvider {

    public HowToPlayUI() {
        super(CC.translate("&b&lSENATIC &8┃ &fHelp"), 27);
    }

    @Override
    public void init(Player player) {
        Inventory inv = getInventory();

        ItemStack kitpvpCommands = new ItemBuilder(Material.BOOK)
                .setLore(
                        CC.translate(""),
                        CC.translate("&8* &3/kit &8&m-&f View your available kits and unlock more by purchasing a rank."),
                        CC.translate("&8* &3/kit <name> &8&m-&f Select a kit for battle."),
                        //CC.translate("&8* &d/crate &8&m-&f Exchange virtual keys for physical keys."),
                        CC.translate("&8* &3/pv <number> &8&m-&f Access your vault, store your items."),
                        CC.translate("&8* &cMore in development."),
                        CC.translate("")
                        )
                .setName(CC.translate("&bKitPvP Commands"))
                .toItemStack();

        ItemStack howPlay = new ItemBuilder(Material.BOOK)
                .setLore(
                        CC.translate(""),
                        CC.translate(" &fThe aim of the server is to fight with other players, to gain kill keys"),
                        CC.translate(" &fabilities, more & progress through the server!"),
                        CC.translate("")

                        )
                .setName(CC.translate("&bHow to play"))
                .toItemStack();

        ItemStack econ = new ItemBuilder(Material.BOOK)
                .setLore(
                        CC.translate(""),
                        CC.translate("&8* &3/credits &8&m-&f Check your credit balance."),
                        CC.translate(""),
                        CC.translate("&fYou can purchase valuable items with your credits!"),
                        CC.translate("&fJust look for the shop at spawn."),
                        //CC.translate("&8* &d/money pay <name> &8&m-&f Send other players money."),
                        //CC.translate("&8* &d/money check <name> &8&m-&f See the balance of others"),
                        //CC.translate("&8* &d/money top &8&m-&f See the players with the highest balance."),
                        CC.translate("")

                        )
                .setName(CC.translate("&bEconomy Commands"))
                .toItemStack();

        inv.setItem(11, kitpvpCommands);
        inv.setItem(13, howPlay);
        inv.setItem(15, econ);
    }

    @Override
    public void onClick(ItemStack item, int slot, InventoryClickEvent event) {
        event.setCancelled(true);
    }
}