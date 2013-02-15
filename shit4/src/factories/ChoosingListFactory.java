package factories;

import javax.swing.JPanel;

import GUI.Main_Window;

import choosingList.ChoosingList;
import choosingList.IChoosingList;

import partiesList.IPartiesList;

public class ChoosingListFactory implements IChoosingListFactory {

	@Override
	public IChoosingList createInstance(IPartiesList parties,
			JPanel stationPanel, IChoosingWindowFactory windowFactory, Main_Window mainWindow) {
		return new ChoosingList(parties, stationPanel, windowFactory, mainWindow);
	}

}
