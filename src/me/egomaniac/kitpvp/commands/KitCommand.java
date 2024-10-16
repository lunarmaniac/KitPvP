package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.ui.api.ItemBuilder;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KitCommand {
    private final Map<String, Map<UUID, Long>> kitCooldowns = new HashMap<>();

    public KitCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    public int kitPvPCooldown = 10;
    public int kitWeeklyCooldown = 604800;
    public int kitKingCooldown = 1200;
    public int kitLordCooldown = 2400;
    public int kitLegendCooldown = 3600;

    private int getRemainingCooldown(Player player, String kitName, int cooldown) {
        if (!kitCooldowns.containsKey(kitName)) {
            return 0;
        }

        long currentTime = System.currentTimeMillis();
        long lastUsedTime = kitCooldowns.get(kitName).getOrDefault(player.getUniqueId(), 0L);
        int cooldownSeconds = cooldown;

        if (lastUsedTime > 0 && currentTime - lastUsedTime < cooldownSeconds * 1000L) {
            return (int) Math.ceil((cooldownSeconds * 1000L - (currentTime - lastUsedTime)) / 1000.0);
        }

        return 0;
    }

    private boolean hasCooldown(Player player, String kitName, int cooldown) {
        int remainingCooldown = getRemainingCooldown(player, kitName, cooldown);

        return remainingCooldown > 0;
    }

    private void setCooldown(Player player, String kitName, int cooldownSeconds) {
        if (player.hasPermission("kitpvp.admin")) {
            return;
        }

        if (!kitCooldowns.containsKey(kitName)) {
            kitCooldowns.put(kitName, new HashMap<>());
        }

        kitCooldowns.get(kitName).put(player.getUniqueId(), System.currentTimeMillis() + (cooldownSeconds * 1000L));
    }

    private void givePots(Player player) {
        Potion splashPotion = new Potion(PotionType.INSTANT_HEAL, 2);
        splashPotion.setSplash(true);

        ItemStack potionItem = splashPotion.toItemStack(1);
        for (ItemStack inv : player.getInventory().getContents()) {
            if (inv == null) {
                player.getInventory().addItem(potionItem);
            }
        }
    }

    public boolean isHasArmorEquipped(Player player) {
        boolean hasArmorEquipped = true;
        for (ItemStack armorPiece : player.getInventory().getArmorContents()) {
            if (armorPiece == null || armorPiece.getType() == Material.AIR) {
                hasArmorEquipped = false;
                break;
            }
        }
        return hasArmorEquipped;
    }


    public void getKits(Player player) {
        String availableKits = "&eAvailable kits: ";


        if (player.hasPermission("kitpvp.player")) {
            if (hasCooldown(player, "pvp", kitPvPCooldown)) {
                availableKits += "&c";
            } else {
                availableKits += "&a";
            }
            availableKits += "pvp&e, ";
        }

        if (player.hasPermission("kitpvp.player")) {
            if (hasCooldown(player, "weekly", kitWeeklyCooldown)) {
                availableKits += "&c";
            } else {
                availableKits += "&a";
            }
            availableKits += "weekly, ";
        }

        if (player.hasPermission("kitpvp.kit.king")) {
            if (hasCooldown(player, "king", kitKingCooldown)) {
                availableKits += "&c";
            } else {
                availableKits += "&a";
            }
            availableKits += "king&e, ";
        }

        if (player.hasPermission("kitpvp.kit.lord")) {
            if (hasCooldown(player, "lord", kitLordCooldown)) {
                availableKits += "&c";
            } else {
                availableKits += "&a";
            }
            availableKits += "lord&e, ";
        }

        if (player.hasPermission("kitpvp.kit.legend")) {
            if (hasCooldown(player, "legend", kitLegendCooldown)) {
                availableKits += "&c";
            } else {
                availableKits += "&a";
            }
            availableKits += "legend&e, ";
        }

        // Remove the trailing comma and space
        if (availableKits.endsWith(", ")) {
            availableKits = availableKits.substring(0, availableKits.length() - 2);
        }


        player.sendMessage(CC.translate(availableKits));
    }

    @Command(name = "kits", permission = "kitpvp.player", inGameOnly = true)
    public void execute(CommandArgs cmd) {
        final Player player = cmd.getPlayer();
        getKits(player);
    }

    @Command(name = "pots", permission = "kitpvp.admin", inGameOnly = true)
    public void pots(CommandArgs cmd) {
        final Player player = cmd.getPlayer();
        givePots(player);
    }

    @Command(name = "kit", permission = "kitpvp.player", inGameOnly = true)
    public void kitCommand(CommandArgs args) {
        final Player executor = args.getPlayer();

        final String[] arguments = args.getArgs();

        if (!Main.getInstance().spawnManager.getCuboid().isIn(executor)) {
            if (!executor.hasPermission("kitpvp.admin")) {
                executor.sendMessage(CC.translate("&cThis command cannot be used outside of spawn"));
                return;
            }
        }

        if (arguments.length < 1) {
            getKits(executor);
            return;
        }

        String kitName = arguments[0].toLowerCase();
        Player targetPlayer = executor; // Default to the executor if no target player provided

        if (executor.hasPermission("kitpvp.admin")) {
            if (arguments.length > 1) {
                String targetPlayerName = arguments[1];
                targetPlayer = Bukkit.getPlayer(targetPlayerName);

                if (targetPlayer == null || !targetPlayer.isOnline()) {
                    executor.sendMessage(CC.translate("&cPlayer '" + targetPlayerName + "' not found or not online."));
                    return;
                }
                executor.sendMessage(CC.translate("&aYou have given &e" + targetPlayerName + " &akit &e" + kitName));

            }
        }

        switch (kitName) {
            case "pvp":
                if (!executor.hasPermission("kitpvp.admin") && hasCooldown(targetPlayer, "pvp", kitPvPCooldown)) {
                    targetPlayer.sendMessage(CC.translate("&cYou need to wait " + getRemainingCooldown(targetPlayer, "pvp", kitPvPCooldown) + " seconds before using this kit again"));
                } else {
                    if (!executor.hasPermission("kitpvp.admin")) {
                        setCooldown(targetPlayer, "pvp", kitPvPCooldown);
                    }
                    giveKitPvP(targetPlayer);
                }
                break;

            case "weekly":
                if (!executor.hasPermission("kitpvp.admin") && hasCooldown(targetPlayer, "weekly", kitWeeklyCooldown)) {
                    targetPlayer.sendMessage(CC.translate("&cYou need to wait " + getRemainingCooldown(targetPlayer, "weekly", kitWeeklyCooldown) + " seconds before using this kit again"));
                } else {
                    if (!executor.hasPermission("kitpvp.admin")) {
                        setCooldown(targetPlayer, "weekly", kitWeeklyCooldown);
                    }
                    giveKitWeekly(targetPlayer);
                }
                break;

            case "king":
                if (!executor.hasPermission("kitpvp.admin") && hasCooldown(targetPlayer, "king", kitKingCooldown)) {
                    targetPlayer.sendMessage(CC.translate("&cYou need to wait " + getRemainingCooldown(targetPlayer, "king", kitKingCooldown) + " seconds before using this kit again"));
                } else if (!executor.hasPermission("kitpvp.king")) {
                    targetPlayer.sendMessage(CC.translate("&cYou do not have permission to use this kit."));
                } else {
                    if (!executor.hasPermission("kitpvp.admin")) {
                        setCooldown(targetPlayer, "king", kitKingCooldown);
                    }
                    giveKitKing(targetPlayer);
                }
                break;

            case "lord":
                if (!executor.hasPermission("kitpvp.admin") && hasCooldown(targetPlayer, "lord", kitLordCooldown)) {
                    targetPlayer.sendMessage(CC.translate("&cYou need to wait " + getRemainingCooldown(targetPlayer, "lord", kitLordCooldown) + " seconds before using this kit again"));
                } else if (!executor.hasPermission("kitpvp.lord")) {
                    targetPlayer.sendMessage(CC.translate("&cYou do not have permission to use this kit."));
                } else {
                    if (!executor.hasPermission("kitpvp.admin")) {
                        setCooldown(targetPlayer, "lord", kitLordCooldown);
                    }
                    giveKitLord(targetPlayer);
                }
                break;

            case "legend":
                if (!executor.hasPermission("kitpvp.admin") && hasCooldown(targetPlayer, "legend", kitLegendCooldown)) {
                    targetPlayer.sendMessage(CC.translate("&cYou need to wait " + getRemainingCooldown(targetPlayer, "legend", kitLegendCooldown) + " seconds before using this kit again"));
                } else if (!executor.hasPermission("kitpvp.legend")) {
                    targetPlayer.sendMessage(CC.translate("&cYou do not have permission to use this kit."));
                } else {
                    if (!executor.hasPermission("kitpvp.admin")) {
                        setCooldown(targetPlayer, "legend", kitLegendCooldown);
                    }
                    giveKitLegend(targetPlayer);
                }
                break;
            default:
                executor.sendMessage(CC.translate("&cInvalid kit name."));
                getKits(executor);
        }
    }

    public void giveKitPvP(Player player) {

        ItemStack sword = new ItemBuilder(Material.IRON_SWORD)
                .setName(CC.translate("&b&lPvP &f&lSword"))
                .toItemStack();

        ItemStack goldenApples = new ItemBuilder(Material.GOLDEN_APPLE, 8)
                .setName(CC.translate("&bGolden Apple"))
                .toItemStack();

        ItemStack helmet = new ItemBuilder(Material.IRON_HELMET)
                .setName(CC.translate("&b&lPvP &f&lHelmet"))
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addEnchant(Enchantment.DURABILITY, 1)
                .toItemStack();

        ItemStack chestplate = new ItemBuilder(Material.IRON_CHESTPLATE)
                .setName(CC.translate("&b&lPvP &f&lChestplate"))
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addEnchant(Enchantment.DURABILITY, 1)
                .toItemStack();

        ItemStack leggings = new ItemBuilder(Material.IRON_LEGGINGS)
                .setName(CC.translate("&b&lPvP &f&lLeggings"))
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addEnchant(Enchantment.DURABILITY, 1)
                .toItemStack();

        ItemStack boots = new ItemBuilder(Material.IRON_BOOTS)
                .setName(CC.translate("&b&lPvP &f&lBoots"))
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addEnchant(Enchantment.DURABILITY, 1)
                .toItemStack();

        ItemStack bow = new ItemBuilder(Material.BOW)
                .setName(CC.translate("&b&lPvP &f&lBow"))
                .toItemStack();

        ItemStack arrow = new ItemBuilder(Material.ARROW, 16)
                .toItemStack();

        ItemStack steak = new ItemBuilder(Material.COOKED_BEEF, 64)
                .toItemStack();


        if (isHasArmorEquipped(player)) {
            player.getInventory().addItem(sword, goldenApples, bow, arrow, steak);
            player.getInventory().addItem(helmet, chestplate, leggings, boots);
        } else {
            player.getInventory().setArmorContents(new ItemStack[]{boots, leggings, chestplate, helmet});
            player.getInventory().addItem(sword, goldenApples, bow, arrow, steak);
        }

        player.sendMessage(CC.translate("&aYou have received kit &ePvP"));
        player.updateInventory();
    }

    public void giveKitWeekly(Player player) {
        ItemStack sword = new ItemBuilder(Material.IRON_SWORD)
                .addEnchant(Enchantment.DAMAGE_ALL, 2)
                .addEnchant(Enchantment.DURABILITY, 1)
                .setName(CC.translate("&3&lWeekly &f&lSword"))
                .toItemStack();

        ItemStack goldenApples = new ItemBuilder(Material.GOLDEN_APPLE, 16)
                .setName(CC.translate("&bGolden Apple"))
                .toItemStack();

        ItemStack helmet = new ItemBuilder(Material.DIAMOND_HELMET)
                .setName(CC.translate("&3&lWeekly &f&lHelmet"))
                .toItemStack();

        ItemStack chestplate = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .setName(CC.translate("&3&lWeekly &f&lChestplate"))
                .toItemStack();

        ItemStack leggings = new ItemBuilder(Material.DIAMOND_LEGGINGS)
                .setName(CC.translate("&3&lWeekly &f&lLeggings"))
                .toItemStack();

        ItemStack boots = new ItemBuilder(Material.DIAMOND_BOOTS)
                .setName(CC.translate("&3&lWeekly &f&lBoots"))
                .toItemStack();

        ItemStack bow = new ItemBuilder(Material.BOW)
                .setName(CC.translate("&3&lWeekly &f&lbow"))
                .toItemStack();

        ItemStack arrow = new ItemBuilder(Material.ARROW, 32)
                .toItemStack();

        ItemStack steak = new ItemBuilder(Material.COOKED_BEEF, 64)
                .toItemStack();

        if (isHasArmorEquipped(player)) {
            player.getInventory().addItem(sword, goldenApples, bow, arrow, steak);
            player.getInventory().addItem(helmet, chestplate, leggings, boots);
        } else {
            player.getInventory().setArmorContents(new ItemStack[]{boots, leggings, chestplate, helmet});
            player.getInventory().addItem(sword, goldenApples, bow, arrow, steak);
        }
        player.updateInventory();

        player.sendMessage(CC.translate("&aYou have received kit &eWeekly"));
    }

    public void giveKitKing(Player player) {

        ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD)
                .addEnchant(Enchantment.DAMAGE_ALL, 1)
                .addEnchant(Enchantment.DURABILITY, 1)
                .setName(CC.translate("&6&lKing &f&lSword"))
                .toItemStack();

        ItemStack goldenApples = new ItemBuilder(Material.GOLDEN_APPLE, 16)
                .setName(CC.translate("&bGolden Apple"))
                .toItemStack();

        ItemStack helmet = new ItemBuilder(Material.DIAMOND_HELMET)
                .setName(CC.translate("&6&lKing &f&lHelmet"))
                .toItemStack();

        ItemStack chestplate = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .setName(CC.translate("&6&lKing &f&lChestplate"))
                .toItemStack();

        ItemStack leggings = new ItemBuilder(Material.DIAMOND_LEGGINGS)
                .setName(CC.translate("&6&lKing &f&lLeggings"))
                .toItemStack();

        ItemStack boots = new ItemBuilder(Material.DIAMOND_BOOTS)
                .setName(CC.translate("&6&lKing &f&lBoots"))
                .toItemStack();

        ItemStack bow = new ItemBuilder(Material.BOW)
                .addEnchant(Enchantment.ARROW_DAMAGE, 1)
                .setName(CC.translate("&6&lKing &f&lBow"))
                .toItemStack();

        ItemStack arrow = new ItemBuilder(Material.ARROW, 64)
                .toItemStack();

        ItemStack steak = new ItemBuilder(Material.COOKED_BEEF, 64)
                .toItemStack();

        if (isHasArmorEquipped(player)) {
            player.getInventory().addItem(sword, goldenApples, bow, arrow, steak);
            player.getInventory().addItem(helmet, chestplate, leggings, boots);
        } else {
            player.getInventory().setArmorContents(new ItemStack[]{boots, leggings, chestplate, helmet});
            player.getInventory().addItem(sword, goldenApples, bow, arrow, steak);
        }
        player.updateInventory();

        player.sendMessage(CC.translate("&aYou have received kit &eKing"));
    }


    public void giveKitLord(Player player) {

        ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD)
                .addEnchant(Enchantment.DAMAGE_ALL, 1)
                .addEnchant(Enchantment.DURABILITY, 1)
                .setName(CC.translate("&d&lLord &f&lSword"))
                .toItemStack();

        ItemStack goldenApples = new ItemBuilder(Material.GOLDEN_APPLE, 32)
                .setName(CC.translate("&bGolden Apple"))
                .toItemStack();

        ItemStack helmet = new ItemBuilder(Material.DIAMOND_HELMET)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addEnchant(Enchantment.DURABILITY, 1)
                .setName(CC.translate("&d&lLord &f&lHelmet"))
                .toItemStack();

        ItemStack chestplate = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addEnchant(Enchantment.DURABILITY, 1)
                .setName(CC.translate("&d&lLord &f&lChestplate"))
                .toItemStack();

        ItemStack leggings = new ItemBuilder(Material.DIAMOND_LEGGINGS)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addEnchant(Enchantment.DURABILITY, 1)
                .setName(CC.translate("&d&lLord &f&lLeggings"))
                .toItemStack();

        ItemStack boots = new ItemBuilder(Material.DIAMOND_BOOTS)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addEnchant(Enchantment.DURABILITY, 1)
                .addEnchant(Enchantment.PROTECTION_FALL, 1)
                .setName(CC.translate("&d&lLord &f&lBoots"))
                .toItemStack();

        ItemStack bow = new ItemBuilder(Material.BOW)
                .addEnchant(Enchantment.ARROW_DAMAGE, 1)
                .setName(CC.translate("&d&lLord &f&lBow"))
                .toItemStack();

        ItemStack arrow = new ItemBuilder(Material.ARROW, 64)
                .toItemStack();

        ItemStack steak = new ItemBuilder(Material.COOKED_BEEF, 64)
                .toItemStack();

        if (isHasArmorEquipped(player)) {
            player.getInventory().addItem(sword, goldenApples, bow, arrow, steak);
            player.getInventory().addItem(helmet, chestplate, leggings, boots);
        } else {
            player.getInventory().setArmorContents(new ItemStack[]{boots, leggings, chestplate, helmet});
            player.getInventory().addItem(sword, goldenApples, bow, arrow, steak);
        }
        player.updateInventory();

        player.sendMessage(CC.translate("&aYou have received kit &eLord"));
    }

    public void giveKitLegend(Player player) {
        ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD)
                .addEnchant(Enchantment.DAMAGE_ALL, 2)
                .addEnchant(Enchantment.DURABILITY, 2)
                .setName(CC.translate("&c&lLegend &f&lSword"))
                .toItemStack();

        ItemStack goldenApples = new ItemBuilder(Material.GOLDEN_APPLE, 48)
                .setName(CC.translate("&bGolden Apple"))
                .toItemStack();

        ItemStack helmet = new ItemBuilder(Material.DIAMOND_HELMET)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .addEnchant(Enchantment.PROTECTION_PROJECTILE, 1)
                .addEnchant(Enchantment.DURABILITY, 2)
                .setName(CC.translate("&c&lLegend &f&lHelmet"))
                .toItemStack();

        ItemStack chestplate = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .addEnchant(Enchantment.PROTECTION_PROJECTILE, 1)
                .addEnchant(Enchantment.DURABILITY, 2)
                .setName(CC.translate("&c&lLegend &f&lChestplate"))
                .toItemStack();

        ItemStack leggings = new ItemBuilder(Material.DIAMOND_LEGGINGS)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .addEnchant(Enchantment.PROTECTION_PROJECTILE, 1)
                .addEnchant(Enchantment.DURABILITY, 2)
                .setName(CC.translate("&c&lLegend &f&lLeggings"))
                .toItemStack();

        ItemStack boots = new ItemBuilder(Material.DIAMOND_BOOTS)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .addEnchant(Enchantment.PROTECTION_PROJECTILE, 1)
                .addEnchant(Enchantment.DURABILITY, 2)
                .addEnchant(Enchantment.PROTECTION_FALL, 2)
                .setName(CC.translate("&c&lLegend &f&lBoots"))
                .toItemStack();

        ItemStack godApple = new ItemBuilder(Material.GOLDEN_APPLE, 1, (short) 1)
                .setName(CC.translate("&e&lGOD &f&lApple"))
                .toItemStack();

        ItemStack bow = new ItemBuilder(Material.BOW)
                .addEnchant(Enchantment.ARROW_DAMAGE, 2)
                .addEnchant(Enchantment.ARROW_KNOCKBACK, 1)
                .setName(CC.translate("&c&lLegend &f&lBow"))
                .toItemStack();

        ItemStack arrow = new ItemBuilder(Material.ARROW, 64)
                .toItemStack();

        ItemStack steak = new ItemBuilder(Material.COOKED_BEEF, 64)
                .toItemStack();

        if (isHasArmorEquipped(player)) {
            player.getInventory().addItem(sword, goldenApples, godApple, bow, arrow, steak);
            player.getInventory().addItem(helmet, chestplate, leggings, boots);
        } else {
            player.getInventory().setArmorContents(new ItemStack[]{boots, leggings, chestplate, helmet});
            player.getInventory().addItem(sword, goldenApples, godApple, bow, arrow, steak);
        }

        player.updateInventory();

        player.sendMessage(CC.translate("&aYou have received kit &eLegend"));
    }
}