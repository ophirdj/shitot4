package integrationTests.practiceStationChoosingList;

import practiceStation.factories.IPracticeStationWindowFactory;
import practiceStation.gui.IPracticeStationWindow;
import practiceStation.logic.IPracticeStation;

public class PracticeWindowStubFactory implements IPracticeStationWindowFactory{

	private IntegrationTest test;

	public PracticeWindowStubFactory(IntegrationTest test) {
		this.test = test;
	}
	
	@Override
	public IPracticeStationWindow createInstance(IPracticeStation caller) {
		PracticeWindowStub stub = new PracticeWindowStub();
		test.setPracticeWindowStub(stub);
		return stub;
	}

}
