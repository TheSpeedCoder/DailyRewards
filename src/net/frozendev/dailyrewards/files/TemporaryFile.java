package net.frozendev.dailyrewards.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class TemporaryFile implements IFile {

	/**
	 * Const for the file name
	 */
	public static final String FILE_NAME = "temporary.yml";

	/**
	 * Instance of File and FileConfiguration
	 */
	private File file;
	private FileConfiguration fileConfiguration;

	/**
	 * Instance of the plugin
	 */
	private Plugin plugin;

	/**
	 * Create new file object using plugin path and file name const, a new
	 * YamlConfiguration object, create the file and load it in
	 * FileConfiguration
	 * 
	 * @param plugin
	 */
	public TemporaryFile(Plugin plugin) {
		this.plugin = plugin;
		file = new File(this.plugin.getDataFolder() + "/" + FILE_NAME);
		fileConfiguration = new YamlConfiguration();
		create();
		load();
	}
	
	@Override
	public FileConfiguration getFileConfiguration() {
		return fileConfiguration;
	}

	@Override
	public File getFile() {
		return file;
	}

	@Override
	public void save() {
		try {
			fileConfiguration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reset() {
		delete();
		this.plugin.saveResource(FILE_NAME, false);
		load();
	}

	@Override
	public void load() {
		try {
			fileConfiguration.load(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete() {
		file.delete();
	}
	
	@Override
	public void create() {
		if (!file.exists())
			this.plugin.saveResource(FILE_NAME, false);
	}

}
