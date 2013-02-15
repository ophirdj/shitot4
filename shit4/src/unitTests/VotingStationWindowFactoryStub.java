package unitTests;

import GUI.Main_Window;
import votingStation.IVotingStation;
import votingStation.IVotingStationWindow;
import factories.IVotingStationWindowFactory;

public class VotingStationWindowFactoryStub implements IVotingStationWindowFactory{

	@Override
	public IVotingStationWindow createInstance(String name,
			IVotingStation caller, Main_Window mainWindow) {
		return new VotingStationWindowStub("", mainWindow);
	}

}
