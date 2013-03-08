package unitTests.practiceStation;

import global.dictionaries.Languages;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Assert;

import choosingList.factories.IChoosingListFactory;

import practiceStation.factories.IImagePanelFactory;
import practiceStation.factories.IPracticeStationWindowFactory;
import practiceStation.logic.IPracticeStation;
import unitTests.practiceStation.ChoosingListStub.ChooseListComponent;
import unitTests.practiceStation.ChoosingListStub.ChoosingListRetireComponent;
import unitTests.practiceStation.ImagePanelStub.ImagePanelRetireComponent;
import unitTests.practiceStation.ImagePanelStub.ShowFirstImageComponent;
import unitTests.practiceStation.PracticeStationWindowStub.ConfirmationWithPartyComponent;
import unitTests.practiceStation.PracticeStationWindowStub.PrintConfirmationMessageComponent;
import unitTests.practiceStation.PracticeStationWindowStub.PrintErrorMessageComponent;
import unitTests.practiceStation.PracticeStationWindowStub.PrintInfoMessageComponent;

public class TestEnvironment {
	
	private Queue<PracticeTestFunction> functionQueue;
	private Queue<ChooseListComponent> choosingList_chooseListQueue;
	private Queue<ChoosingListRetireComponent> choosingList_retireQueue;
	private Queue<ShowFirstImageComponent> ImagePanel_showFirstImageQueue;
	private Queue<ImagePanelRetireComponent> ImagePanel_retireQueue;
	private Queue<PrintErrorMessageComponent> PracticeWindow_printErrorMessageQueue;
	private Queue<PrintConfirmationMessageComponent> PracticeWindow_printConfirmationMessageQueue;
	private Queue<ConfirmationWithPartyComponent> PracticeWindow_ConfirmationWithPartyQueue;
	private Queue<PrintInfoMessageComponent> PracticeWindow_printInfoMessageQueue;
	
	private Queue<PracticeTestDriverCalls> driverCalls;
	private String testName;
	private List<String> expectedTestLog = new LinkedList<String>();
	private int instructionCount = 1;
	
	public TestEnvironment(String testName) {
		this.testName = testName;
		this.functionQueue = new LinkedBlockingQueue<PracticeTestFunction>();
		this.choosingList_chooseListQueue = new LinkedBlockingQueue<ChooseListComponent>();
		this.choosingList_retireQueue = new LinkedBlockingQueue<ChoosingListRetireComponent>();
		this.choosingList_retireQueue = new LinkedBlockingQueue<ChoosingListRetireComponent>();
		this.ImagePanel_showFirstImageQueue = new LinkedBlockingQueue<ShowFirstImageComponent>();
		this.ImagePanel_retireQueue = new LinkedBlockingQueue<ImagePanelRetireComponent>();
		this.PracticeWindow_printErrorMessageQueue = new LinkedBlockingQueue<PrintErrorMessageComponent>();
		this.PracticeWindow_printConfirmationMessageQueue = new LinkedBlockingQueue<PrintConfirmationMessageComponent>();
		this.PracticeWindow_ConfirmationWithPartyQueue = new LinkedBlockingQueue<ConfirmationWithPartyComponent>();
		this.PracticeWindow_printInfoMessageQueue = new LinkedBlockingQueue<PrintInfoMessageComponent>();
		
		this.driverCalls = new LinkedBlockingQueue<PracticeTestDriverCalls>();
	}
	
	/**
	 * update the expected log with the given String.
	 * 
	 * @param str: The given String.
	 * @param testLog: The log.
	 */
	private void updateLog(String str, List<String> testLog){
		testLog.add("\t" + instructionCount +". "+str);
		instructionCount++;
	}
	
	/**
	 * Add a component from the given type (simulate response from stub).
	 * @param component: The given component
	 */
	public void addComponentForTest(ChooseListComponent component){
		functionQueue.add(component.getFunction());
		choosingList_chooseListQueue.add(component);
		updateLog(component.toString(),expectedTestLog);
	}
	
	/**
	 * Add a component from the given type (simulate response from stub).
	 * @param component: The given component
	 */
	public void addComponentForTest(
			ChoosingListRetireComponent component){
		functionQueue.add(component.getFunction());
		choosingList_retireQueue.add(component);
		updateLog(component.toString(),expectedTestLog);
	}
	
	/**
	 * Add a component from the given type (simulate response from stub).
	 * @param component: The given component
	 */
	public void addComponentForTest(ShowFirstImageComponent component){
		functionQueue.add(component.getFunction());
		ImagePanel_showFirstImageQueue.add(component);
		updateLog(component.toString(),expectedTestLog);
	}
	
	/**
	 * Add a component from the given type (simulate response from stub).
	 * @param component: The given component
	 */
	public void addComponentForTest(
			ImagePanelRetireComponent component){
		functionQueue.add(component.getFunction());
		ImagePanel_retireQueue.add(component);
		updateLog(component.toString(),expectedTestLog);
	}
	
