package mainframe.tests;

import fileHandler.factories.IBackupFactory;
import fileHandler.logic.IBackup;

/**
 * this stubs returns the same object - and we can make it return a brand new
 * object by invoking restart()
 * @author Emil
 *
 */
public class BackupStubFactory implements IBackupFactory {
	
	/**
	 * where we hold the BackupStub that this factory repeatedly returns 
	 */
	BackupStub backupStub = new BackupStub();

	@Override
	public BackupStub createInstance() {
		return backupStub;
	}
	
	/**
	 * 
	 * @return get the created BackupStub
	 */
	public BackupStub getCreatedBackupStub(){
		return backupStub;
	}
	
	/**
	 * makes this factory to return a brand new BackupStub object instead of the same one
	 */
	public void restart(){
		backupStub = new BackupStub();
	}

}
