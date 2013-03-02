package votingStation.factories;

import java.util.List;

import choosingList.factories.IChoosingListFactory;

import votingStation.logic.IVotingStation;
import votingStation.logic.VotingStation;

public class VotingStationFactory implements IVotingStationFactory {

	private IChoosingListFactory choseFactory;
	private IVotingStationWindowFactory stationWindowFactory;
	private IVotingRecordFactory votingRecordFactory;

	public VotingStationFactory(IChoosingListFactory choseFactory,
	IVotingStationWindowFactory stationWindowFactory, IVotingRecordFactory votingRecordFactory) {
		this.choseFactory = choseFactory;
		this.stationWindowFactory = stationWindowFactory;
		this.votingRecordFactory = votingRecordFactory;
	}
	
	@Override
	public IVotingStation createInstance(List<String> passwords) {
		return new VotingStation(passwords, choseFactory, stationWindowFactory, votingRecordFactory);
	}

}
