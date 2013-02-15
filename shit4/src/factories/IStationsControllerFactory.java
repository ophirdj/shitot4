package factories;

import GUI.Main_Window;
import mainframe.IMainframe;
import communication.IStationsController;

public interface IStationsControllerFactory {

	IStationsController createInstance(IMainframe mainframe,
			IVotingStationFactory votingStationFactory,
			IChoosingListFactory choosingListFactory,
			IChoosingWindowFactory choosingWindowFactory,
			IVotingStationWindowFactory votingStationWindowFactory,
			Main_Window mainWindow);
}
