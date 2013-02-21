package fileHandler.factories;

import fileHandler.logic.IBackup;

public interface IBackupFactory {
	/**
	 * @return new object that implement IBackup
	 */
	IBackup createInstance();
}
