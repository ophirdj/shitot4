package practiceStation.tests;

import java.util.Queue;

import practiceStation.factories.IPracticeStationWindowFactory;
import practiceStation.gui.IPracticeStationWindow;
import practiceStation.logic.IPracticeStation;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.ConformationWithPartyComponent;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.PrintConformationMessageComponent;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.PrintInfoMessageComponent;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.PrintErrorMessageComponent;

public class PracticeTest_PracticeStationWindowStubFactory implements IPracticeStationWindowFactory{

	private PracticeStationTestEnvironment testEnvironment;
	private Queue<PrintErrorMessageComponent> printErrorMessageQueue;
	private Queue<PrintConformationMessageComponent> printConformationMessageQueue;
	private Queue<ConformationWithPartyComponent> ConformationWithPartyQueue;
	private Queue<PrintInfoMessageComponent> printInfoMessageQueue;

	public PracticeTest_PracticeStationWindowStubFactory(
			PracticeStationTestEnvironment testEnvironment,
			Queue<PrintErrorMessageComponent> printErrorMessageQueue,
			Queue<PrintConformationMessageComponent> printConformationMessageQueue,
			Queue<ConformationWithPartyComponent> ConformationWithPartyQueue,
			Queue<PrintInfoMessageComponent> printInfoMessageQueue){
		this.testEnvironment = testEnvironment;
		this.printErrorMessageQueue = printErrorMessageQueue;
		this.printConformationMessageQueue = printConformationMessageQueue;
		this.ConformationWithPartyQueue = ConformationWithPartyQueue;
		this.printInfoMessageQueue = printInfoMessageQueue;
	}
	
	@Override
	public IPracticeStationWindow createInstance(IPracticeStation caller) {
		return new PracticeTest_PracticeStationWindowStub(testEnvironment,
				printErrorMessageQueue,
				printConformationMessageQueue,
				ConformationWithPartyQueue,
				printInfoMessageQueue);
	}

}
