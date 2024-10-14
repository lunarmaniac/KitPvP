package me.egomaniac.kitpvp.scoreboard.api;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Collections;
import java.util.List;

public class AssembleThread extends Thread
{
    private final Assemble assemble;
    
    AssembleThread(final Assemble assemble) {
        this.assemble = assemble;
        this.start();
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                while (true) {
                    this.tick();
                    Thread.sleep(this.assemble.getTicks() * 50L);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void tick() {
        for (final Player player : this.assemble.getPlugin().getServer().getOnlinePlayers()) {
            try {
                final AssembleBoard board = this.assemble.getBoards().get(player.getUniqueId());
                if (board == null) {
                    continue;
                }
                final Scoreboard scoreboard = board.getScoreboard();
                final Objective objective = board.getObjective();
                if (scoreboard == null || objective == null) {
                    continue;
                }
                final String title = ChatColor.translateAlternateColorCodes('&', this.assemble.getAdapter().getTitle(player));
                if (!objective.getDisplayName().equals(title)) {
                    objective.setDisplayName(title);
                }
                List<String> newLines = this.assemble.getAdapter().getLines(player);
                if (newLines == null || newLines.isEmpty()) {
                    board.getEntries().forEach(AssembleBoardEntry::remove);
                    board.getEntries().clear();
                }
                else {
                    if (newLines.size() > 15) {
                        newLines = this.assemble.getAdapter().getLines(player).subList(0, 15);
                    }
                    if (!this.assemble.getAssembleStyle().isDescending()) {
                        Collections.reverse(newLines);
                    }
                    if (board.getEntries().size() > newLines.size()) {
                        for (int i = newLines.size(); i < board.getEntries().size(); ++i) {
                            final AssembleBoardEntry entry = board.getEntryAtPosition(i);
                            if (entry != null) {
                                entry.remove();
                            }
                        }
                    }
                    int cache = this.assemble.getAssembleStyle().getStartNumber();
                    for (int j = 0; j < newLines.size(); ++j) {
                        AssembleBoardEntry entry2 = board.getEntryAtPosition(j);
                        final String line = ChatColor.translateAlternateColorCodes('&', newLines.get(j));
                        if (entry2 == null) {
                            entry2 = new AssembleBoardEntry(board, line, j);
                        }
                        entry2.setText(line);
                        entry2.setup();
                        entry2.send(this.assemble.getAssembleStyle().isDescending() ? cache-- : cache++);
                    }
                }
                if (player.getScoreboard() == scoreboard || this.assemble.isHook()) {
                    continue;
                }
                player.setScoreboard(scoreboard);
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new AssembleException("There was an error updating " + player.getName() + "'s scoreboard.");
            }
        }
    }
}
