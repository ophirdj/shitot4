package choosingList.factories;

import global.gui.StationPanel;
import choosingList.gui.IChoosingWindow;

/**
 * 
 * Interface for and IChoosingWindow factory
 * @author Ziv Ronen
 */
public interface IChoosingWindowFactory {
	/**
	 * 
	 * @return a new object that implements IChoosingWindow
	 */
	IChoosingWindow createInstance(StationPanel stationPanel);
}
