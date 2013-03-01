package mainframe.tests;

import mainframe.communication.IStationsController;
import mainframe.communication.IStationsControllerFactory;
import mainframe.logic.IMainframe;

public class StationsControllerStubFactory implements
		IStationsControllerFactory {

	@Override
	public IStationsController createInstance(IMainframe mainframe) {
		return new StationsControllerStub();
	}

}
