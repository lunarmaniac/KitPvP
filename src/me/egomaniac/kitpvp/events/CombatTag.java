package me.egomaniac.kitpvp.events;


import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Created by Infames
 * Date: 23/07/2021 @ 20:07
 */

@Getter
public class CombatTag extends PlayerEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player attacker;

    public CombatTag(Player player, Player attacker) {
        super(player);
        this.attacker = attacker;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }
}