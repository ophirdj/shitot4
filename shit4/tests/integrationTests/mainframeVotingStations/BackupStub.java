package integrationTests.mainframeVotingStations;

import org.junit.Assert;

import partiesList.model.IPartiesList;
import votersList.model.IVotersList;
import fileHandler.logic.IBackup;

public class BackupStub implements IBackup{

	private IVotersList voters;
	private IVotersList unregisteredVoters;
	private IPartiesList parties;
	
	
	@Override
	public IVotersList restoreVoters() {
		return voters;
	}

	@Override
	public IVotersList restoreUnregisteredVoters() {
		return unregisteredVoters;
	}

	@Override
	public IPartiesList restoreParties() {
		return parties;
	}

	@Override
	public void storeState(IPartiesList parties, IVotersList voters,
			IVotersList unregistered) {
		this.voters = voters;
		this.unregisteredVoters = unregistered;
		this.parties = parties;
	}

	public void setVotersList(IVotersList voters) {
		this.voters = voters;
	}

	public void setPartiesList(IPartiesList parties) {
		this.parties = parties;
	}

	public void setUnregisteredVoterList(IVotersList voters) {
		this.unregisteredVoters = voters;
	}
	
	public void matchLists(IPartiesList parties, IVotersList voters,
			IVotersList unregistered) {
		Assert.assertEquals(parties, this.parties);
		Assert.assertEquals(voters, this.voters);
		Assert.assertEquals(unregistered, this.unregisteredVoters);
		
	}
	
}
