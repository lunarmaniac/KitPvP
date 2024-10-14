package me.egomaniac.kitpvp.scoreboard.api;

import me.egomaniac.kitpvp.scoreboard.events.AssembleBoardCreateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Assemble
{
    private JavaPlugin plugin;
    private AssembleAdapter adapter;
    private AssembleThread thread;
    private AssembleListener listeners;
    private AssembleStyle assembleStyle;
    private Map<UUID, AssembleBoard> boards;
    private long ticks;
    private boolean hook;
    private boolean debugMode;
    
    public Assemble(final JavaPlugin plugin, final AssembleAdapter adapter) {
        this.assembleStyle = AssembleStyle.MODERN;
        this.ticks = 2L;
        this.hook = false;
        this.debugMode = true;
        if (plugin == null) {
            throw new RuntimeException("Assemble can not be instantiated without a plugin instance!");
        }
        this.plugin = plugin;
        this.adapter = adapter;
        this.boards = new ConcurrentHashMap<UUID, AssembleBoard>();
        this.setup();
    }
    
    @Deprecated
    public void setup() {
        this.listeners = new AssembleListener(this);
        this.plugin.getServer().getPluginManager().registerEvents(this.listeners, this.plugin);
        if (this.thread != null) {
            this.thread.stop();
            this.thread = null;
        }
        for (final Player player : Bukkit.getOnlinePlayers()) {
            final AssembleBoardCreateEvent createEvent = new AssembleBoardCreateEvent(player);
            Bukkit.getPluginManager().callEvent(createEvent);
            if (createEvent.isCancelled()) {
                return;
            }
            this.getBoards().putIfAbsent(player.getUniqueId(), new AssembleBoard(player, this));
        }
        this.thread = new AssembleThread(this);
    }
    
    @Deprecated
    public void cleanup() {
        if (this.thread != null) {
            this.thread.stop();
            this.thread = null;
        }
        if (this.listeners != null) {
            HandlerList.unregisterAll(this.listeners);
            this.listeners = null;
        }
        for (final UUID uuid : this.getBoards().keySet()) {
            final Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                if (!player.isOnline()) {
                    continue;
                }
                this.getBoards().remove(uuid);
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
        }
    }
    
    public JavaPlugin getPlugin() {
        return this.plugin;
    }
    
    public AssembleAdapter getAdapter() {
        return this.adapter;
    }
    
    public AssembleThread getThread() {
        return this.thread;
    }
    
    public AssembleListener getListeners() {
        return this.listeners;
    }
    
    public AssembleStyle getAssembleStyle() {
        return this.assembleStyle;
    }
    
    public Map<UUID, AssembleBoard> getBoards() {
        return this.boards;
    }
    
    public long getTicks() {
        return this.ticks;
    }
    
    public boolean isHook() {
        return this.hook;
    }
    
    public boolean isDebugMode() {
        return this.debugMode;
    }
    
    public void setPlugin(final JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    public void setAdapter(final AssembleAdapter adapter) {
        this.adapter = adapter;
    }
    
    public void setThread(final AssembleThread thread) {
        this.thread = thread;
    }
    
    public void setListeners(final AssembleListener listeners) {
        this.listeners = listeners;
    }
    
    public void setAssembleStyle(final AssembleStyle assembleStyle) {
        this.assembleStyle = assembleStyle;
    }
    
    public void setBoards(final Map<UUID, AssembleBoard> boards) {
        this.boards = boards;
    }
    
    public void setTicks(final long ticks) {
        this.ticks = ticks;
    }
    
    public void setHook(final boolean hook) {
        this.hook = hook;
    }
    
    public void setDebugMode(final boolean debugMode) {
        this.debugMode = debugMode;
    }
}
