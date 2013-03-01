package mainframe.tests;

import mainframe.communication.IStationsController;
import mainframe.communication.IStationsControllerFactory;
import mainframe.logic.IMainframe;

public class StationsControllerStubFactory implements
		IStationsControllerFactory {
	
	private StationsControllerStub stationsControllerStub = new StationsControllerStub();

	@Override
	public IStationsController createInstance(IMainframe mainframe) {
		return stationsControllerStub;
	}
	
	public StationsControllerStub getStationsController(){
		return this.stationsControllerStub;
	}
	
	public void reset(){
		this.stationsControllerStub = new StationsControllerStub();
	}

}
