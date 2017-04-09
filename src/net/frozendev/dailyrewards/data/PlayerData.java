package net.frozendev.dailyrewards.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.frozendev.dailyrewards.DailyRewards;
import net.frozendev.dailyrewards.files.ConfigurationReader;
import net.frozendev.dailyrewards.files.StorageFile;
import net.frozendev.dailyrewards.utils.GlobalUtils;
import net.frozendev.dailyrewards.utils.StringUtils;
import net.md_5.bungee.api.ChatColor;

public class PlayerData {

	/**
	 * Player UUID
	 */
	private UUID uuid;

	/**
	 * Player object
	 */
	private Player player;

	/**
	 * Boolean for easily manage if player can get his reward
	 */
	private boolean rewardAllowed;

	/**
	 * Current player rewardDay
	 */
	private int rewardDay;

	/**
	 * Next reward available time
	 */
	private long nextRewardTime;

	/**
	 * Last reward time
	 */
	private long lastRewardTime;

	/**
	 * GUI inventory
	 */
	private Inventory gui;

	/**
	 * Constructor for existing player
	 * 
	 * @param uuid
	 * @param rewardAllowed
	 * @param rewardDay
	 * @param nextRewardTime
	 * @param lastRewardTime
	 */
	public PlayerData(UUID uuid, boolean rewardAllowed, int rewardDay, long nextRewardTime, long lastRewardTime) {
		this.uuid = uuid;
		this.player = Bukkit.getPlayer(uuid);
		this.rewardAllowed = rewardAllowed;
		this.rewardDay = rewardDay;
		this.nextRewardTime = nextRewardTime;
		this.lastRewardTime = lastRewardTime;
		this.gui = Bukkit.createInventory(player, 9, StringUtils.messageConfigColorParsed("gui-title"));
		setupGUI();
	}

	/**
	 * Constructor for new player
	 * 
	 * @param uuid
	 */
	public PlayerData(UUID uuid) {
		this.uuid = uuid;
		this.player = Bukkit.getPlayer(uuid);
		this.rewardAllowed = true;
		this.rewardDay = 1;
		this.nextRewardTime = new Date().getTime();
		this.lastRewardTime = new Date().getTime();
		this.gui = Bukkit.createInventory(player, 9, "Daily Rewards");
		setupGUI();
	}

	/**
	 * Setup the GUI with correct items
	 */
	public void setupGUI() {
		GlobalUtils.setGUIContents(gui, rewardDay);
	}

	/**
	 * Open the gui
	 */
	public void openGUI() {
		player.openInventory(gui);
	}

	/**
	 * Send all rewards to player
	 */
	public void giveReward() {
		DayData dayData = GlobalData.DAY_REWARDS.get(rewardDay);
		double money = dayData.getMoney();
		List<ItemStack> items = dayData.getItems();
		HashMap<String, String> commands = dayData.getCommands();

		if (money > 0)
			DailyRewards.getVault().getEconomy().depositPlayer(player, money);

		if (items.size() > 0) {
			for (ItemStack item : items) {
				player.getInventory().addItem(item);
			}
		}

		if (commands.size() > 0) {
			for (String cmd : commands.keySet()) {
				DailyRewards.getPlugin().getServer()
						.dispatchCommand(DailyRewards.getPlugin().getServer().getConsoleSender(), cmd);
			}
		}
		
		rewardAllowed = false;
		
		lastRewardTime = new Date().getTime();
		nextRewardTime = lastRewardTime + (1000 * 60 * 60 * 24);
		
		sendRewardMessage();
		
		if (ConfigurationReader.resetAt7Day()) {
			if (rewardDay == 7)
				rewardDay = 1;
			else
				rewardDay++;
		} else {
			if (rewardDay < 7)
				rewardDay++;
		}
	}

