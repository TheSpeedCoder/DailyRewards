package net.frozendev.dailyrewards;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.frozendev.dailyrewards.commands.DailyDebugCommand;
import net.frozendev.dailyrewards.commands.DailyRewardsCommand;
import net.frozendev.dailyrewards.depends.Vault;
import net.frozendev.dailyrewards.events.InventoryEvents;
import net.frozendev.dailyrewards.events.PlayerEvents;
import net.frozendev.dailyrewards.files.FileManager;
import net.frozendev.dailyrewards.utils.GlobalUtils;

public class DailyRewards extends JavaPlugin {

	private static Plugin plugin;
	private static Vault vault;
	private static FileManager fileManager;
	
	@Override
	public void onEnable() {
		plugin = this;
		vault = new Vault(this);
		fileManager = new FileManager(this);
		GlobalUtils.loadPlayers();
		getCommand("dailyrewards").setExecutor(new DailyRewardsCommand());
		getCommand("dailydebug").setExecutor(new DailyDebugCommand());
		getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
		getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
	}
	
	@Override
	public void onDisable() {
		GlobalUtils.savePlayers();
		plugin = null;
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	public static Vault getVault() {
		return vault;
	}
	
	public static FileManager getFileManager() {
		return fileManager;
	}
	
}
