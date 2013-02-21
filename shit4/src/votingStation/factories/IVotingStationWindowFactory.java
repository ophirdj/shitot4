package votingStation.factories;

import votingStation.gui.IVotingStationWindow;
import votingStation.logic.IVotingStation;

public interface IVotingStationWindowFactory {
	/**
	 * 
	 * @param caller
	 *            : caller station
	 * @return a new object that implement IVotingStationWindow
	 */
	IVotingStationWindow createInstance(IVotingStation caller);
}
