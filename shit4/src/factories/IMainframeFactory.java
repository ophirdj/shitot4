package factories;

import GUI.Main_Window;
import mainframe.IMainframe;

public interface IMainframeFactory {

	IMainframe createInstance(IBackupFactory backupFactory,
			IPartiesListFactory partiesListFactory, IPartyFactory partyFactory,
			IVotersListFactory votersListFactory,
			IVoterDataFactory voterDataFactory,
			IChoosingListFactory choosingListFactory,
			IChoosingWindowFactory choosingWindowFactory,
			IVotingStationFactory votingStationFactory,
			IVotingStationWindowFactory votingStationWindowFactory,
			IMainframeWindowFactory mainframeWindowFactory,
			IReadSuppliedXMLFactory readSuppliedXMLFactory,
			IStationsControllerFactory stationsControllerFactory,
			Main_Window mainWindow);
}
