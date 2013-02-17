package factories;

import javax.swing.JPanel;
import choosingList.IChoosingWindow;

public interface IChoosingWindowFactory {
	/**
	 * 
	 * @return a new object that implements IChoosingWindow
	 */
	IChoosingWindow createInstance(JPanel stationPanel);
}
