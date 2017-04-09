package net.frozendev.dailyrewards.files;

import org.bukkit.plugin.Plugin;

public class FileManager {

	/**
	 * MessageFile instance
	 */
	private MessageFile messageFile;

	/**
	 * StorageFile instance
	 */
	private StorageFile storageFile;

	/**
	 * TemporaryFile instance
	 */
	private TemporaryFile temporaryFile;

	/**
	 * FileManager constructor Create instance for MessageFile, StorageFile and
	 * TemporaryFile
	 * @param instance of the plugin
	 */
	public FileManager(Plugin plugin) {
		messageFile = new MessageFile(plugin);
		storageFile = new StorageFile(plugin);
		temporaryFile = new TemporaryFile(plugin);
		plugin.saveDefaultConfig();
	}

	/**
	 * Return MessageFile instance
	 * @return instance of the MessageFile object
	 */
	public MessageFile getMessageFile() {
		return messageFile;
	}

	/**
	 * Return StorageFile instance
	 * @return instance of the StorageFile object
	 */
	public StorageFile getStorageFile() {
		return storageFile;
	}

	/**
	 * Return TemporaryFile instance
	 * @return instance of the TemporaryFile object
	 */
	public TemporaryFile getTemporaryFile() {
		return temporaryFile;
	}

}
