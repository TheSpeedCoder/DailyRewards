package net.frozendev.dailyrewards;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Storage {

	private File f;
	private FileConfiguration fileConfig;
	
	public Storage() {
		f = new File(DailyRewards.getPlugin().getDataFolder() + "/storage.yml");
		if (!f.exists())
			DailyRewards.getPlugin().saveResource("storage.yml", false);
		fileConfig = new YamlConfiguration();
		try {
			fileConfig.load(f);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getFileConfiguration() {
		return fileConfig;
	}
	
	public File getFile() {
		return f;
	}
	
	public void save() {
		try {
			fileConfig.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
