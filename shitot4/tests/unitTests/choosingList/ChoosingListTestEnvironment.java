package unitTests.choosingList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Assert;

import partiesList.factories.IPartiesListFactory;
import partiesList.model.IParty;
import unitTests.choosingList.ChoosingListCaller.CallerComponent;
import unitTests.choosingList.ChoosingListCaller.ChooseListCallerComponent;
import unitTests.choosingList.ChoosingListCaller.ChooseListCallerWaitComponent;
import unitTests.choosingList.ChoosingListCaller.ChooseListRetireComponent;
import unitTests.choosingList.ChoosingListWindowStub.CloseWindowComponent;
import unitTests.choosingList.ChoosingListWindowStub.ConfirmationWithPartyComponent;
import unitTests.choosingList.ChoosingListWindowStub.PrintConfirmationMessageComponent;
import unitTests.choosingList.ChoosingListWindowStub.getChoiceComponent;
import unitTests.choosingList.ChoosingListWindowStub.getPartyComponent;
import unitTests.choosingList.ChoosingListWindowStub.switchOffComponent;
import unitTests.choosingList.ChoosingListWindowStub.switchOnComponent;
import unitTests.choosingList.PartiesListStub.sublistComponent;
import unitTests.choosingList.PartiesListStub.whiteNotePartyComponent;

import choosingList.factories.IChoosingWindowFactory;
import choosingList.logic.IChoosingList;

public class ChoosingListTestEnvironment {
	
	private List<String> expectedTestLog = new LinkedList<String>();
	
	private List<String> runningTestLog = new LinkedList<String>();
	private String testName;
	
	private Queue<ChoosingListFunction> functionQueue = new LinkedBlockingQueue<ChoosingListFunction>();
	private Queue<ConfirmationWithPartyComponent> confirmationWithPartyQueue = new LinkedBlockingQueue<ConfirmationWithPartyComponent>();
	private Queue<PrintConfirmationMessageComponent> confirmationQueue = new LinkedBlockingQueue<PrintConfirmationMessageComponent>();
	private Queue<CloseWindowComponent> closeWindowQueue = new LinkedBlockingQueue<CloseWindowComponent>();
	private Queue<switchOnComponent> switchOnQueue = new LinkedBlockingQueue<switchOnComponent>();
	private Queue<switchOffComponent> switchOffQueue = new LinkedBlockingQueue<switchOffComponent>();
	private Queue<getPartyComponent> getPartyQueue = new LinkedBlockingQueue<getPartyComponent>();
	private Queue<getChoiceComponent> recieveChoiceQueue = new LinkedBlockingQueue<getChoiceComponent>();
	private Queue<sublistComponent> sublistQueue = new LinkedBlockingQueue<sublistComponent>();
	private Queue<whiteNotePartyComponent> whiteNoteQueue = new LinkedBlockingQueue<whiteNotePartyComponent>();
	private Queue<CallerComponent> driverCalls = new LinkedBlockingQueue<CallerComponent>();
	
	
	public ChoosingListTestEnvironment(String testName) {
		this.testName = testName;
	}
	
	private void updateExpectedTestLog(String str){
		expectedTestLog.add(str);
	}
	
	public void updateRunningTestLog(String str){
		runningTestLog.add(str);
	}
	
	public void addComponentForTest(ConfirmationWithPartyComponent comp){
		functionQueue.add(comp.getFunction());
		confirmationWithPartyQueue.add(comp);
		updateExpectedTestLog(comp.toString());
		comp.setTestEnvironment(this);
	}
	
	public void addComponentForTest(PrintConfirmationMessageComponent comp){
		functionQueue.add(comp.getFunction());
		confirmationQueue.add(comp);
		updateExpectedTestLog(comp.toString());
		comp.setTestEnvironment(this);
	}
	
	public void addComponentForTest(CloseWindowComponent comp){
		functionQueue.add(comp.getFunction());
		closeWindowQueue.add(comp);
		updateExpectedTestLog(comp.toString());
		comp.setTestEnvironment(this);
	}
	
	public void addComponentForTest(switchOnComponent comp){
		functionQueue.add(comp.getFunction());
		switchOnQueue.add(comp);
		updateExpectedTestLog(comp.toString());
		comp.setTestEnvironment(this);
	}
	
	public void addComponentForTest(switchOffComponent comp){
		functionQueue.add(comp.getFunction());
		switchOffQueue.add(comp);
		updateExpectedTestLog(comp.toString());
		comp.setTestEnvironment(this);
	}
	
