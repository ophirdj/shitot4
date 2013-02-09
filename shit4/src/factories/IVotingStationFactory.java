package factories;

import java.util.List;

import votingStation.IVotingStation;

public interface IVotingStationFactory {
	/**
	 * 
	 * @param passwords
	 *            : list of passwords for test voting
	 * @param name
	 *            : station name (for visual)
	 * @param choseFactory
	 *            : factory for creating IchoosingList
	 * @param choseWindowFactory
	 *            : factory for creating IchoosingWindow
	 * @param stationWindowFactory
	 *            : factory for creating IVotingStationWindow
	 * @return a new object that implement IVotingStation
	 */
	IVotingStation createInstance(List<String> passwords, String name,
			IChoosingListFactory choseFactory,
			IChoosingWindowFactory choseWindowFactory,
			IVotingStationWindowFactory stationWindowFactory);
}
