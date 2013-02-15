package factories;

import GUI.Main_Window;
import votingStation.IVotingStation;
import votingStation.IVotingStationWindow;

public interface IVotingStationWindowFactory {
	/**
	 * 
	 * @param name
	 *            : the view name in main window
	 * @param caller
	 *            : caller station
	 * @param mainWindow
	 * 			  : main window
	 * @return a new object that implement IVotingStationWindow
	 */
	IVotingStationWindow createInstance(String name, IVotingStation caller, Main_Window mainWindow);
}
