package net.frozendev.dailyrewards.events;

import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.frozendev.dailyrewards.DailyRewards;
import net.frozendev.dailyrewards.data.GlobalData;
import net.frozendev.dailyrewards.data.PlayerData;
import net.frozendev.dailyrewards.files.ConfigurationReader;

public class PlayerEvents implements Listener {

	private static final FileConfiguration STORAGE = DailyRewards.getFileManager().getStorageFile()
			.getFileConfiguration();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		if (!GlobalData.PLAYERS.containsKey(uuid)) {
			if (STORAGE.getConfigurationSection(uuid.toString()) != null) {
				GlobalData.PLAYERS.put(uuid,
						new PlayerData(uuid, STORAGE.getBoolean(uuid + ".reward-allowed"),
								STORAGE.getInt(uuid + ".reward-day"),
								STORAGE.getLong(uuid + ".next-reward-time"),
								STORAGE.getLong(uuid + ".last-reward-time")));

			} else {
				GlobalData.PLAYERS.put(uuid, new PlayerData(uuid));
			}
		}
		GlobalData.PLAYERS.get(uuid).updateRewardAllowed();
		if (ConfigurationReader.openOnLogin()) {
			if (GlobalData.PLAYERS.get(uuid).rewardAllowed()) {
				DailyRewards.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(DailyRewards.getPlugin(),
						new Runnable() {
							@Override
							public void run() {
								GlobalData.PLAYERS.get(uuid).openGUI();
							}
						}, 20L);

			}
		}
	}
	
	@EventHandler
	public void PlayerQuit(PlayerQuitEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		if (GlobalData.PLAYERS.containsKey(uuid)) {
			PlayerData pData = GlobalData.PLAYERS.get(uuid);
			pData.saveData();
			GlobalData.PLAYERS.remove(uuid);
		}
	}

}
