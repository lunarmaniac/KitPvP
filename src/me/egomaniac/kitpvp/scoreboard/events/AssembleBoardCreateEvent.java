package me.egomaniac.kitpvp.scoreboard.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AssembleBoardCreateEvent extends Event implements Cancellable
{
    public static HandlerList handlerList;
    private Player player;
    private boolean cancelled;
    
    public AssembleBoardCreateEvent(final Player player) {
        this.cancelled = false;
        this.player = player;
    }
    
    public HandlerList getHandlers() {
        return AssembleBoardCreateEvent.handlerList;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setPlayer(final Player player) {
        this.player = player;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    public static HandlerList getHandlerList() {
        return AssembleBoardCreateEvent.handlerList;
    }
    
    static {
        AssembleBoardCreateEvent.handlerList = new HandlerList();
    }
}
