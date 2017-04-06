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
import net.frozendev.dailyrewards.utils.ConfigUtils;
import net.frozendev.dailyrewards.utils.ItemUtils;

public class GlobalData {

	private static final FileConfiguration CONFIG = DailyRewards.getPlugin().getConfig();

	public static final HashMap<UUID, PlayerData> PLAYERS = new HashMap<>();
	public static final HashMap<Integer, DayData> DAY_REWARDS = new HashMap<>();

	public static final List<ItemStack> GREEN_ITEMS = new ArrayList<ItemStack>();
	public static final List<ItemStack> RED_ITEMS = new ArrayList<ItemStack>();

	static {
		for (int i = 1; i < 8; i++) {
			DAY_REWARDS.put(i, new DayData(i, ConfigUtils.getRewards(i)));
			GREEN_ITEMS.add(ItemUtils.customItem(ChatColor.DARK_GREEN + CONFIG.getString("rewards.day" + i + ".name"),
					Material.STAINED_GLASS_PANE, 5, ConfigUtils.parseRewards(i), ChatColor.GREEN));
			RED_ITEMS.add(ItemUtils.customItem(ChatColor.DARK_RED + CONFIG.getString("rewards.day" + i + ".name"),
					Material.STAINED_GLASS_PANE, 14, ConfigUtils.parseRewards(i), ChatColor.RED));
		}
	};

}
