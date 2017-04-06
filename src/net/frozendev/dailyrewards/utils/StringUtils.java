package net.frozendev.dailyrewards.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StringUtils {

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;
	
    public static String firstCharToUpperNextToLower(String str) {
        str = str.toLowerCase();
        char c[] = str.toCharArray();
        c[0] = Character.toUpperCase(c[0]);
        str = new String(c);
        return str;
    }

    
    public static String parseMaterialForMessage(String mat) {
        String[] itemInfo = parseConfigItem(mat);
        String amount = parseConfigItem(mat)[1];
        String str = firstCharToUpperNextToLower(itemInfo[0]);
        return ChatColor.GREEN + "- " + ChatColor.WHITE + str + ChatColor.GREEN + " x " + ChatColor.WHITE + amount;
    }
    
    public static String parseMaterialForLore(String mat) {
        String[] itemInfo = parseConfigItem(mat);
        String amount = parseConfigItem(mat)[1];
        String str = firstCharToUpperNextToLower(itemInfo[0]);
        return " " + ChatColor.DARK_AQUA + str + ChatColor.AQUA + " x " + ChatColor.DARK_AQUA + amount;
    }
    
    public static String parseCommandForLore(String cmd) {
        String[] itemInfo = parseConfigItem(cmd);
        String title = itemInfo[0];
        return " " + ChatColor.DARK_GREEN + title + ChatColor.GREEN;
    }
    
    public static String parseCommandForMessage(String cmd) {
        String[] itemInfo = parseConfigItem(cmd);
        String title = itemInfo[0];
        return ChatColor.GREEN + "- " + ChatColor.WHITE + title;
    }
    
    public static String[] parseConfigItem(String str) {
        if (str.contains(":"))
            return str.split(":");
        else
            return new String[] { str, "1" };  
    }
   
    public static String convertLongToString(long time) {
    	long ms = time;
    	StringBuffer text = new StringBuffer("");
    	if (ms > DAY) {
    	  text.append(ms / DAY).append(" days ");
    	  ms %= DAY;
    	}
    	if (ms > HOUR) {
    	  text.append(ms / HOUR).append(" hours ");
    	  ms %= HOUR;
    	}
    	if (ms > MINUTE) {
    	  text.append(ms / MINUTE).append(" minutes ");
    	  ms %= MINUTE;
    	}
    	return text.toString();
    }
    
    public static String parseDailyMessage(String msg) {
    	return ChatColor.GREEN + "[" + ChatColor.WHITE + "Daily Rewards" + ChatColor.GREEN + "] " + ChatColor.WHITE + msg;
    }
    
    public static String parsePlayer(String str, Player p) {
    	return str.replace("%PLAYER%", p.getName());
    }
	
}
