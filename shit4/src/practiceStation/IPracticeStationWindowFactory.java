package practiceStation;

import GUI.Main_Window;


public interface IPracticeStationWindowFactory {
	/**
	 * 
	 * @param name
	 *            : the view name in main window
	 * @param caller
	 *            : caller station
	 * @param mainWindow
	 * 			  : main window
	 * @return a new object that implement IPracticeStationWindow
	 */
	IPracticeStationWindow createInstance(String name, IPracticeStation caller, Main_Window mainWindow);
}
