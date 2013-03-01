package global.tests;

import practiceStation.factories.IPracticeStationWindowFactory;
import practiceStation.gui.IPracticeStationWindow;
import practiceStation.logic.IPracticeStation;

public class PracticeStationWindowStubFactory implements
		IPracticeStationWindowFactory {
	
	private AcceptanceTest test;

	public PracticeStationWindowStubFactory(AcceptanceTest test) {
		this.test = test;
	}

	@Override
	public IPracticeStationWindow createInstance(IPracticeStation caller) {
		PracticeStationWindowStub stub = new PracticeStationWindowStub(caller);
		test.addPracticeStationWindowStub(stub);
		return stub;
	}

}
