package votingStation.tests;

import votingStation.factories.IVotingStationWindowFactory;
import votingStation.gui.IVotingStationWindow;
import votingStation.logic.IVotingStation;

public class stationWindowStubFactory implements IVotingStationWindowFactory {
	
	private IVotingStationWindow windowStub;
	
	public stationWindowStubFactory(IVotingStationWindow windowStub) {
		this.windowStub = windowStub;
	}

	@Override
	public IVotingStationWindow createInstance(IVotingStation caller) {
		return windowStub;
	}

}
