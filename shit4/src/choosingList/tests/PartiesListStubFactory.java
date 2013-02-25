package choosingList.tests;

import java.util.Queue;

import choosingList.tests.PartiesListStub.sublistComponent;
import choosingList.tests.PartiesListStub.whiteNotePartyComponent;
import partiesList.factories.IPartiesListFactory;
import partiesList.model.IPartiesList;

public class PartiesListStubFactory implements IPartiesListFactory{

	
	private ChoosingListTestEnvironment testEnvironment;
	private int partiesAmount;
	private Queue<sublistComponent> sublistQueue;
	private Queue<whiteNotePartyComponent> whiteNoteQueue;

	public PartiesListStubFactory(ChoosingListTestEnvironment testEnvironment,int partiesAmount,
			Queue<sublistComponent> sublistQueue,
			Queue<whiteNotePartyComponent> whiteNoteQueue) {
		this.testEnvironment = testEnvironment;
		this.partiesAmount = partiesAmount;
		this.sublistQueue = sublistQueue;
		this.whiteNoteQueue = whiteNoteQueue;
	}
	
	@Override
	public IPartiesList createInstance() {
		return new PartiesListStub(testEnvironment,partiesAmount,sublistQueue,whiteNoteQueue);
	}
	

}
