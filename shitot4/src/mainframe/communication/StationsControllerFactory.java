package mainframe.communication;

import java.util.ArrayList;
import java.util.List;

import votingStation.factories.IVotingStationFactory;


import mainframe.logic.IMainframe;

/**
 * 
 */
public class StationsControllerFactory implements IStationsControllerFactory {

	private int NUM_STATIONS;
	private IVotingStationFactory votingStationFactory;

	public StationsControllerFactory(IVotingStationFactory votingStationFactory, int numOfStation) {
		this.votingStationFactory = votingStationFactory;
		NUM_STATIONS = numOfStation;
	}
	
	
	@Override
	public IStationsController createInstance(IMainframe mainframe) {
		
		List<String> passwords = getPasswords();
		
		return new StationsController(mainframe, votingStationFactory, passwords,NUM_STATIONS);
	}
	
	
	
	/**
	 * Get passwords for the station
	 * @return list of passwords.
	 */
	private List<String> getPasswords(){
		//TODO: set some passwords here. How do we configure the passwords? Should we read from file???
		List<String> passwords = new ArrayList<String>();
		passwords.add("pass");
		return passwords;
	}
}
