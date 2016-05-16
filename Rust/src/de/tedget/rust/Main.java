package de.tedget.rust;

import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import de.tedget.rust.listener.Board;
import de.tedget.rust.listener.Food;
import de.tedget.rust.listener.Furnace;
import de.tedget.rust.listener.Items;
import de.tedget.rust.listener.Radiation;
import de.tedget.rust.listener.Resources;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable() {
		
		//Listener
		rad = new Radiation(this);
		fo = new Food(this);
		b = new Board(this);
		i = new Items(this);
		r = new Resources(this);
		f = new Furnace(this);
		
		//Teams
		Board.createScoreboard();
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]){
		if (cmd.getName().equalsIgnoreCase("test")){
			Board.createScoreboard();
		}
		return false;
	}
	
	public final Logger logger = Logger.getLogger("Minecraft");
	@SuppressWarnings("unused")
	private static Radiation rad;
	@SuppressWarnings("unused")
	private static Food fo;
	@SuppressWarnings("unused")
	private static Board b;
	@SuppressWarnings("unused")
	private static Items i;
	@SuppressWarnings("unused")
	private static Resources r;
	@SuppressWarnings("unused")
	private static Furnace f;
}
