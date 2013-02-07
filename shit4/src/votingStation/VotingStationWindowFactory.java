package votingStation;

public class VotingStationWindowFactory implements IVotingStationWindowFactory {

	@Override
	public IVotingStationWindow createInstance(String name,
			IVotingStation caller) {
		return new VotingStation_window(name, caller);
	}

}
