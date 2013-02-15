package factories;

import javax.swing.JPanel;

import GUI.Main_Window;

import choosingList.IChoosingList;
import partiesList.IPartiesList;

public interface IChoosingListFactory {
	/**
	 * 
	 * @param parties
	 *            : the parties in the station
	 * @param stationPanel
	 *            : the main panel of station (null in order to not show
	 *            graphic)
	 * @param windowFactory
	 *            : abstract factory that create a window
	 * @param mainWindow
	 * 			  : main window
	 * @return a new object that implements IChoosingList
	 */
	IChoosingList createInstance(IPartiesList parties, JPanel stationPanel,
			IChoosingWindowFactory windowFactory, Main_Window mainWindow);
}
