package choosingList.tests;

import java.util.Queue;

import global.gui.StationPanel;
import choosingList.factories.IChoosingWindowFactory;
import choosingList.gui.IChoosingWindow;
import choosingList.tests.ChoosingListWindowStub.CloseWindowComponent;
import choosingList.tests.ChoosingListWindowStub.ConfirmationWithPartyComponent;
import choosingList.tests.ChoosingListWindowStub.PrintConfirmationMessageComponent;
import choosingList.tests.ChoosingListWindowStub.getChoiceComponent;
import choosingList.tests.ChoosingListWindowStub.getPartyComponent;
import choosingList.tests.ChoosingListWindowStub.switchOffComponent;
import choosingList.tests.ChoosingListWindowStub.switchOnComponent;

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
