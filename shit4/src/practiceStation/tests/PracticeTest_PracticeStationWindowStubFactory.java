package practiceStation.tests;

import java.util.Queue;

import practiceStation.factories.IPracticeStationWindowFactory;
import practiceStation.gui.IPracticeStationWindow;
import practiceStation.logic.IPracticeStation;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.ConfirmationWithPartyComponent;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.PrintConfirmationMessageComponent;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.PrintInfoMessageComponent;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.PrintErrorMessageComponent;

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
