package integrationTests.mainframeAndVotingStations;

import mainframe.factories.IMainframeWindowFactory;
import mainframe.gui.IMainframeWindow;
import mainframe.logic.IMainframe;

public class MainframeWindowStubFactory implements IMainframeWindowFactory {
	
	
	private IntegrationTest test;

	public MainframeWindowStubFactory(IntegrationTest test) {
		this.test = test;
	}

	@Override
	public IMainframeWindow createInstance(IMainframe callerStation) {
		MainframeWindowStub stub = new MainframeWindowStub(callerStation);
		test.setMainframeWindowStub(stub);
		return stub;
	}

}
