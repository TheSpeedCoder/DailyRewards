package net.frozendev.dailyrewards.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import net.frozendev.dailyrewards.DailyRewards;
import net.frozendev.dailyrewards.utils.ItemUtils;

public class GlobalData {

	/**
	 * FileConfiguration of config.yml
	 */
	private static final FileConfiguration CONFIG = DailyRewards.getPlugin().getConfig();

	/**
	 * Storage for all the PlayerData using UUID
	 */
	public static final HashMap<UUID, PlayerData> PLAYERS = new HashMap<>();

	/**
	 * Storage for all day of rewards
	 */
	public static final HashMap<Integer, DayData> DAY_REWARDS = new HashMap<>();

	/**
	 * All items for the GUI (in red & green)
	 */
	public static final List<ItemStack> GREEN_ITEMS = new ArrayList<ItemStack>();
	public static final List<ItemStack> RED_ITEMS = new ArrayList<ItemStack>();

	/**
	 * Add to DAY_REWARDS each day (1 to 7 with every rewards in the config.yml)
	 * Add to GREEN_ITEMS and RED_ITEMS 7 STAINED_GLASS_PANEL with the wanted
	 * color and name
	 */
	static {
		for (int i = 1; i < 8; i++) {
			DAY_REWARDS.put(i, new DayData(i));
			GREEN_ITEMS.add(ItemUtils.customItem(ChatColor.DARK_GREEN + CONFIG.getString("rewards.day" + i + ".name"),
					Material.STAINED_GLASS_PANE, 5, DAY_REWARDS.get(i).getItemLore(), ChatColor.GREEN));
			RED_ITEMS.add(ItemUtils.customItem(ChatColor.DARK_RED + CONFIG.getString("rewards.day" + i + ".name"),
					Material.STAINED_GLASS_PANE, 14, DAY_REWARDS.get(i).getItemLore(), ChatColor.RED));
		}
	};

}
