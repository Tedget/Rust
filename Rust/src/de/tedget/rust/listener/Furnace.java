package de.tedget.rust.listener;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Furnace implements Listener {
	private static Plugin plugin;
	public Furnace(Plugin instance)
	{
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	final static HashMap<Location, Integer> task = new HashMap<Location, Integer>();
	final static HashMap<Location, Integer> burn = new HashMap<Location, Integer>();
	
	public static void cancelTimer(Location l) {
		Integer timerID = task.remove(l);

		if (timerID != null) {
			Bukkit.getScheduler().cancelTask(timerID);
		}
	}
	
	public static void cook(Location l){
		Dispenser d = (Dispenser) l.getBlock().getState();
		for (ItemStack i : d.getInventory().getContents()){
			if(i != null){
				if(i.getType().equals(Material.IRON_ORE)){
					if(i.getAmount() == 1){
						d.getInventory().remove(i);
						d.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 8));
					}
					else{
						i.setAmount(i.getAmount()-1);
						d.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 8));
					}
				}
				if(i.getType().equals(Material.LOG)){
					if(i.getAmount() == 1){
						d.getInventory().remove(i);
						d.getInventory().addItem(new ItemStack(Material.COAL, 5));
					}
					else{
						i.setAmount(i.getAmount()-1);
						d.getInventory().addItem(new ItemStack(Material.COAL, 5));
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlace(BlockPlaceEvent e){
		Player p = e.getPlayer();
		if (e.getBlock().getType().equals(Material.FURNACE)){
			Location l = e.getBlock().getLocation().add(0, 1, 0);
			Location l2 = e.getBlock().getLocation().add(0, 2, 0);
			if (!(l.getBlock().getType().equals(Material.AIR)) || !(l2.getBlock().getType().equals(Material.AIR))){
				e.setCancelled(true);
				p.sendMessage("§cThere's not enough space for this.");
			}
			else{
				p.sendMessage("Data: " + e.getBlock().getData());
				l.getBlock().setType(Material.DISPENSER);
				if (e.getBlock().getData() == 0x2){
					l.getBlock().setData((byte) 0x2);
				}
				if (e.getBlock().getData() == 3){
					l.getBlock().setData((byte) 3);
				}
				if (e.getBlock().getData() == 0x4){
					l.getBlock().setData((byte) 0x4);
				}
				if (e.getBlock().getData() == 0x5){
					l.getBlock().setData((byte) 0x5);
				}
				l2.getBlock().setType(Material.LEVER);
				l2.getBlock().setData((byte) 5);
			}
		}
	}
	
	@EventHandler
	public void onOpen(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if (e.getClickedBlock().getType().equals(Material.FURNACE) || e.getClickedBlock().getType().equals(Material.BURNING_FURNACE)){
				e.setCancelled(true);
				if (e.getClickedBlock().getLocation().add(0, 1, 0).getBlock().getType().equals(Material.DISPENSER)){
					Dispenser d = (Dispenser) e.getClickedBlock().getLocation().add(0, 1, 0).getBlock().getState();
					p.openInventory(d.getInventory());
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onLever(final BlockRedstoneEvent e){
		Location l = e.getBlock().getLocation().subtract(0, 1, 0);
		if (l.getBlock().getType().equals(Material.DISPENSER)){
			final Dispenser d = (Dispenser) l.getBlock().getState();
			task.put(d.getLocation(), Bukkit.getScheduler().runTaskTimer(plugin, new Runnable(){
				@Override			
				public void run()
				{
					if(d.getBlock().isBlockPowered() == true){
						if (d.getInventory().contains(Material.LOG)){
							d.getLocation().subtract(0, 1, 0).getBlock().setType(Material.BURNING_FURNACE);
							d.getLocation().subtract(0, 1, 0).getBlock().setData(d.getBlock().getData());
							if(burn.containsKey(d.getLocation())){
								int i = burn.get(d.getLocation());
								if(i == 30){
									cook(d.getLocation());
									burn.put(d.getLocation(), 0);
								}
								if(i < 30){
									burn.put(d.getLocation(), i+1);
								}
							}
							else{
								burn.put(d.getLocation(), 1);
							}
						}
						else{
							Location lever = d.getLocation().add(0, 1, 0);
							lever.getBlock().setData((byte) 5);
							burn.remove(d.getLocation());
							d.getLocation().subtract(0, 1, 0).getBlock().setType(Material.FURNACE);
							cancelTimer(d.getBlock().getLocation());
						}
					}
					else{
						burn.remove(d.getLocation());
						d.getLocation().subtract(0, 1, 0).getBlock().setType(Material.FURNACE);
						cancelTimer(d.getBlock().getLocation());
					}
				}		
			}, 0L, 20L).getTaskId());
		}
	}
	
	@EventHandler
	public void onShoot(final BlockDispenseEvent e){
		e.setCancelled(true);
	}
}
