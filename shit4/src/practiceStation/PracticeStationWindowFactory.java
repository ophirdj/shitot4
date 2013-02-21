package practiceStation;

import GUI.Main_Window;

public class PracticeStationWindowFactory implements IPracticeStationWindowFactory{
	private Main_Window main_window;

	public PracticeStationWindowFactory(Main_Window main_window) {
		this.main_window = main_window;
	}
	
	@Override
	public IPracticeStationWindow createInstance(String name,
			IPracticeStation caller) {
		return new PracticeStationWindow(name,caller,main_window);
	}
}
