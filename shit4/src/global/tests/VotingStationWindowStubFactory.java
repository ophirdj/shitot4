package global.tests;

import votingStation.factories.IVotingStationWindowFactory;
import votingStation.gui.IVotingStationWindow;
import votingStation.logic.IVotingStation;

public class VotingStationWindowStubFactory implements
		IVotingStationWindowFactory {
	
	private AcceptanceTest test;
	
	public VotingStationWindowStubFactory(AcceptanceTest test) {
		this.test = test;
	}

	@Override
	public IVotingStationWindow createInstance(IVotingStation caller) {
		VotingStationWindowStub stub = new VotingStationWindowStub(caller);
		test.addVotingWindowStub(stub);
		return stub;
	}

}
