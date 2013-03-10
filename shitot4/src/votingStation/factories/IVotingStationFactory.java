package votingStation.factories;

import java.util.List;

import votingStation.logic.IVotingStation;

public interface IVotingStationFactory {
	/**
	 * Create new object of IVotingStation
	 * @param passwords
	 *            : list of passwords for test voting
	 * @return a new object that implement IVotingStation
	 */
	IVotingStation createInstance(List<String> passwords);
}
