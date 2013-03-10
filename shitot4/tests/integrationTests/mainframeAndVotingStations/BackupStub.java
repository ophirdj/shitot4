package integrationTests.mainframeAndVotingStations;

import org.junit.Assert;

import partiesList.model.IPartiesList;
import votersList.model.IVotersList;
import fileHandler.model.IBackup;

public class BackupStub implements IBackup{

	private IVotersList voters;
	private IVotersList unregisteredVoters;
	private IPartiesList parties;
	
	
	@Override
	public IVotersList restoreVoters() {
		if(voters == null) Assert.fail();
		return voters;
	}

	@Override
	public IVotersList restoreUnregisteredVoters() {
		if(unregisteredVoters == null) Assert.fail();
		return unregisteredVoters;
	}

	@Override
	public IPartiesList restoreParties() {
		if(parties == null) Assert.fail();
		return parties;
	}

	@Override
	public void storeState(IPartiesList parties, IVotersList voters,
			IVotersList unregistered) {
		if(voters == null) Assert.fail("voters list is null");
		if(unregistered == null) Assert.fail("unregistered list is null");
		if(parties == null) Assert.fail("parties list is null");
		this.voters = voters;
		this.unregisteredVoters = unregistered;
		this.parties = parties;
		matchLists(parties, voters, unregistered);
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
	
	/**
	 * Assert that given lists match the ones in backup
	 * @param parties
	 * @param voters
	 * @param unregistered
	 */
	public void matchLists(IPartiesList parties, IVotersList voters,
			IVotersList unregistered) {
		Assert.assertEquals(parties, this.parties);
		Assert.assertEquals(voters, this.voters);
		Assert.assertEquals(unregistered, this.unregisteredVoters);
		
	}	
}
