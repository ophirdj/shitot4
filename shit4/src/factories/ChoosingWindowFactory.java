package factories;

import javax.swing.JPanel;

import GUI.Main_Window;

import choosingList.ChoosingList_window;
import choosingList.IChoosingWindow;

public class ChoosingWindowFactory implements IChoosingWindowFactory {

	@Override
	public IChoosingWindow createInstance(JPanel stationPanel, Main_Window mainWindow) {
		return new ChoosingList_window(stationPanel, mainWindow);
	}

}
