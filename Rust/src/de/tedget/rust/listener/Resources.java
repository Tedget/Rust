package de.tedget.rust.listener;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Resources implements Listener {
	private static Plugin plugin;
	public Resources(Plugin instance)
	{
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	//Trees
	public static HashMap<String, Integer> Trees = new HashMap<String, Integer>();
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		e.setCancelled(true);
		Player p = e.getPlayer();
		final String s = ("T"+e.getBlock().getLocation().getBlockX() + e.getBlock().getLocation().getBlockZ());
		if (e.getBlock().getType().equals(Material.LOG)){
			if (Trees.containsKey(s)){
				int i = Trees.get(s);
				if (i == 10){
					p.sendMessage("§cThere is no wood left here.");
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			    		
			    		public void run() {
			    			Trees.remove(s);
			    		}
			    	}, 15*20L);
				}
				if (i < 10){
					p.getInventory().addItem(new ItemStack(Material.LOG, 1));
					Trees.put(s,i+1);
				}
			}
			else{
				p.getInventory().addItem(new ItemStack(Material.LOG, 1));
				Trees.put(s,1);
			}
		}
	}
	
	//Stones
	public static HashMap<String, Integer> Stones = new HashMap<String, Integer>();
	
	@EventHandler
	public void onBreak2(BlockBreakEvent e){
		e.setCancelled(true);
		Player p = e.getPlayer();
		final String s = ("S"+e.getBlock().getLocation().getBlockX() + e.getBlock().getLocation().getBlockZ());
		if (e.getBlock().getType().equals(Material.MOSSY_COBBLESTONE)){
			if (Stones.containsKey(s)){
				int i = Stones.get(s);
				if (i == 10){
					p.sendMessage("§cThere is no wood left here.");
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			    		
			    		public void run() {
			    			Trees.remove(s);
			    		}
			    	}, 15*20L);
				}
				if (i < 10){
					p.getInventory().addItem(new ItemStack(Material.LOG, 1));
					Trees.put(s,i+1);
				}
			}
			else{
				p.getInventory().addItem(new ItemStack(Material.LOG, 1));
				Trees.put(s,1);
			}
		}
	}
}
