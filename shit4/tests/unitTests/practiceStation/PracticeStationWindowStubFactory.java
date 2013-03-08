package unitTests.practiceStation;

import java.util.Queue;

import practiceStation.factories.IPracticeStationWindowFactory;
import practiceStation.gui.IPracticeStationWindow;
import practiceStation.logic.IPracticeStation;
import unitTests.practiceStation.PracticeStationWindowStub.ConfirmationWithPartyComponent;
import unitTests.practiceStation.PracticeStationWindowStub.PrintConfirmationMessageComponent;
import unitTests.practiceStation.PracticeStationWindowStub.PrintErrorMessageComponent;
import unitTests.practiceStation.PracticeStationWindowStub.PrintInfoMessageComponent;

public class PracticeStationWindowStubFactory implements IPracticeStationWindowFactory{

	private TestEnvironment testEnvironment;
	private Queue<PrintErrorMessageComponent> printErrorMessageQueue;
	private Queue<PrintConfirmationMessageComponent> printConfirmationMessageQueue;
	private Queue<ConfirmationWithPartyComponent> ConfirmationWithPartyQueue;
	private Queue<PrintInfoMessageComponent> printInfoMessageQueue;

	public PracticeStationWindowStubFactory(
			TestEnvironment testEnvironment,
			Queue<PrintErrorMessageComponent> printErrorMessageQueue,
			Queue<PrintConfirmationMessageComponent> printConfirmationMessageQueue,
			Queue<ConfirmationWithPartyComponent> ConfirmationWithPartyQueue,
			Queue<PrintInfoMessageComponent> printInfoMessageQueue){
		this.testEnvironment = testEnvironment;
		this.printErrorMessageQueue = printErrorMessageQueue;
		this.printConfirmationMessageQueue = printConfirmationMessageQueue;
		this.ConfirmationWithPartyQueue = ConfirmationWithPartyQueue;
		this.printInfoMessageQueue = printInfoMessageQueue;
	}
	
	@Override
	public IPracticeStationWindow createInstance(IPracticeStation caller) {
		return new PracticeStationWindowStub(testEnvironment,
				printErrorMessageQueue,
				printConfirmationMessageQueue,
				ConfirmationWithPartyQueue,
				printInfoMessageQueue);
	}

}
