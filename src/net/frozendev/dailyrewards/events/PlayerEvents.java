package net.frozendev.dailyrewards.events;

import java.util.Date;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.frozendev.dailyrewards.DailyRewards;
import net.frozendev.dailyrewards.data.GlobalData;
import net.frozendev.dailyrewards.data.PlayerData;


public class PlayerEvents implements Listener {

	private final FileConfiguration STORAGE = DailyRewards.getStorage().getFileConfiguration();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void PlayerJoin(PlayerJoinEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		if (!GlobalData.PLAYERS.containsKey(uuid)) {
			if (STORAGE.get(uuid.toString()) != null)
				GlobalData.PLAYERS.put(uuid, new PlayerData(uuid, STORAGE.getInt(uuid.toString() + ".day"), STORAGE.getBoolean(uuid.toString() + ".cantakereward"), new Date(STORAGE.getString(uuid.toString() + ".lastdate")), new Date(STORAGE.getString(uuid.toString() + ".nextdate"))));
			else
				GlobalData.PLAYERS.put(e.getPlayer().getUniqueId(), new PlayerData(e.getPlayer().getUniqueId(), 1, true, new Date(), new Date()));
		}
		
		if (DailyRewards.getPlugin().getConfig().getBoolean("options.openlogin")) {
			DailyRewards.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(DailyRewards.getPlugin(), new Runnable() {
				@Override
				public void run() {
					GlobalData.PLAYERS.get(uuid).openInventory();
				}
			}, 20L);
		}
	}
	
	@EventHandler
	public void PlayerQuit(PlayerQuitEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		if (GlobalData.PLAYERS.containsKey(uuid)) {
			PlayerData pData = GlobalData.PLAYERS.get(uuid);
			pData.save();
			GlobalData.PLAYERS.remove(uuid);
		}
	}
	
}
