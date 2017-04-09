package net.frozendev.dailyrewards.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.frozendev.dailyrewards.DailyRewards;
import net.frozendev.dailyrewards.data.GlobalData;
import net.frozendev.dailyrewards.data.PlayerData;
import net.frozendev.dailyrewards.files.StorageFile;
import net.frozendev.dailyrewards.files.TemporaryFile;

public class GlobalUtils {

	/**
	 * Set the inventory content according to day
	 * 
	 * @param inv
	 * @param day
	 */
	public static void setGUIContents(Inventory inv, int day) {
		for (int i = 1; i < GlobalData.GREEN_ITEMS.size() + 1; i++) {
			if ((i) == day)
				inv.setItem(i, GlobalData.GREEN_ITEMS.get(i - 1));
			else
				inv.setItem(i, GlobalData.RED_ITEMS.get(i - 1));
		}
	}

	/**
	 * Create custom item
	 * 
	 * @param name
	 * @param material
	 * @param colorId
	 * @param lore
	 * @param loreColor
	 * @return itemstack
	 */
	public static ItemStack customItem(String name, Material material, int colorId, List<String> lore,
			ChatColor loreColor) {
		ItemStack item = new ItemStack(material, 1, (short) colorId);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(loreColor + "" + name);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public static void savePlayers() {
		TemporaryFile temporary = DailyRewards.getFileManager().getTemporaryFile();
		List<String> strList = new ArrayList<String>();
		for (UUID id : GlobalData.PLAYERS.keySet()) {
			strList.add(id.toString());
		}
		temporary.getFileConfiguration().set("players", strList);
		temporary.save();
		for (PlayerData pData : GlobalData.PLAYERS.values()) {
			pData.saveData();
		}
	}

	public static void loadPlayers() {
		StorageFile storage = DailyRewards.getFileManager().getStorageFile();
		TemporaryFile temporary = DailyRewards.getFileManager().getTemporaryFile();
		List<String> players = (List<String>) temporary.getFileConfiguration().getStringList("players");
		if (players != null) {
			if (players.size() > 0) {
				for (String str : players) {
					UUID uuid = UUID.fromString(str);
					PlayerData pData = 						new PlayerData(uuid, storage.getFileConfiguration().getBoolean(uuid + ".reward-allowed"),
							storage.getFileConfiguration().getInt(uuid + ".reward-day"),
							storage.getFileConfiguration().getLong(uuid + ".next-reward-time"),
							storage.getFileConfiguration().getLong(uuid + ".last-reward-time"));
					if (!GlobalData.PLAYERS.containsKey(uuid))
						GlobalData.PLAYERS.put(uuid, pData);
				}
			}
		}
		temporary.reset();
	}
}
