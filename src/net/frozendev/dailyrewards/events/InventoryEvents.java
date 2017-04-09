package net.frozendev.dailyrewards.events;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import net.frozendev.dailyrewards.data.GlobalData;
import net.frozendev.dailyrewards.data.PlayerData;
import net.frozendev.dailyrewards.utils.StringUtils;

public class InventoryEvents implements Listener {
	
	@EventHandler
	public void playerInventoryOpen(InventoryOpenEvent e) {
		if (e.getPlayer() instanceof Player) {
			Player p = (Player) e.getPlayer();
			if (GlobalData.PLAYERS.containsKey(p.getUniqueId())) {
				PlayerData pData = GlobalData.PLAYERS.get(p.getUniqueId());
				if (e.getInventory().equals(pData.getGUI())) {
					pData.setupGUI();
					pData.updateRewardAllowed();
					pData.updateDay();
				}
			}
		}
	}

	@EventHandler
	public void playerInventoryClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			PlayerData pData = GlobalData.PLAYERS.get(p.getUniqueId());
			if (pData.getGUI().equals(e.getInventory())) {
				if (pData.rewardAllowed()) {
					ItemStack item = e.getCurrentItem();
					if (item != null) {
						if (GlobalData.GREEN_ITEMS.contains(item)) {
							pData.giveReward();
							p.closeInventory();
						}
					}
				} else {
					long difference = pData.getNextRewardTime() - new Date().getTime();
					p.sendMessage(StringUtils.parseDailyRewardsMessage(StringUtils.parseTime(
							StringUtils.messageConfigColorParsed("wait-message"),
							StringUtils.convertLongToString(difference))));
				}
				e.setCancelled(true);
			}
		}
	}
}
