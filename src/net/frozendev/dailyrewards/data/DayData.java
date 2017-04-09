package net.frozendev.dailyrewards.data;

import java.util.HashMap;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import net.frozendev.dailyrewards.files.ConfigurationReader;

public class DayData {

	/**
	 * Number of the day
	 */
	private int day;
	
	/**
	 * Money give in reward of the day
	 */
	private double money;
	
	/**
	 * Items give in reward of the day
	 */
	private List<ItemStack> items;
	
	/**
	 * Commands executed in reward of the day
	 */
	private HashMap<String, String> commands;
	
	/**
	 * Lore of the day item
	 */
	private List<String> itemLore;

	/**
	 * Constructor
	 * @param day
	 */
	public DayData(int day) {
		this.day = day;
		this.money = ConfigurationReader.getMoneyReward(day);
		this.items = ConfigurationReader.getItemsReward(day);
		this.commands = ConfigurationReader.getCommandsReward(day);
		this.itemLore = ConfigurationReader.getDayLore(this);
	}

	/**
	 * Return the day
	 * @return day
	 */
	public int getDay() {
		return day;
	}
	
	/**
	 * Return the items of the day
	 * @return items;
	 */
	public List<ItemStack> getItems() {
		return items;
	}

	/**
	 * Return the commands of the day
	 * @return commands
	 */
	public HashMap<String, String> getCommands() {
		return commands;
	}
	
	/**
	 * Get the money in reward of the day
	 * @return money
	 */
	public double getMoney() {
		return money;
	}
	
	/**
	 * Return item lore
	 * @return item lore
	 */
	public List<String> getItemLore() {
		return itemLore;
	}
	
}
