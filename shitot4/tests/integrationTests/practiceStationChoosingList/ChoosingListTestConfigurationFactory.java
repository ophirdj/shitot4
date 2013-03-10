package integrationTests.practiceStationChoosingList;

import global.gui.StationPanel;
import partiesList.model.IPartiesList;
import choosingList.factories.IChoosingListFactory;
import choosingList.factories.IChoosingWindowFactory;
import choosingList.logic.ChoosingList;
import choosingList.logic.IChoosingList;

public class ChoosingListTestConfigurationFactory implements IChoosingListFactory{

	private IChoosingWindowFactory choosingWindowFactory;

	public ChoosingListTestConfigurationFactory(IChoosingWindowFactory choosingWindowFactory) {
		this.choosingWindowFactory = choosingWindowFactory;
	}
	
	@Override
	public IChoosingList createInstance(IPartiesList parties,
			StationPanel stationPanel) {
		return new ChoosingList(parties,stationPanel,choosingWindowFactory);
	}

}
