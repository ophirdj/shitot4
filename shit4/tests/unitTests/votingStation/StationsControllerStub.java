package unitTests.votingStation;

import org.junit.Assert;
import partiesList.model.IPartiesList;
import mainframe.communication.IStationsController;
import mainframe.logic.IMainframe;
import mainframe.logic.IMainframe.VoterDoesNotExist;
import mainframe.logic.IMainframe.VoterStatus;

public class StationsControllerStub implements IStationsController {
	
	public IMainframe.VoterStatus status = null;
	public int id = 0;
	private boolean isInit;

	@Override
	public void peep() {
	}

	@Override
	public void initialize(IPartiesList parties) {
		isInit = true;
	}

	@Override
	public void retire() {
	}

	@Override
	public VoterStatus getVoterStatus(int id) {
		return status;
	}

	@Override
	public void markVoted(int id) throws VoterDoesNotExist {
		Assert.assertEquals(this.id, id);

	}

	@Override
	public IPartiesList gatherVotesFromVotingStations() {
		return null;
	}

	@Override
	public void markStartedVote(int id) throws VoterDoesNotExist {
		Assert.assertEquals(this.id, id);
	}

	@Override
	public boolean checkParties(IPartiesList partiesList) {
		return true;
	}

	@Override
	public boolean checkInit() {
		return isInit;
	}

}
