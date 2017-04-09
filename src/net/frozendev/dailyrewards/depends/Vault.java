package net.frozendev.dailyrewards.depends;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class Vault {

	private Economy economy = null;
	private Plugin plugin;
	
	public Vault(Plugin plugin) {
		this.plugin = plugin;
		setupEconomy();
	}
	
	private void setupEconomy() {
		if (plugin.getServer().getPluginManager().getPlugin("Vault") != null) {
			RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager()
					.getRegistration(Economy.class);
			if (rsp != null) {
				economy = rsp.getProvider();
			}
		}
	}
	
	public Economy getEconomy() {
		return economy;
	}
	
}
