package mainframe.tests;

import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import partiesList.factories.PartiesListFactory;
import partiesList.factories.PartyFactory;
import partiesList.model.IPartiesList;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import votersList.factories.VoterDataFactory;
import votersList.factories.VotersListFactory;
import votersList.model.IVotersList;
import votersList.model.VotersList;
import fileHandler.logic.IBackup;

public class BackupStub implements IBackup {

	
	private IPartyFactory partyFactory = new PartyFactory();
	private IVoterDataFactory voterDataFactory = new VoterDataFactory();
	private IVotersListFactory votersListFactory = new VotersListFactory();
	private IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
	
	/*IVotersList restoreVotersList;
	IVotersList restoreUnregisteredVotersList;
	IPartiesList restorePartiesList;*/
	
	/*IVotersList storeVotersList;
	IVotersList storeUnregisteredVotersList;
	IPartiesList storePartiesList;*/
	
	private IVotersList backupedVotersList;
	private IVotersList backupedUnregisteredVotersList;
	private IPartiesList backupedPartiesList;
	
	
	public void setBackupedVotersList(IVotersList v){
		backupedVotersList = v.copy();
	}
	
	public void setBackupedUnregisteredVotersList(IVotersList unregistered){
		backupedUnregisteredVotersList = unregistered.copy();
	}
	
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
