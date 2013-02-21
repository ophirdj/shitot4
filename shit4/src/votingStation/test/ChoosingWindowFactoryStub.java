package votingStation.test;

import global.gui.StationPanel;
import choosingList.factories.IChoosingWindowFactory;
import choosingList.gui.IChoosingWindow;


public class ChoosingWindowFactoryStub implements IChoosingWindowFactory {

	@Override
	public IChoosingWindow createInstance(StationPanel stationPanel) {
		return new ChoosingWindowStub();
	}

}
