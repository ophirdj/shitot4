package choosingList.factories;

import global.gui.BasicPanel;
import choosingList.gui.IChoosingWindow;

public interface IChoosingWindowFactory {
	/**
	 * 
	 * @return a new object that implements IChoosingWindow
	 */
	IChoosingWindow createInstance(BasicPanel stationPanel);
}
