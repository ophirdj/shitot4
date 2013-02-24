package mainframe.communication;

import java.util.ArrayList;
import java.util.List;

import votingStation.factories.IVotingStationFactory;


import mainframe.logic.IMainframe;

public class StationsControllerFactory implements IStationsControllerFactory {

	private static final int NUM_STATIONS = 2;
	private IVotingStationFactory votingStationFactory;

	public StationsControllerFactory(IVotingStationFactory votingStationFactory) {
		this.votingStationFactory = votingStationFactory;
	}
	
	
	@Override
	public IStationsController createInstance(IMainframe mainframe) {
		
		List<String> passwords = getPasswords();
		
		return new StationsController(mainframe, votingStationFactory, passwords,NUM_STATIONS);
	}
	
	
	private List<String> getPasswords(){
		//TODO: set some passwords here
		List<String> passwords = new ArrayList<String>();
		passwords.add("pass");
		return passwords;
	}
}
