package choosingList.factories;

import global.gui.StationPanel;
import choosingList.gui.IChoosingWindow;

public interface IChoosingWindowFactory {
	/**
	 * 
	 * @return a new object that implements IChoosingWindow
	 */
	IChoosingWindow createInstance(StationPanel stationPanel);
}