	public void addComponentForTest(getPartyComponent comp){
		functionQueue.add(comp.getFunction());
		getPartyQueue.add(comp);
		updateExpectedTestLog(comp.toString());
		comp.setTestEnvironment(this);
	}
	
	public void addComponentForTest(getChoiceComponent comp){
		functionQueue.add(comp.getFunction());
		recieveChoiceQueue.add(comp);
		updateExpectedTestLog(comp.toString());
		comp.setTestEnvironment(this);
	}
	
	public void addComponentForTest(sublistComponent comp){
		functionQueue.add(comp.getFunction());
		sublistQueue.add(comp);
		updateExpectedTestLog(comp.toString());
		comp.setTestEnvironment(this);
	}
	
	public void addComponentForTest(whiteNotePartyComponent comp){
		functionQueue.add(comp.getFunction());
		whiteNoteQueue.add(comp);
		updateExpectedTestLog(comp.toString());
		comp.setTestEnvironment(this);
	}
	
	public void addRetireCall(){
		CallerComponent caller = new ChooseListRetireComponent(this);
		driverCalls.add(caller);
		updateExpectedTestLog(caller.toString());
		
	}
	
	//add retire in needed place
	public void neededRetireCall(){
		updateExpectedTestLog(new ChooseListRetireComponent(this).toString());
	}
	
	public void addChooseList(IParty result){
		CallerComponent caller = new ChooseListCallerComponent(result,this);
		driverCalls.add(caller);
		updateExpectedTestLog(caller.toString());
	}
	
	public void addWaitChooseList(){
		CallerComponent caller = new ChooseListCallerWaitComponent(this);
		driverCalls.add(caller);
		updateExpectedTestLog(caller.toString());
	}
	
	private void printLog(List<String> testLog,String starting){
		int lineNum = 1;
		System.out.println(starting+testName);
		for(String str : testLog){
			System.out.println("\t"+lineNum+". "+str);
			lineNum++;
		}
		System.out.println();
		System.out.println("end of test: "+testName);
		System.out.println();
		System.out.println();
	}
	
	public void printExpectedLog(){
		printLog(expectedTestLog,"expected test: ");
	}
	
	public void printRunningLog(){
		printLog(runningTestLog,"actual test: ");
	}
	
	public void printLogDiffs(){
		int lineNum = 1;
		boolean sameLogs = true;
		Iterator<String> runningIterator = runningTestLog.iterator();
		Iterator<String> expectedIterator = expectedTestLog.iterator();
		while(runningIterator.hasNext() && expectedIterator.hasNext()){
			String runningStr = runningIterator.next();
			String expectedStr = expectedIterator.next();
			if(!runningStr.equals(expectedStr)){
				if(sameLogs){
					System.out.println("in test: " + testName);
				}
				System.out.println("in line: "+ lineNum +" should be: ");
				System.out.println(expectedStr);
				System.out.println("got: ");
				System.out.println(runningStr);
				sameLogs = false;
			}
			lineNum++;
		}
		if(expectedIterator.hasNext()){
			sameLogs = false;
			while(expectedIterator.hasNext()){
				String expectedStr = expectedIterator.next();
				System.out.println("in line: "+ lineNum +" should be: ");
				System.out.println(expectedStr);
				System.out.println("got: ");
				System.out.println();
				lineNum++;
			}
		}
		if(runningIterator.hasNext()){
			sameLogs = false;
			while(runningIterator.hasNext()){
				String runningStr = runningIterator.next();
				System.out.println("in line: "+ lineNum +" should be: ");
				System.out.println();
				System.out.println("got: ");
				System.out.println(runningStr);
				lineNum++;
			}
		}
		
		if(sameLogs){
			System.out.println(testName + " ended successfully");
		}
		Assert.assertTrue(sameLogs);
	}
	
	public void checkCalling(ChoosingListFunction callerFunction){
		ChoosingListFunction shouldBeCaller = functionQueue.poll();
		Assert.assertEquals(shouldBeCaller, callerFunction);
	}

	public IChoosingWindowFactory getChoosingWindowFactory() {
		return new ChoosingListWindowStubFactory(this,
				confirmationQueue,confirmationWithPartyQueue,
				closeWindowQueue,switchOnQueue,switchOffQueue,
				getPartyQueue,recieveChoiceQueue);
	}

	public IPartiesListFactory getPartiesFactory(int size) {
		return new PartiesListStubFactory(this,size,sublistQueue,whiteNoteQueue);
	}

	public void runTest(IChoosingList tested) {
		while(!driverCalls.isEmpty()){
			driverCalls.poll().activate(tested);
			
		}
		Assert.assertTrue(functionQueue.isEmpty());
	}
	
}
