package votingStation.tests;

import global.gui.StationPanel;
import partiesList.model.IPartiesList;
import choosingList.factories.IChoosingListFactory;
import choosingList.logic.IChoosingList;

public class choosingListStubFactory implements IChoosingListFactory {
	
	private ChoosingListStub chooseStub;
	
	public choosingListStubFactory(ChoosingListStub chooseStub) {
		this.chooseStub = chooseStub;
	}

	@Override
	public IChoosingList createInstance(IPartiesList parties,
			StationPanel stationPanel) {
		chooseStub.partiesList = parties;
		return chooseStub;
	}

}
