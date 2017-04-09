package net.frozendev.dailyrewards.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.frozendev.dailyrewards.DailyRewards;


public class StringUtils {

	/**
	 * Set the first char of the string to Upper all next to lower
	 * @param str
	 * @return string
	 */
	public static String firstCharToUpperNextToLower(String str) {
		str = str.toLowerCase();
		char c[] = str.toCharArray();
		c[0] = Character.toUpperCase(c[0]);
		str = new String(c);
		return str;
	}

	/**
	 * Convert long to time format dd:hh:mm:ss
	 * @param time
	 * @return string time
	 */
	public static String convertLongToString(long time) {
		
		int SECOND = 1000;
		int MINUTE = 60 * SECOND;
		int HOUR = 60 * MINUTE;
		int DAY = 24 * HOUR;
		
		long ms = time;
		StringBuffer text = new StringBuffer("");
		
		if (ms > DAY) {
			text.append(ms / DAY).append(":");
			ms %= DAY;
		}
		if (ms > HOUR) {
			text.append(ms / HOUR).append(":");
			ms %= HOUR;
		}
		if (ms > MINUTE) {
			text.append(ms / MINUTE).append(":");
			ms %= MINUTE;
		}
		return text.toString();
	}
	
	/**
	 * Global parser for all daily rewards message
	 * @param msg
	 * @return
	 */
	public static String parseDailyRewardsMessage(String msg) {
		return ChatColor.GREEN + "[" + ChatColor.WHITE + "Daily Rewards" + ChatColor.GREEN + "] " + ChatColor.WHITE
				+ msg;
	}

	/**
	 * Parse player var
	 * @param str
	 * @param p
	 * @return string parsed
	 */
	public static String parsePlayer(String str, Player p) {
		return str.replace("%PLAYER%", p.getName());
	}
	
	/**
	 * Parse time
	 * @param msg
	 * @param time
	 * @return parsed string
	 */
	public static String parseTime(String msg, String time) {
		String[] splitedTime = time.split(":");
		String str = msg.replace("%HOURS%", splitedTime[0]);
		str = str.replaceAll("%MINUTES%", splitedTime[1]);
		return str;
	}
	
	/**
	 * Parse the color of the string config
	 * @param str
	 * @return parsed string
	 */
	public static String parseColor(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
	
	/**
	 * Remove _ and call firstCharToUpperNextLower() method
	 * @param str
	 * @return
	 */
	public static String parseForLore(String str) {
		str = str.replace("_", " ");
		str = firstCharToUpperNextToLower(str);
		return str;
	}
	
	/**
	 * Parse material for lore
	 * @param item
	 * @param amount
	 * @return
	 */
	public static String parseItemForLore(String item, int amount) {
		String str = parseForLore(item);
		return " " + ChatColor.DARK_AQUA + str + ChatColor.AQUA + " x " + ChatColor.DARK_AQUA + amount;
	}
	
	/**
	 * Parse command for lore
	 * @param cmd
	 * @return
	 */
	public static String parseCommandForLore(String cmd) {
		return " " + ChatColor.DARK_GREEN + cmd + ChatColor.GREEN;
	}
	
	/**
	 * Parse command for message
	 * @param cmd
	 * @return
	 */
	public static String parseCommandForMessage(String cmd) {
		return ChatColor.GREEN + "- " + ChatColor.WHITE + cmd;
	}
	
	/**
	 * Parse item for message
	 * @param item
	 * @param amount
	 * @return
	 */
	public static String parseItemForMessage(String item, int amount) {
		item = parseForLore(item);
		return ChatColor.GREEN + "- " + ChatColor.WHITE + item + ChatColor.GREEN + " x " + ChatColor.WHITE + amount;
	}
	
	/**
	 * Get message in config and parse the color
	 * @param str
	 * @return
	 */
	public static String messageConfigColorParsed(String str) {
		return parseColor(DailyRewards.getFileManager().getMessageFile().getFileConfiguration().getString(str));
	}
	
}
