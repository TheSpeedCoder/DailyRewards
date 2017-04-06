package net.frozendev.dailyrewards.utils;

import org.bukkit.inventory.Inventory;

import net.frozendev.dailyrewards.data.GlobalData;

public class InvUtils {

	public static void setInventoryContents(Inventory inv, int day) {
		
        for(int i = 1; i < GlobalData.GREEN_ITEMS.size() + 1; i++) {
            if ((i) == day)
                inv.setItem(i, GlobalData.GREEN_ITEMS.get(i-1));
            else
                inv.setItem(i, GlobalData.RED_ITEMS.get(i-1));
        }
		
	}
	
}
