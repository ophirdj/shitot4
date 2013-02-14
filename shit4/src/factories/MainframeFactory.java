package factories;

import mainframe.IMainframe;
import mainframe.Mainframe;

public class MainframeFactory implements IMainframeFactory {

	@Override
	public IMainframe createInstance(IBackupFactory backupFactory,
			IPartiesListFactory partiesListFactory, IPartyFactory partyFactory,
			IVotersListFactory votersListFactory,
			IVoterDataFactory voterDataFactory,
			IChoosingListFactory choosingListFactory,
			IChoosingWindowFactory choosingWindowFactory,
			IVotingStationFactory votingStationFactory,
			IVotingStationWindowFactory votingStationWindowFactory,
			IMainframeWindowFactory mainframeWindowFactory,
			IReadSuppliedXMLFactory readSuppliedXMLFactory,
			IStationsControllerFactory stationsControllerFactory) {
		return new Mainframe(backupFactory, partiesListFactory, partyFactory,
				votersListFactory, voterDataFactory, choosingListFactory,
				choosingWindowFactory, votingStationFactory,
				votingStationWindowFactory, mainframeWindowFactory,
				readSuppliedXMLFactory, stationsControllerFactory);
	}
}
