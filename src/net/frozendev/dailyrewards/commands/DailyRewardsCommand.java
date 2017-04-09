package net.frozendev.dailyrewards.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.frozendev.dailyrewards.data.GlobalData;

public class DailyRewardsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String str, String[] args) {
		if (cs instanceof Player) {
			Player p = (Player) cs;
			GlobalData.PLAYERS.get(p.getUniqueId()).openGUI();
		}
		return true;
	}

}
