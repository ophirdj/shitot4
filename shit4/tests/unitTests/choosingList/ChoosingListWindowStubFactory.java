package unitTests.choosingList;

import java.util.Queue;

import unitTests.choosingList.ChoosingListWindowStub.CloseWindowComponent;
import unitTests.choosingList.ChoosingListWindowStub.ConfirmationWithPartyComponent;
import unitTests.choosingList.ChoosingListWindowStub.PrintConfirmationMessageComponent;
import unitTests.choosingList.ChoosingListWindowStub.getChoiceComponent;
import unitTests.choosingList.ChoosingListWindowStub.getPartyComponent;
import unitTests.choosingList.ChoosingListWindowStub.switchOffComponent;
import unitTests.choosingList.ChoosingListWindowStub.switchOnComponent;

import global.gui.StationPanel;
import choosingList.factories.IChoosingWindowFactory;
import choosingList.gui.IChoosingWindow;

public class ChoosingListWindowStubFactory implements IChoosingWindowFactory{

	private ChoosingListTestEnvironment testEnvironment;
	private Queue<PrintConfirmationMessageComponent> printConfirmationMessageQueue;
	private Queue<ConfirmationWithPartyComponent> ConfirmationWithPartyQueue;
	private Queue<CloseWindowComponent> closeWindowQueue;
	private Queue<switchOnComponent> switchOnQueue;
	private Queue<switchOffComponent> switchOffQueue;
	private Queue<getPartyComponent> getPartyQueue;
	private Queue<getChoiceComponent> receiveChoiceQueue;
	
	public ChoosingListWindowStubFactory(ChoosingListTestEnvironment testEnvironment,
			Queue<PrintConfirmationMessageComponent> printConfirmationMessageQueue,
			Queue<ConfirmationWithPartyComponent> ConfirmationWithPartyQueue,
			Queue<CloseWindowComponent> closeWindowQueue,
			Queue<switchOnComponent> switchOnQueue,
			Queue<switchOffComponent> switchOffQueue,
			Queue<getPartyComponent> getPartyQueue,
			Queue<getChoiceComponent> receiveChoiceQueue) {
		this.testEnvironment = testEnvironment;
		this.printConfirmationMessageQueue = printConfirmationMessageQueue;
		this.ConfirmationWithPartyQueue = ConfirmationWithPartyQueue;
		this.closeWindowQueue = closeWindowQueue;
		this.switchOnQueue = switchOnQueue;
		this.switchOffQueue = switchOffQueue;
		this.getPartyQueue = getPartyQueue;
		this.receiveChoiceQueue = receiveChoiceQueue;
	}
	
	@Override
	public IChoosingWindow createInstance(StationPanel stationPanel) {
		return new ChoosingListWindowStub(testEnvironment,
				printConfirmationMessageQueue,
				ConfirmationWithPartyQueue,
				closeWindowQueue,
				switchOnQueue,
				switchOffQueue,
				getPartyQueue,
				receiveChoiceQueue); 
	}

}
