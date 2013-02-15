package practiceStation;

import GUI.Main_Window;

public class PracticeStationWindowFactory implements IPracticeStationWindowFactory{
	@Override
	public IPracticeStationWindow createInstance(String name,
			IPracticeStation caller, Main_Window mainWindow) {
		return new PracticeStationWindow(name,caller, mainWindow);
	}
}
