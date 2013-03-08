package unitTests.practiceStation;

import java.util.Queue;

import global.gui.StationPanel;
import partiesList.model.IPartiesList;
import unitTests.practiceStation.ChoosingListStub.ChooseListComponent;
import unitTests.practiceStation.ChoosingListStub.ChoosingListRetireComponent;
import choosingList.factories.IChoosingListFactory;
import choosingList.logic.IChoosingList;

public class ChoosingListStubFactory implements
		IChoosingListFactory {

	private TestEnvironment testEnvironment;
	private Queue<ChooseListComponent> chooseListQueue;
	private Queue<ChoosingListRetireComponent> retireQueue;
	
	public ChoosingListStubFactory(
			TestEnvironment testEnvironment,
			Queue<ChooseListComponent> chooseListQueue,
			Queue<ChoosingListRetireComponent> retireQueue){
		this.testEnvironment = testEnvironment;
		this.chooseListQueue = chooseListQueue;
		this.retireQueue = retireQueue;
		
	}
	
	@Override
	public IChoosingList createInstance(IPartiesList parties,
			StationPanel stationPanel) {
		return new ChoosingListStub(testEnvironment,chooseListQueue,retireQueue);
	}

}
