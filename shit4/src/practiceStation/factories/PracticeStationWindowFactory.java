package practiceStation.factories;

import global.gui.Main_Window;
import practiceStation.gui.IPracticeStationWindow;
import practiceStation.gui.PracticeStationWindow;
import practiceStation.logic.IPracticeStation;

public class PracticeStationWindowFactory implements IPracticeStationWindowFactory{
	private Main_Window main_window;
	private int counter = 1;
	
	public PracticeStationWindowFactory(Main_Window main_window) {
		this.main_window = main_window;
	}
	
	@Override
	public IPracticeStationWindow createInstance(IPracticeStation caller) {
		return new PracticeStationWindow(counter++,caller,main_window);
	}
}
