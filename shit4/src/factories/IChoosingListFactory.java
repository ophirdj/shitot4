package factories;

import javax.swing.JPanel;

import choosingList.IChoosingList;
import partiesList.IPartiesList;

public interface IChoosingListFactory {
	/**
	 * 
	 * @param parties: the parties in the station
	 * @param stationPanel: the main panel of station (null in order to not show graphic)
	 * @param windowFactory: abstract factory that create a window
	 * @return a new object that implements IChoosingList
	 */
	IChoosingList createInstance(IPartiesList parties, JPanel stationPanel, IChoosingWindowFactory windowFactory);
}
