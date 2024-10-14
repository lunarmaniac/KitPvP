package me.egomaniac.kitpvp.ui;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.ui.api.InventoryProvider;
import me.egomaniac.kitpvp.ui.api.ItemBuilder;
import me.egomaniac.kitpvp.utils.CC;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.*;

public class ShopUI extends InventoryProvider {
    public ShopUI() {
        super(CC.translate("&b&lSENATIC &8┃ &fShop"), 54);
    }

    private final Map<UUID, Integer> helmetLevels = new HashMap<>();
    private final Map<UUID, Integer> chestplateLevels = new HashMap<>();
    private final Map<UUID, Integer> leggingsLevels = new HashMap<>();
    private final Map<UUID, Integer> bootsLevels = new HashMap<>();
    private final Map<UUID, Integer> swordLevels = new HashMap<>();
    private final Map<UUID, Boolean> isGodApples = new HashMap<>();

    @Override
    public void init(Player player) {
        UUID playerId = player.getUniqueId();
        int helmetLevel = helmetLevels.getOrDefault(playerId, 1);
        int chestplateLevel = chestplateLevels.getOrDefault(playerId, 1);
        int leggingsLevel = leggingsLevels.getOrDefault(playerId, 1);
        int bootsLevel = bootsLevels.getOrDefault(playerId, 1);

        ItemStack helmetItem = createHelmetItem(playerId, helmetLevel);
        ItemStack chestplateItem = createChestplateItem(playerId, chestplateLevel);
        ItemStack leggingsItem = createLeggingsItem(playerId, leggingsLevel);
        ItemStack bootsItem = createBootsItem(playerId, bootsLevel);
        ItemStack speedPotionItem = createSpeedPotion(player.getUniqueId());
        ItemStack healthPotionItem = createHealthPotion(player.getUniqueId());
        ItemStack enderpearlItem = createEnderpearlItem(player.getUniqueId());

        getInventory().setItem(12, helmetItem);
        getInventory().setItem(21, chestplateItem);
        getInventory().setItem(30, leggingsItem);

        getInventory().setItem(31, enderpearlItem);

        getInventory().setItem(39, bootsItem);
        getInventory().setItem(25, speedPotionItem);
        getInventory().setItem(34, healthPotionItem);

        int swordLevel = swordLevels.getOrDefault(playerId, 1);
        ItemStack diamondSword = createDiamondSwordItem(playerId, swordLevel);
        getInventory().setItem(22, diamondSword);

        ItemStack appleItem;
        boolean isGodApple = isGodApples.getOrDefault(playerId, false);
        if (isGodApple) {
            appleItem = createGodAppleItem(playerId);
        } else {
            appleItem = createGoldenAppleItem(playerId);
        }

        getInventory().setItem(20, appleItem);

        ItemStack glassItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);

