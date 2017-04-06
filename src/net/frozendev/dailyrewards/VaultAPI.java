package net.frozendev.dailyrewards;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class VaultAPI {

	private Plugin plugin;
    private Economy economy = null;

    public VaultAPI(Plugin plugin) {
    	this.plugin = plugin;
    	setupEconomy();
    }
    
    private void setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
    }
    
    public Economy getEconomy() {
    	return economy;
    }
	
}