	/**
	 * Add a component from the given type (simulate response from stub).
	 * @param component: The given component
	 */
	public void addComponentForTest(
			PrintErrorMessageComponent component){
		functionQueue.add(component.getFunction());
		PracticeWindow_printErrorMessageQueue.add(component);
		updateLog(component.toString(),expectedTestLog);
	}
	
	/**
	 * Add a component from the given type (simulate response from stub).
	 * @param component: The given component
	 */
	public void addComponentForTest(
			PrintConfirmationMessageComponent component){
		functionQueue.add(component.getFunction());
		PracticeWindow_printConfirmationMessageQueue.add(component);
		updateLog(component.toString(),expectedTestLog);
	}
	
	/**
	 * Add a component from the given type (simulate response from stub).
	 * @param component: The given component
	 */
	public void addComponentForTest(
			ConfirmationWithPartyComponent component){
		functionQueue.add(component.getFunction());
		PracticeWindow_ConfirmationWithPartyQueue.add(component);
		updateLog(component.toString(),expectedTestLog);
	}
	
	/**
	 * Add a component from the given type (simulate response from stub).
	 * @param component: The given component
	 */
	public void addComponentForTest(
			PrintInfoMessageComponent component){
		
		functionQueue.add(component.getFunction());
		PracticeWindow_printInfoMessageQueue.add(component);
		updateLog(component.toString(),expectedTestLog);
	}
	
	/**
	 * Return a PracticeStationWindowStubFactory
	 * that create window with the pre-defined responses.
	 * @return The factory to build the test PracticeStationWindow stub.
	 */
	public IPracticeStationWindowFactory getPracticeWindowFactory(){
		return new PracticeStationWindowStubFactory(this,
				PracticeWindow_printErrorMessageQueue,
				PracticeWindow_printConfirmationMessageQueue,
				PracticeWindow_ConfirmationWithPartyQueue,
				PracticeWindow_printInfoMessageQueue);
	}
	
	/**
	 * Return a ImagePanelStubFactory
	 * that create guides with the pre-defined responses.
	 * @return The factory to build the test ImagePanel stub.
	 */
	public IImagePanelFactory getImagePanelFactory(){
		return new ImagePanelStubFactory(this,
				ImagePanel_showFirstImageQueue,
				ImagePanel_retireQueue);
	}
	
	/**
	 * Return a ChoosingListStubFactory
	 * that create choosing list with the pre-defined responses.
	 * @return The factory to build the test ChoosingList stub.
	 */
	public IChoosingListFactory getChoosingListFactory(){
		return new ChoosingListStubFactory(this,
				choosingList_chooseListQueue,
				choosingList_retireQueue);
	}
	
	/**
	 * Assert that a call match the expected call.
	 * @param callerFunction: The actual call.
	 */
	public void checkCalling(PracticeTestFunction callerFunction){
		PracticeTestFunction shouldBeCaller = functionQueue.poll();
		Assert.assertEquals(shouldBeCaller, callerFunction);
	}
	
	/**
	 * Add a call to the unit (to perform practiceVote()).
	 */
	public void addPracticeVoteCall(){
		driverCalls.add(PracticeTestDriverCalls.PracticeVote);
		updateLog(PracticeTestDriverCalls.PracticeVote.toString(),expectedTestLog);
	}
	
	/**
	 * Add a call to the unit (to perform SetLanguage(language)).
	 * @param language: the language for the call.
	 */
	public void addSetLanguage(Languages language){
		switch(language){
		case Hebrew: driverCalls.add(PracticeTestDriverCalls.SetLangugeHebrew);
			updateLog(PracticeTestDriverCalls.SetLangugeHebrew.toString(),expectedTestLog);
			break;
		case English: driverCalls.add(PracticeTestDriverCalls.SetLangugeEnglish);
			updateLog(PracticeTestDriverCalls.SetLangugeEnglish.toString(),expectedTestLog);
			break;
		}
	}
	
	/**
	 * Add a retire call.
	 */
	public void addRetire(){
		driverCalls.add(PracticeTestDriverCalls.Retire);
		updateLog(PracticeTestDriverCalls.Retire.toString(),expectedTestLog);
	}
	
	/**
	 * before using runTest: 
	 * 	showing the expected function call of practice station to is stubs
	 */
	public void expectedTestLog(){
		System.out.println("expected test: "+ testName);
		for(String insturction : expectedTestLog){
			System.out.println(insturction);
		}
		System.out.println();
		System.out.println("end test: "+ testName);
		System.out.println();
		System.out.println();
	}
	
	/**
	 * Run the test.
	 * @param testedStation: The unit to test.
	 */
	public void runTest(IPracticeStation testedStation){
		
		addRetire();
		while(!driverCalls.isEmpty()){
			driverCalls.poll().activate(testedStation);
		}
		Assert.assertTrue(functionQueue.isEmpty());
	}
}
