package unitTests;

import javax.swing.JPanel;

import choosingList.IChoosingWindow;
import factories.IChoosingWindowFactory;

public class ChoosingWindowFactoryStub implements IChoosingWindowFactory {

	@Override
	public IChoosingWindow createInstance(JPanel stationPanel) {
		return new ChoosingWindowStub();
	}

}
