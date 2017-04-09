package net.frozendev.dailyrewards.files;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import net.frozendev.dailyrewards.DailyRewards;
import net.frozendev.dailyrewards.data.DayData;
import net.frozendev.dailyrewards.utils.StringUtils;

public class ConfigurationReader {

	private static final FileConfiguration CONFIG = DailyRewards.getPlugin().getConfig();
	
	/**
	 * Check if the day is reset at 7 days
	 * @param day
	 * @return boolean
	 */
	public static boolean openOnLogin() {
		return CONFIG.getBoolean("options.open-on-login");
	}
	
	/**
	 * Check if the day is reset at 7 days
	 * @param day
	 * @return boolean
	 */
	public static boolean resetAt7Day() {
		return CONFIG.getBoolean("options.reset-at-7-day");
	}
	
	/**
	 * Check if the item use custom lore or not
	 * @param day
	 * @return boolean
	 */
	public static boolean customMessage() {
		return CONFIG.getBoolean("options.custom-messages");
	}
	
	/**
	 * Check if the item use custom lore or not
	 * @param day
	 * @return boolean
	 */
	public static boolean customLore(int day) {
		if (CONFIG.getString("rewards.day" + day + ".lore").equals("%REWARDS%")) {
			return false;
		}
		return true;
	}
	
	/**
	 * Get the lore of the day
	 * @param day
	 * @return
	 */
	public static List<String> getDayLore(DayData day) {
		List<String> lore = new ArrayList<>();
		if (!customLore(day.getDay())) {
			double money = day.getMoney();
			List<ItemStack> items = day.getItems();
			HashMap<String, String> commands = day.getCommands();

			lore.add(ChatColor.WHITE + "------------------");

			if (money > 0) {
				lore.add(ChatColor.YELLOW + "Money : " + ChatColor.GOLD + money + ChatColor.YELLOW + "$");
				lore.add(" ");
			}

			if (items.size() > 0) {
				lore.add(ChatColor.BLUE + "Items :");
				for (ItemStack item : items) {
					lore.add(StringUtils.parseItemForLore(item.getType().name(), item.getAmount()));
				}
				lore.add(" ");
			}

			if (commands.size() > 0) {
				lore.add(ChatColor.GREEN + "Others :");
				for (String cmd : commands.values()) {
					lore.add(StringUtils.parseCommandForLore(cmd));
				}
				lore.add(" ");
			}
		} else {
			lore = CONFIG.getStringList("rewards.day" + day + ".lore");
			for(String str : lore) {
				lore.add(StringUtils.parseColor(str));
			}
		}

		return lore;
	}
	
	/**
	 * Get the money of reward of the day
	 * @param day
	 * @return
	 */
	public static double getMoneyReward(int day) {
		String configSection = "rewards.day" + day + ".rewards.";
		return CONFIG.getDouble(configSection + "money");
	}
	
	/**
	 * Get the items reward of the day
	 * @param day
	 * @return
	 */
	public static List<ItemStack> getItemsReward(int day) {
		String configSection = "rewards.day" + day + ".rewards.";
		
		List<ItemStack> items = new ArrayList<ItemStack>();
		List<String> materials = CONFIG.getStringList(configSection + "items");
		
		for (String mat : materials) {
			String[] splited = mat.split(":");
			if (splited.length > 1)
				items.add(new ItemStack(Material.getMaterial(splited[0]), Integer.parseInt(splited[1])));
			else
				items.add(new ItemStack(Material.getMaterial(splited[0]), 1));
		}
		
		return items;
	}
	
	/**
	 * Get the commands reward of the day
	 * @param day
	 * @return
	 */
	public static HashMap<String, String> getCommandsReward(int day) {
		String configSection = "rewards.day" + day + ".rewards.";
		
		HashMap<String, String> commands = new HashMap<String, String>();
		List<String> cmds = CONFIG.getStringList(configSection + "commands");
		
		for (String cmd : cmds) {
			String[] splited = cmd.split(":");
			if (splited.length > 1) {
				commands.put(splited[1], splited[0]);
			}
		}
		
		return commands;
	}
	
	
}
