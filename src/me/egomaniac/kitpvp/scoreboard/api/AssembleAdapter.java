package me.egomaniac.kitpvp.scoreboard.api;

import org.bukkit.entity.Player;

import java.util.List;

public interface AssembleAdapter
{
    String getTitle(final Player p0);
    
    List<String> getLines(final Player p0);
}
