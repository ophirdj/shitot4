package choosingList.factories;

import global.gui.BasicPanel;
import global.gui.Main_Window;
import choosingList.gui.ChoosingList_window;
import choosingList.gui.IChoosingWindow;


public class ChoosingWindowFactory implements IChoosingWindowFactory {

	private Main_Window main_window;
	
	public ChoosingWindowFactory(Main_Window main_window) {
		this.main_window = main_window;
	}
	
	@Override
	public IChoosingWindow createInstance(BasicPanel stationPanel) {
		return new ChoosingList_window(stationPanel,main_window);
	}

}
