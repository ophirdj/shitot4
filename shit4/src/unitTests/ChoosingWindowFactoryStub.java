package unitTests;

import javax.swing.JPanel;

import GUI.Main_Window;

import choosingList.IChoosingWindow;
import factories.IChoosingWindowFactory;

public class ChoosingWindowFactoryStub implements IChoosingWindowFactory {

	@Override
	public IChoosingWindow createInstance(JPanel stationPanel, Main_Window mainWindow) {
		return new ChoosingWindowStub();
	}

}
