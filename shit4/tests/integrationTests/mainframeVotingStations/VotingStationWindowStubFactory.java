package integrationTests.mainframeVotingStations;

import votingStation.factories.IVotingStationWindowFactory;
import votingStation.gui.IVotingStationWindow;
import votingStation.logic.IVotingStation;

public class VotingStationWindowStubFactory implements
		IVotingStationWindowFactory {
	
	private IntegrationTest test;
	
	public VotingStationWindowStubFactory(IntegrationTest test) {
		this.test = test;
	}

	@Override
	public IVotingStationWindow createInstance(IVotingStation caller) {
		VotingStationWindowStub stub = new VotingStationWindowStub(caller);
		test.addVotingWindowStub(stub);
		return stub;
	}

}
