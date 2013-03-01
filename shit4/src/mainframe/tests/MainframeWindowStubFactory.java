package mainframe.tests;

import mainframe.factories.IMainframeWindowFactory;
import mainframe.gui.IMainframeWindow;
import mainframe.logic.IMainframe;

public class MainframeWindowStubFactory implements IMainframeWindowFactory {

	@Override
	public IMainframeWindow createInstance(IMainframe callerStation) {
		return new MainframeWindowStub();
	}

}
