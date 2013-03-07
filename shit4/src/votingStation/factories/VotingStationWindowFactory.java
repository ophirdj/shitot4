package votingStation.factories;

import global.gui.Main_Window;
import votingStation.gui.IVotingStationWindow;
import votingStation.gui.VotingStationWindow;
import votingStation.logic.IVotingStation;

public class VotingStationWindowFactory implements IVotingStationWindowFactory {

	private Main_Window main_window;
	private int id = 1;
	
	public VotingStationWindowFactory(Main_Window main_window) {
		this.main_window = main_window;
	}
	
	@Override
	public IVotingStationWindow createInstance(IVotingStation caller) {
		int stationID = id;
		id++;
		return new VotingStationWindow(stationID, caller,main_window);
	}

}
