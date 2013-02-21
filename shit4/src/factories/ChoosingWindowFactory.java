package factories;

import javax.swing.JPanel;

import GUI.Main_Window;

import choosingList.ChoosingList_window;
import choosingList.IChoosingWindow;

public class ChoosingWindowFactory implements IChoosingWindowFactory {

	private Main_Window main_window;
	
	public ChoosingWindowFactory(Main_Window main_window) {
		this.main_window = main_window;
	}
	
	@Override
	public IChoosingWindow createInstance(JPanel stationPanel) {
		return new ChoosingList_window(stationPanel,main_window);
	}

}
