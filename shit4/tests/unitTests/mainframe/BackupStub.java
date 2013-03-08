package unitTests.mainframe;

import partiesList.model.IPartiesList;
import votersList.model.IVotersList;
import fileHandler.logic.IBackup;

/**
 * a Backup stub
 * allows use to use store and restore as we would expect from the class
 * also defines methods that set a specific list - instead of 'storeState' that requests them all
 * @author Emil
 *
 */
public class BackupStub implements IBackup {
	
	private IVotersList backupedVotersList;
	private IVotersList backupedUnregisteredVotersList;
	private IPartiesList backupedPartiesList;
	
	/**
	 * set the voters list of the stub
	 * @param v the voters list which we want to set in the stub
	 */
	public void setBackupedVotersList(IVotersList v){
		backupedVotersList = v.copy();
	}
	
	/**
	 * set the unregistered voters list of the stub
	 * @param unregistered the unregistered voters list which we want to set in the stub
	 */
	public void setBackupedUnregisteredVotersList(IVotersList unregistered){
		backupedUnregisteredVotersList = unregistered.copy();
	}
	
	/**
	 * set the parties list of the stub
	 * @param p the parties list which we want to set in the stub
	 */
	public void setBackupedPartiesList(IPartiesList p){
		backupedPartiesList = p.copy();
	}
	
	
	@Override
	public IVotersList restoreVoters() {
		return backupedVotersList.copy();
	}

	@Override
	public IVotersList restoreUnregisteredVoters() {
		return backupedUnregisteredVotersList.copy();
	}

	@Override
	public IPartiesList restoreParties() {
		return backupedPartiesList.copy();
	}

	@Override
	public void storeState(IPartiesList parties, IVotersList voters,
			IVotersList unregistered) {
		backupedVotersList = voters.copy();
		backupedUnregisteredVotersList = unregistered.copy();
		backupedPartiesList = parties.copy();

	}

}
