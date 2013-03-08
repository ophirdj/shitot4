package unitTests.practiceStation;

import java.util.Queue;

import practiceStation.factories.IPracticeStationWindowFactory;
import practiceStation.gui.IPracticeStationWindow;
import practiceStation.logic.IPracticeStation;
import unitTests.practiceStation.PracticeTest_PracticeStationWindowStub.ConfirmationWithPartyComponent;
import unitTests.practiceStation.PracticeTest_PracticeStationWindowStub.PrintConfirmationMessageComponent;
import unitTests.practiceStation.PracticeTest_PracticeStationWindowStub.PrintErrorMessageComponent;
import unitTests.practiceStation.PracticeTest_PracticeStationWindowStub.PrintInfoMessageComponent;

public class PracticeTest_PracticeStationWindowStubFactory implements IPracticeStationWindowFactory{

	private PracticeStationTestEnvironment testEnvironment;
	private Queue<PrintErrorMessageComponent> printErrorMessageQueue;
	private Queue<PrintConfirmationMessageComponent> printConfirmationMessageQueue;
	private Queue<ConfirmationWithPartyComponent> ConfirmationWithPartyQueue;
	private Queue<PrintInfoMessageComponent> printInfoMessageQueue;

	public PracticeTest_PracticeStationWindowStubFactory(
			PracticeStationTestEnvironment testEnvironment,
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
		return new PracticeTest_PracticeStationWindowStub(testEnvironment,
				printErrorMessageQueue,
				printConfirmationMessageQueue,
				ConfirmationWithPartyQueue,
				printInfoMessageQueue);
	}

}
