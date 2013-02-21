package choosingList.factories;

import global.gui.BasicPanel;
import choosingList.logic.ChoosingList;
import choosingList.logic.IChoosingList;


import partiesList.model.IPartiesList;

public class ChoosingListFactory implements IChoosingListFactory {

	private IChoosingWindowFactory windowFactory;
	
	public ChoosingListFactory(IChoosingWindowFactory windowFactory) {
		this.windowFactory = windowFactory;
	}

	@Override
	public IChoosingList createInstance(IPartiesList parties,
			BasicPanel stationPanel) {
		return new ChoosingList(parties, stationPanel, windowFactory);
	}

}
