package votingStation.factories;

import votingStation.model.IVotingRecord;
import votingStation.model.VotingRecord;

public class VotingRecordFactory implements IVotingRecordFactory {
	
	private final long maxVotingTimeSeconds = 120L;

	@Override
	public IVotingRecord createInstance(int id) {
		return new VotingRecord(id, maxVotingTimeSeconds);
	}

}
