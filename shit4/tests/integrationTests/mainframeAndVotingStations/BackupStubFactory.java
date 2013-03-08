package integrationTests.mainframeAndVotingStations;


import partiesList.model.IPartiesList;
import votersList.model.IVotersList;
import fileHandler.factories.IBackupFactory;
import fileHandler.logic.IBackup;

public class BackupStubFactory implements IBackupFactory{

	private BackupStub backupStub;


	public BackupStubFactory() {
		backupStub = new BackupStub();
	}
	
	public void setVoterList(IVotersList voters){
		backupStub.setVotersList(voters);
	}
	
	public void setPartiesList(IPartiesList parties){
		backupStub.setPartiesList(parties);
	}
	
	public void setUnregisteredVoterList(IVotersList voters){
		backupStub.setUnregisteredVoterList(voters);
	}
	
	@Override
	public IBackup createInstance() {
		return backupStub;
	}
	
	/**
	 * Assert that given lists match the ones in the backup
	 * @param parties
	 * @param voters
	 * @param unregistered
	 */
	public void matchLists(IPartiesList parties, IVotersList voters,
			IVotersList unregistered) {
		backupStub.matchLists(parties, voters, unregistered);
	}	
}
