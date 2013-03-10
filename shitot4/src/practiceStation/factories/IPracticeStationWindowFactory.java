package practiceStation.factories;

import practiceStation.gui.IPracticeStationWindow;
import practiceStation.logic.IPracticeStation;


public interface IPracticeStationWindowFactory {
	/**
	 * 
	 * @param caller
	 *             caller station
	 * @return a new object that implement IPracticeStationWindow
	 */
	IPracticeStationWindow createInstance(IPracticeStation caller);
}
