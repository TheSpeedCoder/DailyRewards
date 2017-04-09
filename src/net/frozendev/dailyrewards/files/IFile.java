package net.frozendev.dailyrewards.files;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public interface IFile {

	/**
	 * Return an YamConfiguration instance of the file
	 * @return the FileConfiguration of the file
	 */
	public FileConfiguration getFileConfiguration();

	/**
	 * Return an File instance of the file
	 * @return the File
	 */
	public File getFile();

	/**
	 * Save the file in the /plugins/DailyRewards folder
	 */
	public void save();

	/**
	 * Reset the file in the /plugins/DailyRewards folder
	 */
	public void reset();

	/**
	 * Load the File in the FileConfiguration
	 */
	public void load();

	/**
	 * Delete the file in the /plugins/DailyRewards folder
	 */
	public void delete();
	
	/**
	 * Create the file in the /plugins/DailyRewards folder
	 */
	public void create();
	

}
