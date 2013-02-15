package factories;

import GUI.Main_Window;
import votingStation.IVotingStation;
import votingStation.IVotingStationWindow;
import votingStation.VotingStation_window;

public class VotingStationWindowFactory implements IVotingStationWindowFactory {

	@Override
	public IVotingStationWindow createInstance(String name,
			IVotingStation caller, Main_Window mainWindow) {
		return new VotingStation_window(name, caller, mainWindow);
	}

}
