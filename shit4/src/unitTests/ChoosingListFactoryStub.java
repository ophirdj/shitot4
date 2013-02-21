package unitTests;

import GUI.BasicPanel;

import partiesList.IPartiesList;
import choosingList.IChoosingList;
import factories.IChoosingListFactory;

public class ChoosingListFactoryStub implements IChoosingListFactory {

	@Override
	public IChoosingList createInstance(IPartiesList parties,
			BasicPanel stationPanel) {
		return new ChoosingListStub();
	}

}
