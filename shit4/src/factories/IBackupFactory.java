package factories;

import XML.IBackup;

public interface IBackupFactory {
	/**
	 * @return new object that implement IBackup
	 */
	IBackup createInstance();
}
