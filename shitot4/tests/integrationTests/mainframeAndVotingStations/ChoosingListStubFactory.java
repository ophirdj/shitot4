package integrationTests.mainframeAndVotingStations;

import global.gui.StationPanel;
import partiesList.model.IPartiesList;
import choosingList.factories.IChoosingListFactory;
import choosingList.logic.IChoosingList;

public class ChoosingListStubFactory implements IChoosingListFactory{

	private IntegrationTest test;

	public ChoosingListStubFactory(IntegrationTest integrationTest) {
		this.test = integrationTest;
	}

	@Override
	public IChoosingList createInstance(IPartiesList parties,
			StationPanel stationPanel) {
		return new ChoosingListStub(test, parties, stationPanel);
	}

}
