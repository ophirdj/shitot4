package votingStation.factories;

import votingStation.model.IVotingRecord;

public interface IVotingRecordFactory {

	IVotingRecord createInstance(int id);
}
