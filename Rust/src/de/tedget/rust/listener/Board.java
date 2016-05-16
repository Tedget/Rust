package de.tedget.rust.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class Board implements Listener {
	private static Plugin plugin;
	public Board(Plugin instance)
	{
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	//Teams
			static Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
			static Objective objective = board.registerNewObjective("test", "dummy");
			static Score Health = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Health    "));
			static Score Food = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "Food    "));
			static Score Rads = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.WHITE + "Rads    "));
	
	public static void createScoreboard(){
		for(Player p : Bukkit.getOnlinePlayers()){
			
    		//Board
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    		objective.setDisplayName("§6Info");
    		Health.setScore((int) p.getHealthScale());
    		Food.setScore(p.getFoodLevel());
    		Rads.setScore(Rads.getScore());
    		p.setScoreboard(board);
    	}
    }
	
	@EventHandler
	public void onFoodChange(final FoodLevelChangeEvent e){
		if (e.getEntity() instanceof Player){
			Food.setScore(e.getFoodLevel());
		}
	}
	
	@EventHandler
	public void onHealthChange(final EntityDamageEvent e){
		if (e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			Health.setScore((int) p.getHealth());
		}
	}
	
	@EventHandler
	public void onRegain(final EntityRegainHealthEvent e){
		if (e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			Health.setScore((int) p.getHealth());
		}
	}
	
	@EventHandler
	public void onRespawn(final PlayerRespawnEvent e){
		Player p = e.getPlayer();
		Health.setScore((int) p.getHealth());
		Food.setScore(p.getFoodLevel());
	}
}
