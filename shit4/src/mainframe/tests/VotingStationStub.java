package mainframe.tests;

import mainframe.communication.IStationsController;
import partiesList.model.IPartiesList;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;
import votingStation.logic.IVotingStation;

public class VotingStationStub implements IVotingStation {

	@Override
	public void initialize(IPartiesList parties, IStationsController controller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void retire() {
		// TODO Auto-generated method stub

	}

	@Override
	public IPartiesList getPartiesList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void voting() throws ChoosingInterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public void testVoting() throws ChoosingInterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public void peep() {
		// TODO Auto-generated method stub

	}

}
