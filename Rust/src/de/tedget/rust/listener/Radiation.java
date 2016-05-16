package de.tedget.rust.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Radiation implements Listener {
	
	private static Plugin plugin;
	public Radiation(Plugin instance)
	{
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
}
