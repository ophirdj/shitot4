package acceptanceTest;

import mainframe.factories.IMainframeWindowFactory;
import mainframe.gui.IMainframeWindow;
import mainframe.logic.IMainframe;

public class MainframeWindowStubFactory implements IMainframeWindowFactory {
	
	
	private AcceptanceTest test;

	public MainframeWindowStubFactory(AcceptanceTest test) {
		this.test = test;
	}

	@Override
	public IMainframeWindow createInstance(IMainframe callerStation) {
		MainframeWindowStub stub = new MainframeWindowStub(callerStation);
		test.setMainframeWindowStub(stub);
		return stub;
	}

}
