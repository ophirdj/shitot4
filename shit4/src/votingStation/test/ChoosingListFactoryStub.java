package votingStation.test;

import global.gui.StationPanel;
import choosingList.factories.IChoosingListFactory;
import choosingList.logic.IChoosingList;

import partiesList.model.IPartiesList;

public class ChoosingListFactoryStub implements IChoosingListFactory {

	@Override
	public IChoosingList createInstance(IPartiesList parties,
			StationPanel stationPanel) {
		return new ChoosingListStub();
	}

}
