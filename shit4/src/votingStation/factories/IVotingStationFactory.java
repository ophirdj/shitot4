package votingStation.factories;

import java.util.List;

import votingStation.logic.IVotingStation;

public interface IVotingStationFactory {
	/**
	 * 
	 * @param passwords
	 *            : list of passwords for test voting
	 * @param name
	 *            : station name (for visual)
	 * @return a new object that implement IVotingStation
	 */
	IVotingStation createInstance(List<String> passwords, String name);
}
