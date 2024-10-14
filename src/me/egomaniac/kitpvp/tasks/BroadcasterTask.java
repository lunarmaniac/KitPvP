package me.egomaniac.kitpvp.tasks;

import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.Main;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class BroadcasterTask extends BukkitRunnable {

    private final List<String> broadcastMessages;
    private int index;

    public BroadcasterTask() {
        this.broadcastMessages = Arrays.asList(
                " &8* &bSuspect a player of cheating? &8&m-&f use /report to alert staff",
                " &8* &bInterested in staff position? &8&m-&f apply at www.senaticpvp.net!",
                " &8* &bWant a cool tag in the chat? &8&m-&f buy chat tags at spawn shop!",
                " &8* &bFound a bug? Help us improve &8&m-&f /bugreport to submit a bug",
                " &8* &bWant to suggest improvements? &8&m-&f /discord to share ideas!",
                " &8* &bVote for us and earn rewards! &8&m-&f /vote for more info",
                " &8* &bEquip perks to enhance your gameplay &8&m-&f /perks",
                " &8* &bJoin our Discord for updates and info! &8&m-&f /discord"
        );
        this.index = 0;
    }

    @Override
    public void run() {
        if (broadcastMessages.isEmpty()) {
            return;
        }

        String message = broadcastMessages.get(index);

        Main.getInstance().getServer().broadcastMessage(" ");
        Main.getInstance().getServer().broadcastMessage(CC.translate(message));
        Main.getInstance().getServer().broadcastMessage(" ");

        index++;

        if (index >= broadcastMessages.size()) {
            index = 0;
        }
    }
}