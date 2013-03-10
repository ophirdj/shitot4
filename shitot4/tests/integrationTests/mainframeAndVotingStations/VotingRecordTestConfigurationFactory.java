package integrationTests.mainframeAndVotingStations;

import votingStation.factories.IVotingRecordFactory;
import votingStation.model.IVotingRecord;
import votingStation.model.VotingRecord;

public class VotingRecordTestConfigurationFactory implements IVotingRecordFactory {
	
	private final long maxVotingTimeSeconds;
	
	public VotingRecordTestConfigurationFactory(long maxVotingTimeSeconds) {
		this.maxVotingTimeSeconds = maxVotingTimeSeconds;
	}

	@Override
	public IVotingRecord createInstance(int id) {
		return new VotingRecord(id, maxVotingTimeSeconds);
	}

}
