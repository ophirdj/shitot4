package votingStation.test;

import global.gui.BasicPanel;
import choosingList.factories.IChoosingListFactory;
import choosingList.logic.IChoosingList;

import partiesList.model.IPartiesList;

public class ChoosingListFactoryStub implements IChoosingListFactory {

	@Override
	public IChoosingList createInstance(IPartiesList parties,
			BasicPanel stationPanel) {
		return new ChoosingListStub();
	}

}
