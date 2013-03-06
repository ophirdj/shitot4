package votingStation.tests;

import java.util.Iterator;

import org.junit.Assert;

import partiesList.model.IPartiesList;
import votingStation.logic.IVotingStation;
import mainframe.communication.IStationsController;
import mainframe.logic.IMainframe;
import mainframe.logic.IMainframe.VoterDoesNotExist;
import mainframe.logic.IMainframe.VoterStatus;

public class StationsControllerStub implements IStationsController {
	
	public IMainframe.VoterStatus status = null;
	public int id = 0;

	@Override
	public void peep() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize(IPartiesList parties) {
		// TODO Auto-generated method stub

	}

	@Override
	public void retire() {
		// TODO Auto-generated method stub

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
	public IPartiesList hotBackup() {
		// TODO Auto-generated method stub
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

}
