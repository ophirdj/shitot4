package unitTests;

import GUI.BasicPanel;

import choosingList.IChoosingWindow;
import factories.IChoosingWindowFactory;

public class ChoosingWindowFactoryStub implements IChoosingWindowFactory {

	@Override
	public IChoosingWindow createInstance(BasicPanel stationPanel) {
		return new ChoosingWindowStub();
	}

}
