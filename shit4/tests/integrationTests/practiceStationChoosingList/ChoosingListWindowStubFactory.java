package integrationTests.practiceStationChoosingList;

import global.gui.StationPanel;
import choosingList.factories.IChoosingWindowFactory;
import choosingList.gui.IChoosingWindow;

public class ChoosingListWindowStubFactory implements IChoosingWindowFactory{

	private long timeWait;
	private IntegrationTest test;

	public ChoosingListWindowStubFactory(IntegrationTest test ,long timeWait) {
		this.test = test;
		this.timeWait = timeWait;
	}
	
	@Override
	public IChoosingWindow createInstance(StationPanel stationPanel) {
		ChoosingListWindowStub stub = new ChoosingListWindowStub(timeWait);
		test.setChoosingWindowStub(stub);
		return stub;
	}

}
