package net.frozendev.dailyrewards.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import net.frozendev.dailyrewards.DailyRewards;

public class ConfigUtils {

	private static final FileConfiguration CONFIG = DailyRewards.getPlugin().getConfig();
	
	public static List<String> getRewards(int day) {
		List<String> rewards = new ArrayList<>();
        int money = CONFIG.getInt("rewards.day" + day + ".reward.money");
        List<String> materials = (List<String>)CONFIG.getStringList("rewards.day" + day + ".reward.items");
        List<String> commands = (List<String>)CONFIG.getStringList("rewards.day" + day + ".reward.commands");
        if (money > 0) {
        	rewards.add("money:" + money);
        }
        if (materials.size() > 0) {
           	for(String mat : materials) {
            	String[] itemParsed = StringUtils.parseConfigItem(mat);
            	rewards.add("item:"+ itemParsed[0] + ":" + itemParsed[1]);
        	}
        }
        if (commands.size() > 0) {
           	for(String cmd : commands) {
            	rewards.add("command:"+ cmd);
        	}
        }
        return rewards;
	}
	
    public static List<String> parseRewards(int day) {
        List<String> lore = new ArrayList<>();
        int money = CONFIG.getInt("rewards.day" + day + ".reward.money");
        List<String> materials = (List<String>)CONFIG.getStringList("rewards.day" + day + ".reward.items");
        List<String> commands = (List<String>)CONFIG.getStringList("rewards.day" + day + ".reward.commands");
        
        lore.add(ChatColor.WHITE + "------------------");
        
        if (money > 0) {
        	lore.add(ChatColor.YELLOW + "Money : " + ChatColor.GOLD + money + ChatColor.YELLOW + "$");
        	lore.add(" ");
        }
        
        if (materials.size() > 0) {
            lore.add(ChatColor.BLUE + "Items :");
            for(String mat : materials) {
                lore.add(StringUtils.parseMaterialForLore(mat));
            }
            lore.add(" ");
        }
        
        if (commands.size() > 0) {
            lore.add(ChatColor.GREEN + "Others :");
            for(String cmd : commands) {
                lore.add(StringUtils.parseCommandForLore(cmd));
            }
            lore.add(" ");
        }
        
        return lore;
    }
	
}
