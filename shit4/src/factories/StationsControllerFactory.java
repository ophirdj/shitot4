package factories;

import java.util.ArrayList;
import java.util.List;

import mainframe.IMainframe;
import communication.IStationsController;
import communication.StationsController;

public class StationsControllerFactory implements IStationsControllerFactory {

	private static final int NUM_STATIONS = 2;

	@Override
	public IStationsController createInstance(IMainframe mainframe,
			IVotingStationFactory votingStationFactory,
			IChoosingListFactory choosingListFactory,
			IChoosingWindowFactory choosingWindowFactory,
			IVotingStationWindowFactory votingStationWindowFactory) {
		
		List<String> stationsNames = getStationsNames();
		List<String> passwords = getPasswords();
		
		return new StationsController(mainframe, votingStationFactory,
				choosingListFactory, choosingWindowFactory,
				votingStationWindowFactory, stationsNames, passwords);
	}
	
	
	private List<String> getStationsNames(){
		List<String> names = new ArrayList<String>();
		for(int i = 1; i <= NUM_STATIONS; i++){
			names.add("voting station " + i);
		}
		return names;
	}
	
	
	private List<String> getPasswords(){
		//TODO: set some passwords here
		return null;
	}
}
