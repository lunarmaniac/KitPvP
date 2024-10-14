package me.egomaniac.kitpvp.managers;

import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.cuboid.CustomLocation;
import me.egomaniac.kitpvp.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class SumoManager {
    private boolean running;
    private boolean joiningAllowed;
    private final List<Player> participants;
    private final List<Player> fightPairs;
    private CustomLocation pos1;
    private CustomLocation pos2;
    private CustomLocation waitingLocation;
    private CustomLocation safezoneMin;
    private CustomLocation safezoneMax;

    public SumoManager() {
        this.running = false;
        this.joiningAllowed = false;
        this.participants = new ArrayList<>();
        this.fightPairs = new ArrayList<>();
        this.savedInventories = new HashMap<>();
    }

    public void setPos1(CustomLocation pos1) {
        this.pos1 = pos1;
    }

    public CustomLocation getPos1() {
        return pos1;
    }

    public void setPos2(CustomLocation pos2) {
        this.pos2 = pos2;
    }

    public CustomLocation getPos2() {
        return pos2;
    }

    public void setWaitingLocation(CustomLocation waitingLocation) {
        this.waitingLocation = waitingLocation;
    }

    public CustomLocation getWaitingLocation() {
        return waitingLocation;
    }
    public void setSafezoneMin(CustomLocation safezoneMin) {
        this.safezoneMin = safezoneMin;
    }

    public CustomLocation getSafezoneMin() {
        return safezoneMin;
    }

    public void setSafezoneMax(CustomLocation safezoneMax) {
        this.safezoneMax = safezoneMax;
    }

    public CustomLocation getSafezoneMax() {
        return safezoneMax;
    }
    public boolean isRunning() {
        return running;
    }

    private final Map<Player, ItemStack[]> savedInventories;
    private final Map<Player, ItemStack[]> savedArmors = new HashMap<>();

    public void saveAndRemoveInventory(Player player) {
        PlayerInventory inventory = player.getInventory();
        ItemStack[] inventoryContents = inventory.getContents();
        ItemStack[] armorContents = player.getInventory().getArmorContents();

        savedInventories.put(player, inventoryContents);
        savedArmors.put(player, armorContents);

        inventory.clear();
        inventory.setArmorContents(null);
    }

    public void restoreInventory(Player player) {
        if (savedInventories.containsKey(player) && savedArmors.containsKey(player)) {
            PlayerInventory inventory = player.getInventory();

            ItemStack[] savedContents = savedInventories.get(player);
            ItemStack[] savedArmorContents = savedArmors.get(player);

            inventory.setContents(savedContents); // Restore the saved inventory contents
            inventory.setArmorContents(savedArmorContents); // Restore the saved armor contents

            savedInventories.remove(player);
            savedArmors.remove(player);
        }
    }

    public void startEvent() {
        this.running = true;
        this.joiningAllowed = true;
        this.participants.clear();
        this.fightPairs.clear();
    }

    public void closeEvent() {
        this.running = false;
        this.joiningAllowed = false;

        for (Player player : participants) {
            leaveSumo(player);
        }

        this.participants.clear();
        this.fightPairs.clear();
    }

    public void stopJoining() {
        this.joiningAllowed = false;
    }

    public boolean canStart() {
        return participants.size() >= 2;
    }

    public void joinSumo(Player player) {
        if (Main.getInstance().sumoManager.isParticipant(player)) {
            player.sendMessage(CC.translate("&cYou are already a participant."));
            return;
        }

        if (Main.getInstance().sumoManager.isRunning()) {
            player.sendMessage(CC.translate("&aYou have joined the Sumo event."));
        } else {
            player.sendMessage(CC.RED + "There is currently no sumo event running.");
            return;
        }

        player.setGameMode(GameMode.SURVIVAL);

        player.setHealth(20);
        player.setFoodLevel(20);

        Main.getInstance().sumoManager.teleportParticipantToLobby(player);
        Main.getInstance().sumoManager.addParticipant(player);
        Main.getInstance().profileManager.getProfile(player.getUniqueId()).setSumoEvent(true);
        saveAndRemoveInventory(player);

        for (Player participant : participants) {
            participant.sendMessage(CC.translate("&a" + player.getDisplayName() + " has joined the sumo event "));
        }

        //Bukkit.broadcastMessage(participants.toString());
    }

    public void leaveSumo(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        Main.getInstance().teleportManager.teleportSpawn(player);
        Main.getInstance().sumoManager.removeParticipant(player);
        fightPairs.remove(player);
        //Main.getInstance().sumoManager.removeFightpair(player);

        Main.getInstance().profileManager.getProfile(player.getUniqueId()).setSumoEvent(false);
        restoreInventory(player);
    }

    public void teleportParticipantToLobby(Player player) {
        String lobbyLocation = Main.getInstance().getConfig().getString("SERVER.SUMO.WAITING-LOCATION");

        String[] locationData = lobbyLocation.split(", ");

        double x = Double.parseDouble(locationData[0]);
        double y = Double.parseDouble(locationData[1]);
        double z = Double.parseDouble(locationData[2]);
        float yaw = Float.parseFloat(locationData[3]);
        float pitch = Float.parseFloat(locationData[4]);
        Location lobby = new Location(Bukkit.getWorld("world"), x, y, z, yaw, pitch);
        player.teleport(lobby);

    }

    public void handleDeath(Player player) {
        Player loser = null;
        for (Player p : fightPairs) {
            if (p.equals(player)) {
                loser = p;
                break;
            }
        }

        if (loser != null) {
            //fightPairs.remove(loser);
            leaveSumo(loser);
//            Main.getInstance().teleportManager.teleportSpawn(loser);
//            Main.getInstance().sumoManager.removeParticipant(loser);
//            Main.getInstance().profileManager.getProfile(loser.getUniqueId()).setSumoEvent(false);

            loser.sendMessage("");
            loser.sendMessage(CC.translate("&eYou didn't win this time, but here's &b100 &eCredits for participating!"));
            loser.sendMessage("");

            ProfileManager.Profile profile = Main.getInstance().profileManager.getProfile(loser.getUniqueId());
            int credits = profile.getCredits() + 100;
            profile.setCredits(credits);
        }

        Player winner = null;
        if (fightPairs.size() > 0) {
            winner = fightPairs.get(0);
        }

        if (winner != null) {
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(CC.translate(Main.getInstance().profileManager.getProfile(winner.getUniqueId()).getRank().getColor()
                    + winner.getDisplayName() + " &ehas won the &bSumo &eEvent and received &b1000 &eCredits!"));
            Bukkit.broadcastMessage("");

            awardCredits(winner);
            leaveSumo(winner);
        }

        closeEvent();

    }

    public void hostEvent(Player player) {

        if (Main.getInstance().sumoManager.isRunning()) {
            player.sendMessage(CC.translate("&cThe Sumo event is already in progress."));
            return;
        }

        Main.getInstance().sumoManager.startEvent();
        ProfileManager profile = Main.getInstance().profileManager;
        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(CC.translate(profile.getProfile(player.getUniqueId()).getRank().getPrefix() + player.getDisplayName() + " &eis hosting a &bSumo &eevent! \n&eParticipate for a winning prize of &b1000 &ecredits."));
        String joinMessage = CC.translate("&eClick &chere &eto join.");

        Bukkit.spigot().broadcast(new ComponentBuilder(joinMessage)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/event join"))
                .create());
        Bukkit.broadcastMessage("");

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (Main.getInstance().sumoManager.canStart()) {
                Main.getInstance().sumoManager.stopJoining();
                Bukkit.broadcastMessage(CC.translate("&eThe Sumo event will start in 5 seconds..."));
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                    Bukkit.broadcastMessage(CC.translate("&eThe Sumo event will start in 4 seconds..."));
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                        Bukkit.broadcastMessage(CC.translate("&eThe Sumo event will start in 3 seconds..."));
                        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                            Bukkit.broadcastMessage(CC.translate("&eThe Sumo event will start in 2 seconds..."));
                            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                                Bukkit.broadcastMessage(CC.translate("&eThe Sumo event will start in 1 second..."));
                                Main.getInstance().sumoManager.sumoStart();
                            }, 20L);
                        }, 20L);
                    }, 20L);
                }, 20L);
            } else {

                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(CC.translate("&cThe sumo event has been canceled due to lack of players."));
                Bukkit.broadcastMessage("");

                Main.getInstance().sumoManager.closeEvent();

            }
        }, 20 * 20L); // Allow players to join for 60 seconds (1200 ticks)
    }

    public void sumoStart() {
        if (participants.size() < 2) {
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(CC.translate("&cThe sumo event has been canceled due to lack of players."));
            Bukkit.broadcastMessage("");

            closeEvent();
            return; // Not enough participants for a match
        }

        Random random = new Random();
        Player player1 = participants.get(random.nextInt(participants.size()));

        Player player2 = null;
        do {
            player2 = participants.get(random.nextInt(participants.size()));
        } while (player2.equals(player1));

        fightPairs.add(player1);
        fightPairs.add(player2);

        Bukkit.broadcastMessage(CC.GREEN + "" + player1.getDisplayName() + " vs " + player2.getDisplayName());
        Main.getInstance().teleportManager.teleportSumoPositions(player1, player2);
    }

    private void awardCredits(Player player) {
        player.sendMessage("");
        player.sendMessage(CC.translate("&eYou have recieved &b500 &eCredits for winning the event!"));
        player.sendMessage("");
        UUID playerId = player.getUniqueId();
        ProfileManager.Profile profile = Main.getInstance().profileManager.getProfile(playerId);
        int credits = profile.getCredits() + 1000;
        profile.setCredits(credits);
        Main.getInstance().profileManager.saveProfile(profile);
    }

    public void addParticipant(Player player) {
        if (joiningAllowed) {
            participants.add(player);
        } else {
            player.sendMessage(CC.translate("&cYou can no longer join the Sumo event."));
        }
    }

    public void removeParticipant(Player player) {
        participants.remove(player);
    }

    public boolean isParticipant(Player player) {
        return participants.contains(player);
    }

    public boolean isFightPair(Player player) {
        for (Player player1 : fightPairs) {
            if (player1.equals(player)) {
                return true;
            }
        }
        return false;
    }
}
