package mainframe.tests;

import java.util.Iterator;

import partiesList.model.IPartiesList;
import votingStation.logic.IVotingStation;
import mainframe.communication.IStationsController;
import mainframe.logic.IMainframe.VoterDoesNotExist;
import mainframe.logic.IMainframe.VoterStartedVote;
import mainframe.logic.IMainframe.VoterStatus;

public class StationsControllerStub implements IStationsController {

	@Override
	public Iterator<IVotingStation> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void markVoted(int id) throws VoterDoesNotExist {
		// TODO Auto-generated method stub

	}

	@Override
	public void markStartedVote(int id) throws VoterDoesNotExist,
			VoterStartedVote {
		// TODO Auto-generated method stub

	}

	@Override
	public IPartiesList hotBackup() {
		// TODO Auto-generated method stub
		return null;
	}

}
