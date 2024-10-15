package me.egomaniac.kitpvp.managers;

import com.nametagedit.plugin.NametagEdit;
import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.perks.perks.Incognito;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileManager {
    public final Map<UUID, Profile> profiles;
    private final FileConfiguration profileConfig;
    private final File profileFile;

    public ProfileManager() {
        profiles = new HashMap<>();
        profileFile = new File(Main.getInstance().getDataFolder(), "profiles.yml");
        profileConfig = YamlConfiguration.loadConfiguration(profileFile);
        loadProfiles();
    }

    public Profile getProfile(UUID playerId) {
        if (profiles.containsKey(playerId)) {
            return profiles.get(playerId);
        } else {
            return createDefaultProfile(playerId);
        }
    }

    public void saveProfile(Profile profile) {
        profiles.put(profile.getPlayerId(), profile);
        profileConfig.set(profile.getPlayerId().toString() + ".rank", profile.getRank().name());
        profileConfig.set(profile.getPlayerId().toString() + ".tag", profile.getTag().name());
        profileConfig.set(profile.getPlayerId().toString() + ".credits", profile.getCredits());
        profileConfig.set(profile.getPlayerId().toString() + ".killstreak", profile.getKillstreak());
        profileConfig.set(profile.getPlayerId().toString() + ".highestkillstreak", profile.getHighestKillstreak());
        profileConfig.set(profile.getPlayerId().toString() + ".bounty", profile.getBounty());
        profileConfig.set(profile.getPlayerId().toString() + ".togglepm", profile.isTogglepm());

        try {
            profileConfig.save(profileFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateNameTag(Player player) {
        if (NametagEdit.getInstance().isEnabled()) {

            boolean hasIncognitoPerk = Main.getInstance().playerPerksManager.hasPerk(player, new Incognito());

            if (hasIncognitoPerk) {
                // If the player has the "Incognito" perk, don't show the bountyDisplay
                NametagEdit.getApi().setNametag(player, String.valueOf(Main.getInstance().profileManager.getPlayerRank(player.getUniqueId()).getColor()), "");
            } else {
                // If the player doesn't have the "Incognito" perk, show the bountyDisplay
                String bountyDisplay = "";
                double bounty = Main.getInstance().profileManager.getProfile(player.getUniqueId()).getBounty(); // Replace this with the method to get the player's bounty

                if (bounty > 0) {
                    if (bounty >= 1000000) {
                        bountyDisplay = String.format("%.1fM", bounty / 1000000.0);
                    } else if (bounty >= 1000) {
                        bountyDisplay = String.format("%.1fK", bounty / 1000.0);
                    } else {
                        bountyDisplay = String.valueOf((int) bounty);
                    }
                    bountyDisplay = " &6&l" + bountyDisplay;
                }
                // Bukkit.broadcastMessage(player.getDisplayName()); // return nickname
                // String displayName = player.getDisplayName();

                // NametagEdit.getInstance().getManager().setNametag("test", String.valueOf(Main.getInstance().profileManager.getPlayerRank(player.getUniqueId()).getColor()), CC.translate(bountyDisplay));

                if (Main.getInstance().staffManager.isVanished(player)) {
                    bountyDisplay = bountyDisplay + " &7[V&7]";
                }

                NametagEdit.getApi().setNametag(player, String.valueOf(Main.getInstance().profileManager.getPlayerRank(player.getUniqueId()).getColor()), bountyDisplay);
            }
        }
    }

    public Rank getPlayerRank(UUID playerId) {
        Profile profile = getProfile(playerId);
        return profile.getRank();
    }

    public void setPlayerRank(UUID playerId, Rank rank) {
        Profile profile = getProfile(playerId);
        profile.setRank(rank);
        saveProfile(profile);
    }

    public Tag getPlayerTag(UUID playerId) {
        Profile profile = getProfile(playerId);
        return profile.getTag();
    }

    public void setPlayerTag(UUID playerId, Tag tag) {
        Profile profile = getProfile(playerId);
        profile.setTag(tag);
        saveProfile(profile);
    }

    public int getPlayerCredits(UUID playerId) {
        Profile profile = getProfile(playerId);
        return profile.getCredits();
    }

    public void setPlayerCredits(UUID playerId, int credits) {
        Profile profile = getProfile(playerId);
        profile.setCredits(credits);
        saveProfile(profile);
    }

    public void addPlayerCredits(UUID playerId, int credits) {
        Profile profile = getProfile(playerId);
        profile.setCredits(getPlayerCredits(playerId) + credits);
        saveProfile(profile);
    }

    public void takePlayerCredits(UUID playerId, int credits) {
        Profile profile = getProfile(playerId);
        profile.setCredits(getPlayerCredits(playerId) - credits);
        saveProfile(profile);
    }

    public void updateKillstreak(UUID playerId) {
        Profile profile = getProfile(playerId);
        profile.setKillstreak(profile.getKillstreak() + 1);
        if (profile.getKillstreak() > profile.getHighestKillstreak()) {
            profile.setHighestKillstreak(profile.getKillstreak());
        }
        saveProfile(profile);
    }

    public void resetKillstreak(UUID playerId) {
        Profile profile = getProfile(playerId);
        profile.setKillstreak(0);
        saveProfile(profile);
    }

    private Profile createDefaultProfile(UUID playerId) {
        // Check if the player already has a profile

        if (profiles.containsKey(playerId)) {
            return profiles.get(playerId);
        }

        // Create a default profile for a player
        Profile profile = new Profile(playerId, Rank.DEFAULT, Tag.NONE, 0, 0, 0, 0,true);
        saveProfile(profile);
        profiles.put(playerId, profile);
        return profile;
    }

    private void loadProfiles() {
        if (profileFile.exists()) {
            for (String key : profileConfig.getKeys(false)) {
                UUID playerId = UUID.fromString(key);
                String rankName = profileConfig.getString(key + ".rank");
                String tagName = profileConfig.getString(key + ".tag");

                int credits = profileConfig.getInt(key + ".credits");
                int killstreak = profileConfig.getInt(key + ".killstreak");
                int highestKillstreak = profileConfig.getInt(key + ".highestkillstreak");
                int bounty = profileConfig.getInt(key + ".bounty");
                boolean togglepm = profileConfig.getBoolean(key + ".togglepm");

                Rank rank = Rank.valueOf(rankName);
                Tag tag = Tag.valueOf(tagName);

                if (rank != null && tag != null) {
                    Profile profile = new Profile(playerId, rank, tag, credits, killstreak, highestKillstreak, bounty, togglepm);
                    profiles.put(playerId, profile);
                }

            }
        }
    }

    public static class Profile {
        private final UUID playerId;
        private Rank rank;
        private Tag tag;
        private int credits;
        private int killstreak;
        private int highestKillstreak;
        private boolean togglepm;
        private Player lastMessager = null;
        private int bounty;
        private boolean isTeleporting;
        private boolean sumoEvent;

        public Profile(UUID playerId, Rank rank, Tag tag, int credits, int killstreak, int highestKillstreak, int bounty, boolean togglepm) {
            this.playerId = playerId;
            this.rank = rank;
            this.tag = tag;
            this.credits = credits;
            this.killstreak = killstreak;
            this.highestKillstreak = highestKillstreak;
            this.bounty = bounty;
            this.togglepm = togglepm;
        }

        public int getBounty() {
            return bounty;
        }

        public void setBounty(int bounty) {
            this.bounty = bounty;
        }

        public void addBounty(int amount) {
            this.bounty += amount;
        }

        public int getCredits() {
            return credits;
        }

        public void setCredits(int credits) {
            this.credits = credits;
        }

        public UUID getPlayerId() {
            return playerId;
        }

        public Rank getRank() {
            return rank;
        }

        public void setRank(Rank rank) {
            this.rank = rank;
        }

        public Tag getTag() {
            return tag;
        }

        public void setTag(Tag tag) {
            this.tag = tag;
        }

        public int getKillstreak() {
            return killstreak;
        }

        public void setKillstreak(int killstreak) {
            this.killstreak = killstreak;
        }

        public int getHighestKillstreak() {
            return highestKillstreak;
        }


        public void setHighestKillstreak(int highestKillstreak) {
            this.highestKillstreak = highestKillstreak;
        }

        public boolean isTogglepm() {
            return togglepm;
        }

        public void setTogglepm(boolean togglepm) {
            this.togglepm = togglepm;
        }

        public Player getLastMessager() {
            return lastMessager;
        }

        public void setLastMessager(Player lastmessager) {
            this.lastMessager = lastmessager;
        }

        public boolean isTeleporting() {
            return isTeleporting;
        }

        public void setTeleporting(boolean isteleporting) {
            this.isTeleporting = isteleporting;
        }

        public boolean isSumoEvent() {
            return sumoEvent;
        }

        public void setSumoEvent(boolean sumoEvent) {
            this.sumoEvent = sumoEvent;
        }

    }

    public enum Rank {
        DEFAULT("Default", "&a", ChatColor.GREEN, 1, "kitpvp.player"),
        KING("King", "&7[&6&lKING&7] &6", ChatColor.GOLD, 2, "kitpvp.kit.king"),
        LORD("Lord", "&7[&d&lLORD&7] &d", ChatColor.LIGHT_PURPLE, 3, "kitpvp.kit.lord"),
        LEGEND("Legend", "&7[&c&lLEGEND&7] &c", ChatColor.RED, 4, "kitpvp.kit.legend"),
        MOD("Mod", "&7[&3Mod&7] &3", ChatColor.DARK_AQUA, 5, "kitpvp.staff"),
        ADMIN("Admin", "&7[&4Admin&7] &4", ChatColor.DARK_RED, 6, "kitpvp.admin"),
        DEVELOPER("Developer", "&7[&bDeveloper&7] &b", ChatColor.AQUA, 7, "*"),
        OWNER("Owner", "&7[&4Owner&7] &4", ChatColor.DARK_RED, 8, "*");

        private final String displayName;
        private final String prefix;
        private final ChatColor color;
        private final int weight;
        private final String[] permissions;


        Rank(String displayName, String prefix, ChatColor color, int weight, String... permissions) {
            this.displayName = displayName;
            this.prefix = prefix;
            this.color = color;
            this.weight = weight;
            this.permissions = permissions;

        }

        public String getDisplayName() {
            return displayName;
        }

        public String getPrefix() {
            return prefix;
        }

        public ChatColor getColor() {
            return color;
        }

        public int getWeight() {
            return weight;
        }

        public String[] getPermissions() {
            return permissions;
        }

        //need to make inheritance work

        public boolean isHigherThan(Rank otherRank) {
            return this.weight > otherRank.weight;
        }

        public boolean isInheritedFrom(Rank otherRank) {
            return this.weight >= otherRank.weight;
        }

    }

    public enum Tag {
        NONE("", "NONE"),
        STAR(" &e&l✦ ", "STAR"),
        CIRCLESTAR(" &d&l✪ ", "CIRCLE STAR"),
        SNOW(" &b&l❆ ", "SNOW"),
        CROSS(" &4&l✠ ", "CROSS"),
        HEART(" &c&l❤ ", "HEART"),
        DIAMOND(" &3&l❖ ", "DIAMOND"),
        CHECKMARK(" &a&l✔ ", "CHECKMARK"),
        F("&6&l#F ", "F"),
        GOD("&6&l#GOD ", "GOD"),
        DAB("&4&l<o/ ", "DAB"),
        EDATER("&d&l#EDATER ", "EDATER");

        private final String prefix;
        private final String displayName;

        Tag(String prefix, String displayName) {
            this.prefix = prefix;
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getPrefix() {
            return prefix;
        }
    }
}
