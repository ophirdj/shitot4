package factories;

import javax.swing.JPanel;

import choosingList.ChoosingList_window;
import choosingList.IChoosingWindow;

public class ChoosingWindowFactory implements IChoosingWindowFactory {

	@Override
	public IChoosingWindow createInstance(JPanel stationPanel) {
		return new ChoosingList_window(stationPanel);
	}

}
