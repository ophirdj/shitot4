package factories;

import GUI.BasicPanel;
import choosingList.IChoosingWindow;

public interface IChoosingWindowFactory {
	/**
	 * 
	 * @return a new object that implements IChoosingWindow
	 */
	IChoosingWindow createInstance(BasicPanel stationPanel);
}
