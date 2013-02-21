package choosingList.factories;


import global.gui.StationPanel;
import choosingList.logic.IChoosingList;

import partiesList.model.IPartiesList;

public interface IChoosingListFactory {
	/**
	 * 
	 * @param parties
	 *            : the parties in the station
	 * @param stationPanel
	 *            : the main panel of station (null in order to not show
	 *            graphic)
	 * @return a new object that implements IChoosingList
	 */
	IChoosingList createInstance(IPartiesList parties, StationPanel stationPanel);
}
