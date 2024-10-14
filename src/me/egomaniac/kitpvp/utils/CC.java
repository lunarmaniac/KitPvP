package me.egomaniac.kitpvp.utils;

import java.util.ArrayList;
import java.util.List;

public class CC
{
    public static final String BLUE;
    public static final String AQUA;
    public static final String YELLOW;
    public static final String RED;
    public static final String GRAY;
    public static final String GOLD;
    public static final String GREEN;
    public static final String WHITE;
    public static final String BLACK;
    public static final String BOLD;
    public static final String ITALIC;
    public static final String UNDER_LINE;
    public static final String STRIKE_THROUGH;
    public static final String RESET;
    public static final String MAGIC;
    public static final String DARK_BLUE;
    public static final String DARK_AQUA;
    public static final String DARK_GRAY;
    public static final String DARK_GREEN;
    public static final String DARK_PURPLE;
    public static final String DARK_RED;
    public static final String PINK;
    public static final String MENU_BAR;
    public static final String CHAT_BAR;
    public static final String SB_BAR;

    public static String translate(final String in) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', in);
    }

    public static List<String> translate(final List<String> lines) {
        final List<String> toReturn = new ArrayList<String>();
        for (final String line : lines) {
            toReturn.add(org.bukkit.ChatColor.translateAlternateColorCodes('&', line));
        }
        return toReturn;
    }

    public static List<String> translate(final String[] lines) {
        final List<String> toReturn = new ArrayList<String>();
        for (final String line : lines) {
            if (line != null) {
                toReturn.add(org.bukkit.ChatColor.translateAlternateColorCodes('&', line));
            }
        }
        return toReturn;
    }

    static {
        BLUE = org.bukkit.ChatColor.BLUE.toString();
        AQUA = org.bukkit.ChatColor.AQUA.toString();
        YELLOW = org.bukkit.ChatColor.YELLOW.toString();
        RED = org.bukkit.ChatColor.RED.toString();
        GRAY = org.bukkit.ChatColor.GRAY.toString();
        GOLD = org.bukkit.ChatColor.GOLD.toString();
        GREEN = org.bukkit.ChatColor.GREEN.toString();
        WHITE = org.bukkit.ChatColor.WHITE.toString();
        BLACK = org.bukkit.ChatColor.BLACK.toString();
        BOLD = org.bukkit.ChatColor.BOLD.toString();
        ITALIC = org.bukkit.ChatColor.ITALIC.toString();
        UNDER_LINE = org.bukkit.ChatColor.UNDERLINE.toString();
        STRIKE_THROUGH = org.bukkit.ChatColor.STRIKETHROUGH.toString();
        RESET = org.bukkit.ChatColor.RESET.toString();
        MAGIC = org.bukkit.ChatColor.MAGIC.toString();
        DARK_BLUE = org.bukkit.ChatColor.DARK_BLUE.toString();
        DARK_AQUA = org.bukkit.ChatColor.DARK_AQUA.toString();
        DARK_GRAY = org.bukkit.ChatColor.DARK_GRAY.toString();
        DARK_GREEN = org.bukkit.ChatColor.DARK_GREEN.toString();
        DARK_PURPLE = org.bukkit.ChatColor.DARK_PURPLE.toString();
        DARK_RED = org.bukkit.ChatColor.DARK_RED.toString();
        PINK = org.bukkit.ChatColor.LIGHT_PURPLE.toString();
        MENU_BAR = org.bukkit.ChatColor.GRAY.toString() + org.bukkit.ChatColor.STRIKETHROUGH + "------------------------";
        CHAT_BAR = org.bukkit.ChatColor.GRAY.toString() + org.bukkit.ChatColor.STRIKETHROUGH + "------------------------------------------------";
        SB_BAR = org.bukkit.ChatColor.GRAY.toString() + org.bukkit.ChatColor.STRIKETHROUGH + "----------------------";
    }
}