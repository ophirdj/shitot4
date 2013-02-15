package unitTests;

import javax.swing.JPanel;

import GUI.Main_Window;

import partiesList.IPartiesList;
import choosingList.IChoosingList;
import factories.IChoosingListFactory;
import factories.IChoosingWindowFactory;

public class ChoosingListFactoryStub implements IChoosingListFactory {

	@Override
	public IChoosingList createInstance(IPartiesList parties,
			JPanel stationPanel, IChoosingWindowFactory windowFactory, Main_Window mainWindow) {
		return new ChoosingListStub();
	}

}
