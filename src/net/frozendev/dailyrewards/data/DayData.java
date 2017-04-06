package net.frozendev.dailyrewards.data;

import java.util.List;

public class DayData {

	private int day;
	private List<String> rewards;
	
	public DayData(int day, List<String> rewards) {
		this.day = day;
		this.rewards = rewards;
	}
	
	public int getDay() {
		return day;
	}
	
	public List<String> getRewards() {
		return rewards;
	}
	
}
