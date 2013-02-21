package votingStation.test;

import votingStation.factories.IVotingStationWindowFactory;
import votingStation.gui.IVotingStationWindow;
import votingStation.logic.IVotingStation;

public class VotingStationWindowFactoryStub implements IVotingStationWindowFactory{

	@Override
	public IVotingStationWindow createInstance(
			IVotingStation caller) {
		return new VotingStationWindowStub();
	}

}
