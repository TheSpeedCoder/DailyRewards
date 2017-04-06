package net.frozendev.dailyrewards.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.frozendev.dailyrewards.DailyRewards;
import net.frozendev.dailyrewards.utils.InvUtils;
import net.frozendev.dailyrewards.utils.StringUtils;
import net.md_5.bungee.api.ChatColor;

public class PlayerData {

	private UUID uuid;
	private Player player;

	private boolean canTakeReward;
	private int day;

	private Date lastDate;
	private Date nextDate;

	private Inventory inv;

	public PlayerData(UUID uuid, int day, boolean canTakeReward, Date lastDate, Date nextDate) {
		this.uuid = uuid;
		this.player = Bukkit.getPlayer(uuid);
		this.day = day;
		this.lastDate = lastDate;
		this.nextDate = nextDate;
		this.canTakeReward = canTakeReward;
		this.inv = Bukkit.createInventory(player, 9, ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Daily Rewards");
		setupInventory();
	}

	public void setupInventory() {
		InvUtils.setInventoryContents(inv, day);
	}

	public void openInventory() {
		player.openInventory(inv);
	}

	public void giveReward() {
		List<String> rewards = GlobalData.DAY_REWARDS.get(day).getRewards();
		double money = 0;
		List<ItemStack> items = new ArrayList<>();

		for (String str : rewards) {
			String[] splited = null;
			if (str.contains(":"))
				splited = str.split(":");
			if (str.contains("money")) {
				if (splited != null)
					money = Double.parseDouble(splited[1]);
			}
			if (str.contains("item")) {
				if (splited != null) {
					String item = splited[1];
					int amount = Integer.parseInt(splited[2]);
					items.add(new ItemStack(Material.getMaterial(item), amount));
				}
			}
			if (str.contains("command")) {
				if (splited != null) {
					String cmd = splited[2];
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), StringUtils.parsePlayer(cmd, player));
				}
			}
		}

		DailyRewards.getVault().getEconomy().depositPlayer(player, money);
		for (ItemStack item : items)
			player.getInventory().addItem(item);

		canTakeReward = false;
		sendMessage();

		if (DailyRewards.getPlugin().getConfig().getBoolean("options.resetat7")) {
			if (day == 7)
				day = 1;
			else
				day++;
		} else {
			if (day < 7)
				day++;
		}

		updateDate();
	}

	public boolean canTakeReward() {
		return canTakeReward;
	}

	public int getDay() {
		return day;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public Date getNextDate() {
		return nextDate;
	}

	public Player getPlayer() {
		return player;
	}

	public UUID getUUID() {
		return this.uuid;
	}

	public Inventory getInventory() {
		return inv;
	}

	public void setCanTakeReward(boolean value) {
		canTakeReward = value;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setNextDate(Date date) {
		nextDate = date;
	}

	public void setLastDate(Date date) {
		lastDate = date;
	}

	private void sendMessage() {
		FileConfiguration config = DailyRewards.getPlugin().getConfig();
		int money = config.getInt("rewards.day" + day + ".reward.money");
		List<String> materials = (List<String>) config.getStringList("rewards.day" + day + ".reward.items");
		List<String> commands = (List<String>) config.getStringList("rewards.day" + day + ".reward.commands");
		player.sendMessage("-------------------");
		player.sendMessage("     " + ChatColor.DARK_GREEN + "DAILY REWARDS");
		player.sendMessage("          " + ChatColor.DARK_GREEN + "Day : " + ChatColor.GREEN + day);
		player.sendMessage("-------------------");
		player.sendMessage(" ");
		player.sendMessage(ChatColor.GREEN + "You received :");
		if (money > 0)
			player.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + money + ChatColor.GREEN + "$");

		if (materials.size() > 0) {
			for (String mat : materials) {
				player.sendMessage(StringUtils.parseMaterialForMessage(mat));
			}
		}
		
		if (commands.size() > 0) {
			for (String cmd : commands) {
				player.sendMessage(StringUtils.parseCommandForMessage(cmd));
			}
		}
	}

	public void updateCanTakeReward() {
		if (nextDate != null) {
			if (new Date().after(nextDate)) {
				canTakeReward = true;
			}
		}
	}

	public void updateDay() {
		if (nextDate != null) {
			if (new Date(nextDate.getTime() + (1000 * 60 * 60 * 24)).before(new Date())) {
				day = 1;
			}
		}
	}

	public void save() {
		FileConfiguration STORAGE = DailyRewards.getStorage().getFileConfiguration();

		if (STORAGE.get(uuid.toString()) != null) {
			STORAGE.set(uuid + ".day", day);
			STORAGE.set(uuid + ".cantakereward", canTakeReward);
			STORAGE.set(uuid + ".lastdate", lastDate);
			STORAGE.set(uuid + ".nextdate", nextDate);
		} else {
			STORAGE.set(uuid.toString() + ".day", day);
			STORAGE.set(uuid.toString() + ".cantakereward", canTakeReward);
			STORAGE.set(uuid.toString() + ".lastdate", lastDate);
			STORAGE.set(uuid.toString() + ".nextdate", nextDate);

		}
		DailyRewards.getStorage().save();
	}

	private void updateDate() {
		lastDate = new Date();
		nextDate = new Date(lastDate.getTime() + (1000 * 60 * 60 * 24));
	}

}
