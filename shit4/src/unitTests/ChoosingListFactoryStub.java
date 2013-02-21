package unitTests;

import javax.swing.JPanel;

import partiesList.IPartiesList;
import choosingList.IChoosingList;
import factories.IChoosingListFactory;

public class ChoosingListFactoryStub implements IChoosingListFactory {

	@Override
	public IChoosingList createInstance(IPartiesList parties,
			JPanel stationPanel) {
		return new ChoosingListStub();
	}

}
