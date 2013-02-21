package unitTests;

import votingStation.IVotingStation;
import votingStation.IVotingStationWindow;
import factories.IVotingStationWindowFactory;

public class VotingStationWindowFactoryStub implements IVotingStationWindowFactory{

	@Override
	public IVotingStationWindow createInstance(String name,
			IVotingStation caller) {
		return new VotingStationWindowStub();
	}

}