        for (int slot = 0; slot < getInventory().getSize(); slot++) {
            if (getInventory().getItem(slot) == null) {
                getInventory().setItem(slot, glassItem);
            }
        }
    }

    private ItemStack createSpeedPotion(UUID playerId) {
        int credits = Main.getInstance().economyManager.getCredits(playerId);
        int potionCost = 300; // The cost of the Speed II potion

        // Create a potion of Speed II
        ItemStack speedPotion = new ItemStack(Material.POTION, 1, (short) 8226); // 8226 is the durability for Speed II (extended) in Minecraft 1.8

        // Set item meta and hide potion info from item's lore
        ItemMeta itemMeta = speedPotion.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        speedPotion.setItemMeta(itemMeta);

        // Customize the item's display name and lore
        ItemMeta customMeta = speedPotion.getItemMeta();
        customMeta.setDisplayName(CC.translate("&bSpeed II Potion"));
        customMeta.setLore(Arrays.asList(
                CC.translate("&7&m---------------------------"),
                CC.translate("&eCost: &b" + potionCost + " &ecredits"),
                CC.translate("&7Right-Click to purchase the Speed II potion"),
                CC.translate("&7&m---------------------------"),
                credits >= potionCost ? CC.translate("&aYou have enough credits") : CC.translate("&cYou do not have enough credits")
        ));
        speedPotion.setItemMeta(customMeta);

        return speedPotion;
    }

    private ItemStack createHealthPotion(UUID playerId) {
        int credits = Main.getInstance().economyManager.getCredits(playerId);
        int potionCost = 50; // The cost of the Health II potion

        ItemStack splashPotion = new ItemStack(Material.POTION);
        Potion potion = new Potion(PotionType.INSTANT_HEAL, 2, true); // Healing II
        potion.apply(splashPotion);

        // Customize the item's display name and lore
        ItemMeta customMeta = splashPotion.getItemMeta();
        customMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        customMeta.setDisplayName(CC.translate("&cHealth II Potion"));
        customMeta.setLore(Arrays.asList(
                CC.translate("&7&m---------------------------"),
                CC.translate("&eCost: &b" + potionCost + " &ecredits"),
                CC.translate("&7Right-Click to purchase the Health II potion"),
                CC.translate("&7&m---------------------------"),
                credits >= potionCost ? CC.translate("&aYou have enough credits") : CC.translate("&cYou do not have enough credits")
        ));
        splashPotion.setItemMeta(customMeta);

        return splashPotion;
    }


    private ItemStack createEnderpearlItem(UUID playerId) {
        int credits = Main.getInstance().economyManager.getCredits(playerId);

        int cost = 500;
        ItemStack enderpearl = new ItemStack(Material.ENDER_PEARL);

        // Customize the item's display name and lore
        ItemMeta customMeta = enderpearl.getItemMeta();
        customMeta.setDisplayName(CC.translate("&dEnderpearl"));
        customMeta.setLore(Arrays.asList(
                CC.translate("&7&m---------------------------"),
                CC.translate("&eCost: &b" + cost + " &ecredits"),
                CC.translate("&7&m---------------------------"),
                credits >= cost ? CC.translate("&aYou have enough credits") : CC.translate("&cYou do not have enough credits")
        ));
        enderpearl.setItemMeta(customMeta);

        return enderpearl;
    }

    private ItemStack createHelmetItem(UUID playerId, int level) {
        int credits = Main.getInstance().economyManager.getCredits(playerId);

        int cost = (level == 3 ? 1250 : (level == 2 ? 500 : 250));
        ItemStack diamondHelmet = new ItemStack(Material.DIAMOND_HELMET);

        // Add Protection enchantment
        diamondHelmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, level);

        // Set item meta and hide enchantment info from item's lore
        ItemMeta itemMeta = diamondHelmet.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        diamondHelmet.setItemMeta(itemMeta);

        // Customize the item's display name and lore
        ItemMeta customMeta = diamondHelmet.getItemMeta();
        customMeta.setDisplayName(CC.translate("&bDiamond Helmet &a(Protection " + level + ")"));
        customMeta.setLore(Arrays.asList(
                CC.translate("&7&m---------------------------"),
                CC.translate("&eCost: &b" + cost + " &ecredits"),
                CC.translate("&7Right-Click to cycle through enchantment levels"),
                CC.translate("&7&m---------------------------"),
                credits >= cost ? CC.translate("&aYou have enough credits") : CC.translate("&cYou do not have enough credits")
        ));
        diamondHelmet.setItemMeta(customMeta);

        return diamondHelmet;
    }

    private ItemStack createChestplateItem(UUID playerId, int level) {
        int credits = Main.getInstance().economyManager.getCredits(playerId);

        int cost = (level == 3 ? 2000 : (level == 2 ? 1500 : 500));
        ItemStack diamondChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);

        // Add Protection enchantment
        diamondChestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, level);

        // Set item meta and hide enchantment info from item's lore
        ItemMeta itemMeta = diamondChestplate.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        diamondChestplate.setItemMeta(itemMeta);

        // Customize the item's display name and lore
        ItemMeta customMeta = diamondChestplate.getItemMeta();
        customMeta.setDisplayName(CC.translate("&bDiamond Chestplate &a(Protection " + level + ")"));
        customMeta.setLore(Arrays.asList(
                CC.translate("&7&m---------------------------"),
                CC.translate("&eCost: &b" + cost + " &ecredits"),
                CC.translate("&7Right-Click to cycle through enchantment levels"),
                CC.translate("&7&m---------------------------"),
                credits >= cost ? CC.translate("&aYou have enough credits") : CC.translate("&cYou do not have enough credits")
        ));
        diamondChestplate.setItemMeta(customMeta);

        return diamondChestplate;
    }

    private ItemStack createLeggingsItem(UUID playerId, int level) {
        int credits = Main.getInstance().economyManager.getCredits(playerId);

        int cost = (level == 3 ? 1500 : (level == 2 ? 950 : 600));
        ItemStack diamondLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);

        // Add Protection enchantment
        diamondLeggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, level);

        // Set item meta and hide enchantment info from item's lore
        ItemMeta itemMeta = diamondLeggings.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        diamondLeggings.setItemMeta(itemMeta);

        // Customize the item's display name and lore
        ItemMeta customMeta = diamondLeggings.getItemMeta();
        customMeta.setDisplayName(CC.translate("&bDiamond Leggings &a(Protection " + level + ")"));
        customMeta.setLore(Arrays.asList(
                CC.translate("&7&m---------------------------"),
                CC.translate("&eCost: &b" + cost + " &ecredits"),
                CC.translate("&7Right-Click to cycle through enchantment levels"),
                CC.translate("&7&m---------------------------"),
                credits >= cost ? CC.translate("&aYou have enough credits") : CC.translate("&cYou do not have enough credits")
        ));
        diamondLeggings.setItemMeta(customMeta);

        return diamondLeggings;
    }

    private ItemStack createBootsItem(UUID playerId, int level) {
        int credits = Main.getInstance().economyManager.getCredits(playerId);

        int cost = (level == 3 ? 1250 : (level == 2 ? 500 : 250));
        ItemStack diamondBoots = new ItemStack(Material.DIAMOND_BOOTS);

        // Add Protection enchantment
        diamondBoots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, level);

        // Set item meta and hide enchantment info from item's lore
        ItemMeta itemMeta = diamondBoots.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        diamondBoots.setItemMeta(itemMeta);

        // Customize the item's display name and lore
        ItemMeta customMeta = diamondBoots.getItemMeta();
        customMeta.setDisplayName(CC.translate("&bDiamond Boots &a(Protection " + level + ")"));
        customMeta.setLore(Arrays.asList(
                CC.translate("&7&m---------------------------"),
                CC.translate("&eCost: &b" + cost + " &ecredits"),
                CC.translate("&7Right-Click to cycle through enchantment levels"),
                CC.translate("&7&m---------------------------"),
                credits >= cost ? CC.translate("&aYou have enough credits") : CC.translate("&cYou do not have enough credits")
        ));
        diamondBoots.setItemMeta(customMeta);

        return diamondBoots;
    }
    private ItemStack createDiamondSwordItem(UUID playerId, int level) {
        int credits = Main.getInstance().economyManager.getCredits(playerId);

        int cost = (level == 3 ? 2000 : (level == 2 ? 1500 : 500));
        ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);

        // Add Sharpness enchantment
        diamondSword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, level);

        // Set item meta and hide enchantment info from item's lore
        ItemMeta itemMeta = diamondSword.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        diamondSword.setItemMeta(itemMeta);

        // Customize the item's display name and lore
        ItemMeta customMeta = diamondSword.getItemMeta();
        customMeta.setDisplayName(CC.translate("&bDiamond Sword &a(Sharpness " + level + ")"));
        customMeta.setLore(Arrays.asList(
                CC.translate("&7&m---------------------------"),
                CC.translate("&eCost: &b" + cost + " &ecredits"),
                CC.translate("&7Right-Click to cycle through enchantment levels"),
                CC.translate("&7&m---------------------------"),
                credits >= cost ? CC.translate("&aYou have enough credits") : CC.translate("&cYou do not have enough credits")
        ));
        diamondSword.setItemMeta(customMeta);

        return diamondSword;
    }

    private ItemStack createGoldenAppleItem(UUID playerId) {
        int credits = Main.getInstance().economyManager.getCredits(playerId);
        int appleCost = 50; // The cost of the golden apple

        return new ItemBuilder(Material.GOLDEN_APPLE)
                .setName(CC.translate("&bGolden Apple"))
                .addLoreLine(CC.translate("&7&m---------------------------"))
                .addLoreLine(CC.translate("&eCost: &b" + appleCost + " &ecredits"))
                .addLoreLine(CC.translate("&7Right-Click to toggle between golden apples and god apples"))
                .addLoreLine(CC.translate("&7&m---------------------------"))
                .addLoreLine(credits >= appleCost ? CC.translate("&aYou have enough credits") : CC.translate("&cYou do not have enough credits"))
                .toItemStack();
    }

    private ItemStack createGodAppleItem(UUID playerId) {
        int credits = Main.getInstance().economyManager.getCredits(playerId);
        int appleCost = 3500; // The cost of the enchanted golden apple

        return new ItemBuilder(Material.GOLDEN_APPLE)
                .setName(CC.translate("&6God Apple"))
                .addLoreLine(CC.translate("&7&m---------------------------"))
                .addLoreLine(CC.translate("&eCost: &b" + appleCost + " &ecredits"))
                .addLoreLine(CC.translate("&7Right-Click to toggle between golden apples and god apples"))
                .addLoreLine(CC.translate("&7&m---------------------------"))
                .addLoreLine(credits >= appleCost ? CC.translate("&aYou have enough credits") : CC.translate("&cYou do not have enough credits"))
                .toItemStack();
    }

    private byte getGoldenAppleData(boolean isGodApple) {
        // Data value for different types of golden apples (0 = Golden Apple, 1 = Enchanted Golden Apple)
        return isGodApple ? (byte) 1 : (byte) 0;
    }

    @Override
    public void onClick(ItemStack item, int slot, InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        UUID playerId = player.getUniqueId();
        int credits = Main.getInstance().economyManager.getCredits(playerId);

        switch (slot) {
            case 12: // Diamond Helmet
                if (event.isRightClick()) {
                    int currentLevel = helmetLevels.getOrDefault(playerId, 1);
                    int newLevel = (currentLevel % 3) + 1; // Cycle between 1, 2, 3
                    helmetLevels.put(playerId, newLevel);

                    ItemStack helmetItem = createHelmetItem(playerId, newLevel);
                    getInventory().setItem(12, helmetItem);
                } else if (event.isLeftClick()) {
                    int helmetLevel = helmetLevels.getOrDefault(playerId, 1);
                    int helmetCost = (helmetLevel == 3 ? 1250 : (helmetLevel == 2 ? 500 : 250));

                    if (credits >= helmetCost) {
                        Main.getInstance().profileManager.takePlayerCredits(playerId, helmetCost);

                        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
                        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, helmetLevel);

                        player.getInventory().addItem(helmet);
                    } else {
                        player.sendMessage(CC.translate("&cYou do not have enough credits to purchase this item."));
                    }
                }
                break;
            case 20: // Golden Apples and God Apples
                boolean isGodApple = isGodApples.getOrDefault(playerId, false);
                int appleCost = (isGodApple ? 3500 : 50);

                if (event.isRightClick()) {
                    // Toggle between golden apple and god apple
                    isGodApple = !isGodApple;
                    isGodApples.put(playerId, isGodApple);
                    byte appleData = getGoldenAppleData(isGodApple);

                    ItemStack appleItem;
                    if (isGodApple) {
                        appleItem = createGodAppleItem(playerId);
                    } else {
                        appleItem = createGoldenAppleItem(playerId);
                    }

                    // Set the data value for the apples
                    appleItem.setDurability(appleData);

                    // Preserve the updated lore
                    ItemMeta itemMeta = item.getItemMeta();
                    List<String> lore = appleItem.getItemMeta().getLore();

                    // Set the lore for the apples (updated lore)
                    ItemMeta appleMeta = appleItem.getItemMeta();
                    appleMeta.setLore(lore);
                    appleItem.setItemMeta(appleMeta);

                    // Update the inventory with the new ItemStack based on the current apple type
                    getInventory().setItem(20, appleItem);

                    event.setCancelled(true);
                } else if (event.isLeftClick()) {
                    // Attempt to give the player the selected apple type
                    if (credits >= appleCost) {
                        Main.getInstance().profileManager.takePlayerCredits(playerId, appleCost);

                        ItemStack apple;
                        if (isGodApple) {
                            apple = new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1);
                        } else {
                            apple = new ItemStack(Material.GOLDEN_APPLE);
                        }

                        player.getInventory().addItem(apple);
                    } else {
                        player.sendMessage(CC.translate("&cYou do not have enough credits to purchase this item."));
                    }
                }
                break;
            case 21: // Diamond Chestplate
                if (event.isRightClick()) {
                    int currentLevel = chestplateLevels.getOrDefault(playerId, 1);
                    int newLevel = (currentLevel % 3) + 1; // Cycle between 1, 2, 3
                    chestplateLevels.put(playerId, newLevel);

                    ItemStack chestplateItem = createChestplateItem(playerId, newLevel);
                    getInventory().setItem(21, chestplateItem);
                } else if (event.isLeftClick()) {
                    int chestplateLevel = chestplateLevels.getOrDefault(playerId, 1);
                    int chestplateCost = (chestplateLevel == 3 ? 2000 : (chestplateLevel == 2 ? 1500 : 500));

                    if (credits >= chestplateCost) {
                        Main.getInstance().profileManager.takePlayerCredits(playerId, chestplateCost);

                        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, chestplateLevel);

                        player.getInventory().addItem(chestplate);
                    } else {
                        player.sendMessage(CC.translate("&cYou do not have enough credits to purchase this item."));
                    }
                }
                break;
            case 30: // Diamond Leggings
                if (event.isRightClick()) {
                    int currentLevel = leggingsLevels.getOrDefault(playerId, 1);
                    int newLevel = (currentLevel % 3) + 1; // Cycle between 1, 2, 3
                    leggingsLevels.put(playerId, newLevel);

                    ItemStack leggingsItem = createLeggingsItem(playerId, newLevel);
                    getInventory().setItem(30, leggingsItem);
                } else if (event.isLeftClick()) {
                    int leggingsLevel = leggingsLevels.getOrDefault(playerId, 1);
                    int leggingsCost = (leggingsLevel == 3 ? 1500 : (leggingsLevel == 2 ? 950 : 600));

                    if (credits >= leggingsCost) {
                        Main.getInstance().profileManager.takePlayerCredits(playerId, leggingsCost);

                        ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, leggingsLevel);

                        player.getInventory().addItem(leggings);
                    } else {
                        player.sendMessage(CC.translate("&cYou do not have enough credits to purchase this item."));
                    }
                }
                break;
            case 39: // Diamond Boots
                if (event.isRightClick()) {
                    int currentLevel = bootsLevels.getOrDefault(playerId, 1);
                    int newLevel = (currentLevel % 3) + 1; // Cycle between 1, 2, 3
                    bootsLevels.put(playerId, newLevel);

                    ItemStack bootsItem = createBootsItem(playerId, newLevel);
                    getInventory().setItem(39, bootsItem);
                } else if (event.isLeftClick()) {
                    int bootsLevel = bootsLevels.getOrDefault(playerId, 1);
                    int bootsCost = (bootsLevel == 3 ? 1250 : (bootsLevel == 2 ? 500 : 250));

                    if (credits >= bootsCost) {
                        Main.getInstance().profileManager.takePlayerCredits(playerId, bootsCost);

                        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, bootsLevel);

                        player.getInventory().addItem(boots);
                    } else {
                        player.sendMessage(CC.translate("&cYou do not have enough credits to purchase this item."));
                    }
                }
                break;
            case 22: // Diamond Sword
                if (event.isRightClick()) {
                    int currentLevel = swordLevels.getOrDefault(playerId, 1);
                    int newLevel = (currentLevel % 3) + 1; // Cycle between 1, 2, 3
                    swordLevels.put(playerId, newLevel);

                    ItemStack diamondSword = createDiamondSwordItem(playerId, newLevel);
                    getInventory().setItem(22, diamondSword);
                } else if (event.isLeftClick()) {
                    int swordLevel = swordLevels.getOrDefault(playerId, 1);
                    int swordCost = (swordLevel == 3 ? 2000 : (swordLevel == 2 ? 1500 : 500));

                    if (credits >= swordCost) {
                        Main.getInstance().profileManager.takePlayerCredits(playerId, swordCost);

                        ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
                        diamondSword.addEnchantment(Enchantment.DAMAGE_ALL, swordLevel);

                        player.getInventory().addItem(diamondSword);
                    } else {
                        player.sendMessage(CC.translate("&cYou do not have enough credits to purchase this item."));
                    }
                }
                break;
            case 25: // Speed II Potion
                int speedPotionCost = 300;

                if (event.isLeftClick()) {
                    if (credits >= speedPotionCost) {
                        Main.getInstance().profileManager.takePlayerCredits(playerId, speedPotionCost);

                        ItemStack speedPotion = new ItemStack(Material.POTION, 1, (short) 8226); // 8226 is the durability for Speed II (extended) in Minecraft 1.8

                        player.getInventory().addItem(speedPotion);
                    } else {
                        player.sendMessage(CC.translate("&cYou do not have enough credits to purchase this item."));
                    }
                }
                break;
            case 34: // Health II Potion
                int healthPotionCost = 50;

                if (event.isLeftClick()) {
                    if (credits >= healthPotionCost) {
                        Main.getInstance().profileManager.takePlayerCredits(playerId, healthPotionCost);

                        // Create a new Health II Splash Potion ItemStack without any lore or custom data
                        ItemStack healthPotion = new ItemStack(Material.POTION);
                        Potion potion = new Potion(PotionType.INSTANT_HEAL, 2, true); // Healing II (splash)
                        potion.apply(healthPotion);

                        player.getInventory().addItem(healthPotion);
                    } else {
                        player.sendMessage(CC.translate("&cYou do not have enough credits to purchase this item."));
                    }
                }
                break;
            case 31: // Health II Potion
                int enderpearlCost = 500;

                if (event.isLeftClick()) {
                    if (credits >= enderpearlCost) {
                        Main.getInstance().profileManager.takePlayerCredits(playerId, enderpearlCost);
                        ItemStack enderpearl = new ItemStack(Material.ENDER_PEARL);
                        player.getInventory().addItem(enderpearl);
                    } else {
                        player.sendMessage(CC.translate("&cYou do not have enough credits to purchase this item."));
                    }
                }
                break;
        }

    }

    private boolean hasEmptySlot(Inventory inventory) {
        for (ItemStack item : inventory.getContents()) {
            if (item == null || item.getType() == Material.AIR) {
                return true;
            }
        }
        return false;
    }
}
