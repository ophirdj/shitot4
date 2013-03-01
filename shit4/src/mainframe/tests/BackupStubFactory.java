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
	
	BackupStub backupStub = new BackupStub();

	@Override
	public BackupStub createInstance() {
		return backupStub;
	}
	
	public BackupStub getCreatedBackupStub(){
		return backupStub;
	}
	
	public void restart(){
		backupStub = new BackupStub();
	}

}
