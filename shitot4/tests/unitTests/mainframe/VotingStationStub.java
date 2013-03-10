package unitTests.mainframe;

import mainframe.communication.IStationsController;
import partiesList.model.IPartiesList;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;
import votingStation.logic.IVotingStation;

/**
 * this stub is here only because without it the Mainframe wont work
 * we do not bother to implement any behavior for this stub
 * @author Emil
 *
 */
public class VotingStationStub implements IVotingStation {

	private boolean isInit;

	@Override
	public void initialize(IPartiesList parties, IStationsController controller) {
		isInit = true;
	}

	@Override
	public void retire() {

	}

	@Override
	public IPartiesList getPartiesList() {
		return null;
	}

	@Override
	public void voting() throws ChoosingInterruptedException {

	}

	@Override
	public void testVoting() throws ChoosingInterruptedException {

	}

	@Override
	public void peep() {

	}

	@Override
	public boolean isInit() {
		return isInit;
	}

}
