package choosingList.factories;

import global.gui.Main_Window;
import global.gui.StationPanel;
import choosingList.gui.ChoosingListWindow;
import choosingList.gui.IChoosingWindow;

/**
 * A factory for creating choosing window object.
 */
public class ChoosingWindowFactory implements IChoosingWindowFactory {

	private Main_Window main_window;
	
	public ChoosingWindowFactory(Main_Window main_window) {
		this.main_window = main_window;
	}
	
	@Override
	public IChoosingWindow createInstance(StationPanel stationPanel) {
		return new ChoosingListWindow(stationPanel,main_window);
	}

}
