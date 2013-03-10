package acceptanceTest;

import global.gui.StationPanel;
import choosingList.factories.IChoosingWindowFactory;
import choosingList.gui.IChoosingWindow;

public class ChoosingWindowStubFactory implements IChoosingWindowFactory {

	private AcceptanceTest test;

	public ChoosingWindowStubFactory(AcceptanceTest test) {
		this.test = test;
	}
	
	@Override
	public IChoosingWindow createInstance(StationPanel stationPanel) {
		ChoosingWindowStub stub = new ChoosingWindowStub();
		test.addChoosingWindowStub(stub,stationPanel);
		return stub;
	}

}
