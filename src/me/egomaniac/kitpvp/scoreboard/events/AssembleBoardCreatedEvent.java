package me.egomaniac.kitpvp.scoreboard.events;

import me.egomaniac.kitpvp.scoreboard.api.AssembleBoard;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AssembleBoardCreatedEvent extends Event
{
    public static HandlerList handlerList;
    private AssembleBoard board;
    private boolean cancelled;
    
    public AssembleBoardCreatedEvent(final AssembleBoard board) {
        this.cancelled = false;
        this.board = board;
    }
    
    public HandlerList getHandlers() {
        return AssembleBoardCreatedEvent.handlerList;
    }
    
    public AssembleBoard getBoard() {
        return this.board;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setBoard(final AssembleBoard board) {
        this.board = board;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    public static HandlerList getHandlerList() {
        return AssembleBoardCreatedEvent.handlerList;
    }
    
    static {
        AssembleBoardCreatedEvent.handlerList = new HandlerList();
    }
}
