package fileHandler.factories;

import fileHandler.logic.IBackup;

/**
 * interface of the factory of IBackup
 * @author Emil
 *
 */
public interface IBackupFactory {
	/**
	 * @return new object that implement IBackup
	 */
	IBackup createInstance();
}
