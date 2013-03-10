package votingStation.factories;

import java.util.List;

import choosingList.factories.IChoosingListFactory;

import votingStation.logic.IVotingStation;
import votingStation.logic.VotingStation;

public class VotingStationFactory implements IVotingStationFactory {

	private IChoosingListFactory chooseFactory;
	private IVotingStationWindowFactory stationWindowFactory;
	private IVotingRecordFactory votingRecordFactory;

	public VotingStationFactory(IChoosingListFactory chooseFactory,
	IVotingStationWindowFactory stationWindowFactory, IVotingRecordFactory votingRecordFactory) {
		this.chooseFactory = chooseFactory;
		this.stationWindowFactory = stationWindowFactory;
		this.votingRecordFactory = votingRecordFactory;
	}
	
	@Override
	public IVotingStation createInstance(List<String> passwords) {
		return new VotingStation(passwords, chooseFactory, stationWindowFactory, votingRecordFactory);
	}

}
