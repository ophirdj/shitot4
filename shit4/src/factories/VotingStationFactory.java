package factories;

import java.util.List;

import votingStation.IVotingStation;
import votingStation.VotingStation;

public class VotingStationFactory implements IVotingStationFactory {

	private IChoosingListFactory choseFactory;
	private IVotingStationWindowFactory stationWindowFactory;

	public VotingStationFactory(IChoosingListFactory choseFactory,
	IVotingStationWindowFactory stationWindowFactory) {
		this.choseFactory = choseFactory;
		this.stationWindowFactory = stationWindowFactory;
	}
	
	@Override
	public IVotingStation createInstance(List<String> passwords, String name) {
		return new VotingStation(passwords, name, choseFactory,stationWindowFactory);
	}

}
