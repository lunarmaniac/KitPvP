package me.egomaniac.kitpvp.tablist.impl;

import lombok.Getter;
import me.egomaniac.kitpvp.tablist.impl.adapter.TabAdapter;
import me.egomaniac.kitpvp.tablist.impl.listener.TabListener;
import me.egomaniac.kitpvp.tablist.impl.packet.TabPacket;
import me.egomaniac.kitpvp.tablist.impl.runnable.TabRunnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Tablist {

	@Getter
	private static Tablist instance;
	
	private final TabAdapter adapter;
	
	public Tablist(TabAdapter adapter, JavaPlugin plugin, long time) {
		instance = this;
		this.adapter = adapter;
		
		new TabPacket(plugin);
				
		Bukkit.getServer().getPluginManager().registerEvents(new TabListener(this), plugin);
		Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new TabRunnable(adapter), 60L, time); //TODO: async to run 1 millis
	}
}