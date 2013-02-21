package votingStation.test;

import global.gui.BasicPanel;
import choosingList.factories.IChoosingWindowFactory;
import choosingList.gui.IChoosingWindow;


public class ChoosingWindowFactoryStub implements IChoosingWindowFactory {

	@Override
	public IChoosingWindow createInstance(BasicPanel stationPanel) {
		return new ChoosingWindowStub();
	}

}
