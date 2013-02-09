package factories;

import votingStation.IVotingStation;
import votingStation.IVotingStationWindow;
import votingStation.VotingStation_window;

public class VotingStationWindowFactory implements IVotingStationWindowFactory {

	@Override
	public IVotingStationWindow createInstance(String name,
			IVotingStation caller) {
		return new VotingStation_window(name, caller);
	}

}
