package practiceStation.tests;

import java.util.Queue;

import global.gui.StationPanel;
import partiesList.model.IPartiesList;
import practiceStation.tests.PracticeTest_ChoosingListStub.ChooseListComponent;
import practiceStation.tests.PracticeTest_ChoosingListStub.ChoosingListRetireComponent;
import choosingList.factories.IChoosingListFactory;
import choosingList.logic.IChoosingList;

public class PracticeTest_choosingListStubFactory implements
		IChoosingListFactory {

	private PracticeStationTestEnvironment testEnvironment;
	private Queue<ChooseListComponent> chooseListQueue;
	private Queue<ChoosingListRetireComponent> retireQueue;
	
	public PracticeTest_choosingListStubFactory(
			PracticeStationTestEnvironment testEnvironment,
			Queue<ChooseListComponent> chooseListQueue,
			Queue<ChoosingListRetireComponent> retireQueue){
		this.testEnvironment = testEnvironment;
		this.chooseListQueue = chooseListQueue;
		this.retireQueue = retireQueue;
		
	}
	
	@Override
	public IChoosingList createInstance(IPartiesList parties,
			StationPanel stationPanel) {
		return new PracticeTest_ChoosingListStub(testEnvironment,chooseListQueue,retireQueue);
	}

}
