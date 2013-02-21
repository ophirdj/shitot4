package factories;

import GUI.BasicPanel;

import choosingList.ChoosingList;
import choosingList.IChoosingList;

import partiesList.IPartiesList;

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
