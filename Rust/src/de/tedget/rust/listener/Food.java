package de.tedget.rust.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Food implements Listener {
	private static Plugin plugin;
	public Food(Plugin instance)
	{
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
}
