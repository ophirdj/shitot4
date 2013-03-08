package integrationTests.mainframeVotingStations;

import mainframe.communication.IStationsController;
import mainframe.communication.IStationsControllerFactory;
import mainframe.logic.IMainframe;

/**
 * this stub factory returns the same object - and we can make it return a brand new
 * object by invoking restart()
 * @author Emil
 *
 */
public class StationsControllerStubFactory implements
		IStationsControllerFactory {
	
	/**
	 * where we hold the stationsControllerStub that this factory repeatedly returns 
	 */
	private StationsControllerStub stationsControllerStub = new StationsControllerStub();

	@Override
	public IStationsController createInstance(IMainframe mainframe) {
		return stationsControllerStub;
	}
	
	/**
	 * 
	 * @return get the created StationsControllerStub
	 */
	public StationsControllerStub getStationsController(){
		return this.stationsControllerStub;
	}
	
	/**
	 * makes this factory to return a brand new BackupStub object instead of the same one
	 */
	public void reset(){
		this.stationsControllerStub = new StationsControllerStub();
	}

}
