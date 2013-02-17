package factories;

import java.util.List;

import votingStation.IVotingStation;
import votingStation.VotingStation;

public class VotingStationFactory implements IVotingStationFactory {

	@Override
	public IVotingStation createInstance(List<String> passwords, String name,
			IChoosingListFactory choseFactory,
			IChoosingWindowFactory choseWindowFactory,
			IVotingStationWindowFactory stationWindowFactory) {
		return new VotingStation(passwords, name, choseFactory,
				choseWindowFactory, stationWindowFactory);
	}

}
