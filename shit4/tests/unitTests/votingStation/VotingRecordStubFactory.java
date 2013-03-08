package unitTests.votingStation;

import votingStation.factories.IVotingRecordFactory;
import votingStation.model.IVotingRecord;
import votingStation.model.VotingRecord;

public class VotingRecordStubFactory implements IVotingRecordFactory {
	
	private final long maxVotingTimeSeconds;
	
	public VotingRecordStubFactory(long maxVotingTimeSeconds) {
		this.maxVotingTimeSeconds = maxVotingTimeSeconds;
	}

	@Override
	public IVotingRecord createInstance(int id) {
		return new VotingRecord(id, maxVotingTimeSeconds);
	}

}
