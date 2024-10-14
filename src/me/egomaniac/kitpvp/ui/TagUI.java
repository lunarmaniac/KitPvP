package me.egomaniac.kitpvp.ui;


import me.egomaniac.kitpvp.managers.ProfileManager;
import me.egomaniac.kitpvp.ui.api.InventoryProvider;
import me.egomaniac.kitpvp.ui.api.ItemBuilder;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.Main;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TagUI extends InventoryProvider {
    public TagUI() {
        super(CC.translate("&b&lSENATIC &8┃ &fSelect a tag"), 18);
    }

    @Override
    public void init(Player player) {
        ProfileManager.Tag playerTag = Main.getInstance().profileManager.getPlayerTag(player.getUniqueId());

        // Create and set the tag items
        for (ProfileManager.Tag tag : ProfileManager.Tag.values()) {
            if (tag == ProfileManager.Tag.NONE) {
                // Handle the "NONE" tag separately
                ItemStack noneTagItem = new ItemBuilder(Material.BARRIER)
                        .setName(CC.RED + "NONE")
                        .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                        .addLoreLine(CC.translate("&eClick to remove your tag"))
                        .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                        .toItemStack();

                if (playerTag == ProfileManager.Tag.NONE) {
                    // Add a glow effect to the "NONE" tag if the player already has it selected
                    noneTagItem.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
                    ItemMeta itemMeta = noneTagItem.getItemMeta();
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    noneTagItem.setItemMeta(itemMeta);
                }

                getInventory().addItem(noneTagItem);
            } else {
                ItemStack tagItem = new ItemBuilder(Material.NAME_TAG)
                        .setName(CC.translate(tag.getPrefix()))
                        .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                        .addLoreLine(CC.translate("&eClick to select this tag"))
                        .addLoreLine(CC.translate("&7&m-------------------------------------------"))
                        .toItemStack();

                if (playerTag == tag) {
                    // Add a glow effect to the selected tag
                    tagItem.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
                    ItemMeta itemMeta = tagItem.getItemMeta();
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    tagItem.setItemMeta(itemMeta);
                }

                getInventory().addItem(tagItem);
            }
        }
    }

    @Override
    public void onClick(ItemStack item, int slot, InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        // Check if the clicked item has a display name
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            String displayName = item.getItemMeta().getDisplayName();
            ProfileManager.Tag clickedTag = null;

            // Find the matching tag based on display name
            for (ProfileManager.Tag tag : ProfileManager.Tag.values()) {
                if (tag == ProfileManager.Tag.NONE) {
                    if (displayName.equals(CC.RED + "NONE")) {
                        clickedTag = tag;
                        break;
                    }
                } else {
                    if (CC.translate(tag.getPrefix()).equals(displayName)) {
                        clickedTag = tag;
                        break;
                    }
                }
            }

            // Check if a valid tag was clicked
            if (clickedTag != null) {
                if (!player.hasPermission("kitpvp.tag." + clickedTag.name().toLowerCase())) {
                    player.sendMessage(CC.RED + "You do not own this tag.");
                    return;
                }

                // Check if the player already has the selected tag
                if (Main.getInstance().profileManager.getPlayerTag(player.getUniqueId()) == clickedTag) {
                    player.closeInventory();
                    return;
                }

                // Set the player's tag
                Main.getInstance().profileManager.setPlayerTag(player.getUniqueId(), clickedTag);

                player.sendMessage(CC.translate("&aYour tag has been set to " + clickedTag.getPrefix()));
                player.closeInventory();
            }
        }
    }
}