	/**
	 * Send the reward message to the player
	 */
	private void sendRewardMessage() {
		
		if (!ConfigurationReader.customMessage()) {
			DayData dayData = GlobalData.DAY_REWARDS.get(rewardDay);
			double money = dayData.getMoney();
			List<ItemStack> items = dayData.getItems();
			HashMap<String, String> commands = dayData.getCommands();
			
			player.sendMessage("-------------------");
			player.sendMessage("     " + ChatColor.DARK_GREEN + "DAILY REWARDS");
			player.sendMessage("          " + ChatColor.DARK_GREEN + "Day : " + ChatColor.GREEN + dayData.getDay());
			player.sendMessage("-------------------");
			player.sendMessage(" ");
			player.sendMessage(ChatColor.GREEN + "You received :");

			if (money > 0)
				player.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + money + ChatColor.GREEN + "$");

			if (items.size() > 0) {
				for (ItemStack item : items) {
					player.sendMessage(
							StringUtils.parseItemForMessage(item.getType().name(), item.getAmount()));
				}
			}

			if (commands.size() > 0) {
				for (String cmd : commands.values()) {
					player.sendMessage(StringUtils.parseCommandForMessage(cmd));
				}
			}
		} else {
			List<String> messages = DailyRewards.getFileManager().getMessageFile().getFileConfiguration()
					.getStringList("messages.day" + rewardDay);
			for (String msg : messages) {
				player.sendMessage(StringUtils.parseColor(msg));
			}
		}
	}

	/**
	 * Check if the current date is superior or equal to the next date if yes
	 * the player can take is reward so we put rewardAllowed to true
	 */
	public void updateRewardAllowed() {
		if (nextRewardTime != 0) {
			if (new Date().getTime() >= nextRewardTime) {
				rewardAllowed = true;
			}
		}
	}

	/**
	 * Check if the current date is superior or equal to the next date + 1 day
	 * if yes the rewardDay is set to 1
	 */
	public void updateDay() {
		if (nextRewardTime != 0) {
			if (new Date().getTime() >= (nextRewardTime + (1000 * 60 * 60 * 24))) {
				rewardDay = 1;
			}
		}
	}

	/**
	 * Save all player data to the storage.yml file
	 */
	public void saveData() {
		StorageFile storageFile = DailyRewards.getFileManager().getStorageFile();
		storageFile.getFileConfiguration().set(uuid + ".reward-day", rewardDay);
		storageFile.getFileConfiguration().set(uuid + ".reward-allowed", rewardAllowed);
		storageFile.getFileConfiguration().set(uuid + ".next-reward-time", nextRewardTime);
		storageFile.getFileConfiguration().set(uuid + ".last-reward-time", lastRewardTime);
		storageFile.save();
	}

	/**
	 * Get player UUID
	 * 
	 * @return player uuid
	 */
	public UUID getUUID() {
		return uuid;
	}

	/**
	 * Get player instance
	 * 
	 * @return player instance
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Get player rewardAllowed
	 * 
	 * @return reward allowed
	 */
	public boolean rewardAllowed() {
		return rewardAllowed;
	}

	/**
	 * Get player current reward day
	 * 
	 * @return player reward day
	 */
	public int getRewardDay() {
		return rewardDay;
	}

	/**
	 * Get player next reward time
	 * 
	 * @return next reward time
	 */
	public long getNextRewardTime() {
		return nextRewardTime;
	}

	/**
	 * Get player last reward time
	 * 
	 * @return last reward time
	 */
	public long getLastRewardTime() {
		return lastRewardTime;
	}

	/**
	 * Get player GUI personnal inventory
	 * 
	 * @return player GUI personnal inventory
	 */
	public Inventory getGUI() {
		return gui;
	}

	/**
	 * Set nextRewardTime to the selected value
	 * 
	 * @param nextRewardTime
	 */
	public void setNextRewardTime(long nextRewardTime) {
		this.nextRewardTime = nextRewardTime;
	}

	/**
	 * Set lastRewardTime to the selected value
	 * 
	 * @param lastRewardTime
	 */
	public void setLastRewardTime(long lastRewardTime) {
		this.lastRewardTime = lastRewardTime;
	}

	/**
	 * Set rewardDay to the selected value
	 * 
	 * @param rewardDay
	 */
	public void setRewardDay(int rewardDay) {
		this.rewardDay = rewardDay;
	}

	/**
	 * Set rewardAllowed to the selected value
	 * 
	 * @param rewardAllowed
	 */
	public void setRewardAllowed(boolean rewardAllowed) {
		this.rewardAllowed = rewardAllowed;
	}

}
