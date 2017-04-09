package net.frozendev.dailyrewards.commands;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.frozendev.dailyrewards.DailyRewards;
import net.frozendev.dailyrewards.data.GlobalData;
import net.frozendev.dailyrewards.data.PlayerData;

public class DailyDebugCommand implements CommandExecutor {

	private final Logger log = DailyRewards.getPlugin().getLogger();
	private final FileConfiguration storage = DailyRewards.getFileManager().getStorageFile().getFileConfiguration();

	public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
		for (Player p : DailyRewards.getPlugin().getServer().getOnlinePlayers()) {
			if (GlobalData.PLAYERS.containsKey(p.getUniqueId())) {
				log.info(p.getName() + " exists in GlobalData.PLAYERS");
			} else {
				log.info("ERROR : " + p.getName() + " is missing in GlobalData.PLAYERS");
			}
		}
		for (PlayerData pData : GlobalData.PLAYERS.values()) {
			log.info("PLAYERDATA DEBUG FOR " + pData.getPlayer().getName());
			log.info("pData name : " + pData.getPlayer().getName());
			log.info("pData uuid : " + pData.getUUID());
			log.info("pData day : " + pData.getRewardDay());
			log.info("pData last date : " + pData.getLastRewardTime());
			log.info("pData next date : " + pData.getNextRewardTime());
		}
		for (PlayerData pData : GlobalData.PLAYERS.values()) {
			log.info("STORAGE DEBUG FOR " + pData.getPlayer().getName());
			if (storage.get(pData.getUUID().toString()) != null) {
				log.info(pData.getPlayer().getName() + " exists in storage");
			} else {
				log.info("ERROR : " + pData.getPlayer().getName() + " is missing in storage");
			}
			log.info("Storage day : " + storage.getString(pData.getUUID().toString() + ".day"));
			log.info("Storage cantakereward : " + storage.getString(pData.getUUID().toString() + ".cantakereward"));
			log.info("Storage lastdate : " + storage.getString(pData.getUUID().toString() + ".lastdate"));
			log.info("Storage nextdate : " + storage.getString(pData.getUUID().toString() + ".nextdate"));
		}
		return true;
	}
}
