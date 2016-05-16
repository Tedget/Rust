package de.tedget.rust.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.plugin.Plugin;

public class Items implements Listener {
	private static Plugin plugin;
	public Items(Plugin instance)
	{
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	//Slot
	@EventHandler
	public void onSlotChange(PlayerItemHeldEvent e){
		Player p = e.getPlayer();
		if (e.getNewSlot() == 7){
			p.getInventory().setHeldItemSlot(2);
		}
		if (e.getNewSlot() == 8){
			p.getInventory().setHeldItemSlot(2);
		}
		if (e.getNewSlot() == 0){
			p.getInventory().setHeldItemSlot(6);
		}
		if (e.getNewSlot() == 1){
			p.getInventory().setHeldItemSlot(6);
		}
	}
}
