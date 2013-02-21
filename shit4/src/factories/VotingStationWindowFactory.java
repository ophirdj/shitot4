package factories;

import GUI.Main_Window;
import votingStation.IVotingStation;
import votingStation.IVotingStationWindow;
import votingStation.VotingStation_window;

public class VotingStationWindowFactory implements IVotingStationWindowFactory {

	private Main_Window main_window;

	public VotingStationWindowFactory(Main_Window main_window) {
		this.main_window = main_window;
	}
	
	@Override
	public IVotingStationWindow createInstance(String name,
			IVotingStation caller) {
		return new VotingStation_window(name, caller,main_window);
	}

}
