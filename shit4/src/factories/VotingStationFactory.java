package factories;

import java.util.List;

import GUI.Main_Window;

import votingStation.IVotingStation;
import votingStation.VotingStation;

public class VotingStationFactory implements IVotingStationFactory {

	@Override
	public IVotingStation createInstance(List<String> passwords, String name,
			IChoosingListFactory choseFactory,
			IChoosingWindowFactory choseWindowFactory,
			IVotingStationWindowFactory stationWindowFactory,
			Main_Window mainWindow) {
		return new VotingStation(passwords, name, choseFactory,
				choseWindowFactory, stationWindowFactory, mainWindow);
	}

}
