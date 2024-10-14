package me.egomaniac.kitpvp.scoreboard.api;

import me.egomaniac.kitpvp.scoreboard.events.AssembleBoardCreatedEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AssembleBoard
{
    private final List<AssembleBoardEntry> entries;
    private final List<String> identifiers;
    private final UUID uuid;
    private final Assemble assemble;
    
    public AssembleBoard(final Player player, final Assemble assemble) {
        this.entries = new ArrayList<AssembleBoardEntry>();
        this.identifiers = new ArrayList<String>();
        this.uuid = player.getUniqueId();
        this.assemble = assemble;
        this.setup(player);
    }
    
    private static String getRandomChatColor(final int position) {
        return ChatColor.values()[position].toString();
    }
    
    public Scoreboard getScoreboard() {
        final Player player = Bukkit.getPlayer(this.getUuid());
        if (this.getAssemble().isHook() || player.getScoreboard() != Bukkit.getScoreboardManager().getMainScoreboard()) {
            return player.getScoreboard();
        }
        return Bukkit.getScoreboardManager().getNewScoreboard();
    }
    
    public Objective getObjective() {
        final Scoreboard scoreboard = this.getScoreboard();
        if (scoreboard.getObjective("Assemble") == null) {
            final Objective objective = scoreboard.registerNewObjective("Assemble", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(this.getAssemble().getAdapter().getTitle(Bukkit.getPlayer(this.getUuid())));
            return objective;
        }
        return scoreboard.getObjective("Assemble");
    }
    
    private void setup(final Player player) {
        final Scoreboard scoreboard = this.getScoreboard();
        player.setScoreboard(scoreboard);
        this.getObjective();
        final AssembleBoardCreatedEvent createdEvent = new AssembleBoardCreatedEvent(this);
        Bukkit.getPluginManager().callEvent(createdEvent);
    }
    
    public AssembleBoardEntry getEntryAtPosition(final int pos) {
        return (pos >= this.entries.size()) ? null : this.entries.get(pos);
    }
    
    public String getUniqueIdentifier(final int position) {
        String identifier;
        for (identifier = getRandomChatColor(position) + ChatColor.WHITE; this.identifiers.contains(identifier); identifier = identifier + getRandomChatColor(position) + ChatColor.WHITE) {}
        if (identifier.length() > 16) {
            return this.getUniqueIdentifier(position);
        }
        this.identifiers.add(identifier);
        return identifier;
    }
    
    public List<AssembleBoardEntry> getEntries() {
        return this.entries;
    }
    
    public List<String> getIdentifiers() {
        return this.identifiers;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public Assemble getAssemble() {
        return this.assemble;
    }
}
