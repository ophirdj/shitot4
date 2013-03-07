package votingStation.factories;

import votingStation.model.IVotingRecord;

/**
 * Factory for IVotingRecord
 * Will configure how much time a voter has after
 * first vote before cannot vote anymore
 * @author Ophir De Jager
 *
 */
public interface IVotingRecordFactory {

	/**
	 * Create a new object of IVotingRecord
	 * @param id: voter ID
	 * @return
	 */
	IVotingRecord createInstance(int id);
}
