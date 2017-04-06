package net.frozendev.dailyrewards;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.frozendev.dailyrewards.commands.DailyRewardsCommand;
import net.frozendev.dailyrewards.data.GlobalData;
import net.frozendev.dailyrewards.data.PlayerData;
import net.frozendev.dailyrewards.events.InventoryEvents;
import net.frozendev.dailyrewards.events.PlayerEvents;

public class DailyRewards extends JavaPlugin {

	private static Plugin plugin;
	private static VaultAPI vault;
	private static Storage storage;
	private static TempStorage temp;

	@Override
	public void onEnable() {
		plugin = this;
		vault = new VaultAPI(this);
		storage = new Storage();
		temp = new TempStorage();
		saveDefaultConfig();
		List<String> players = (List<String>) temp.getFileConfiguration().getStringList("players");
		if (players != null) {
			if (players.size() > 0) {
				for (String str : players) {
					UUID uuid = UUID.fromString(str);
					@SuppressWarnings("deprecation")
					PlayerData pData = new PlayerData(uuid, storage.getFileConfiguration().getInt(uuid.toString() + ".day"), storage.getFileConfiguration().getBoolean(uuid.toString() + ".cantakereward"), new Date(storage.getFileConfiguration().getString(uuid.toString() + ".lastdate")), new Date(storage.getFileConfiguration().getString(uuid.toString() + ".nextdate")));
					if (!GlobalData.PLAYERS.containsKey(uuid))
						GlobalData.PLAYERS.put(uuid, pData);
				}
			}
		}
		temp.reset();
		getCommand("dailyrewards").setExecutor(new DailyRewardsCommand());
		getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
		getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
	}

	@Override
	public void onDisable() {
		List<String> strList = new ArrayList<String>();
		for (UUID id : GlobalData.PLAYERS.keySet()) {
			strList.add(id.toString());
		}
		temp.getFileConfiguration().set("players", strList);
		temp.save();
		for (PlayerData pData : GlobalData.PLAYERS.values()) {
			pData.save();
		}
		plugin = null;
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static Storage getStorage() {
		return storage;
	}

	public static TempStorage getTempStorage() {
		return temp;
	}

	public static VaultAPI getVault() {
		return vault;
	}
}
