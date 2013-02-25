package choosingList.tests;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Assert;

import partiesList.factories.IPartiesListFactory;
import partiesList.model.IParty;

import choosingList.factories.IChoosingWindowFactory;
import choosingList.logic.IChoosingList;
import choosingList.tests.ChoosingListCaller.ChooseListCallerWaitComponent;
import choosingList.tests.ChoosingListCaller.ChooseListRetireComponent;
import choosingList.tests.ChoosingListCaller.CallerComponent;
import choosingList.tests.ChoosingListCaller.ChooseListCallerComponent;
import choosingList.tests.ChoosingListWindowStub.CloseWindowComponent;
import choosingList.tests.ChoosingListWindowStub.ConformationWithPartyComponent;
import choosingList.tests.ChoosingListWindowStub.PrintConformationMessageComponent;
import choosingList.tests.ChoosingListWindowStub.getChoiceComponent;
import choosingList.tests.ChoosingListWindowStub.getPartyComponent;
import choosingList.tests.ChoosingListWindowStub.switchOffComponent;
import choosingList.tests.ChoosingListWindowStub.switchOnComponent;
import choosingList.tests.PartiesListStub.sublistComponent;
import choosingList.tests.PartiesListStub.whiteNotePartyComponent;

public class ChoosingListTestEnvironment {
	public static void assertTrue(boolean b){
		if(!b) throw new AssertionError();
	}
	
	private int instructionCounter = 1;
	private List<String> testLog = new LinkedList<String>();
	
	private Queue<ChoosingListFunction> functionQueue = new LinkedBlockingQueue<ChoosingListFunction>();
	private Queue<ConformationWithPartyComponent> conformationWithPartyQueue = new LinkedBlockingQueue<ConformationWithPartyComponent>();
	private Queue<PrintConformationMessageComponent> conformationQueue = new LinkedBlockingQueue<PrintConformationMessageComponent>();
	private Queue<CloseWindowComponent> closeWindowQueue = new LinkedBlockingQueue<CloseWindowComponent>();
	private Queue<switchOnComponent> switchOnQueue = new LinkedBlockingQueue<switchOnComponent>();
	private Queue<switchOffComponent> switchOffQueue = new LinkedBlockingQueue<switchOffComponent>();
	private Queue<getPartyComponent> getPartyQueue = new LinkedBlockingQueue<getPartyComponent>();
	private Queue<getChoiceComponent> recieveChoiceQueue = new LinkedBlockingQueue<getChoiceComponent>();
	private Queue<sublistComponent> sublistQueue = new LinkedBlockingQueue<sublistComponent>();
	private Queue<whiteNotePartyComponent> whiteNoteQueue = new LinkedBlockingQueue<whiteNotePartyComponent>();
	private Queue<CallerComponent> driverCalls = new LinkedBlockingQueue<CallerComponent>();
	
	public ChoosingListTestEnvironment(String testName) {
		testLog.add("build test: " + testName);
	}
	
	private void updateLog(String str){
		testLog.add("\t"+instructionCounter + ". " + str);
		instructionCounter++;
	}
	
	public void addComponentForTest(ConformationWithPartyComponent comp){
		functionQueue.add(comp.getFunction());
		conformationWithPartyQueue.add(comp);
		updateLog(comp.toString());
	}
	
	public void addComponentForTest(PrintConformationMessageComponent comp){
		functionQueue.add(comp.getFunction());
		conformationQueue.add(comp);
		updateLog(comp.toString());
	}
	
	public void addComponentForTest(CloseWindowComponent comp){
		functionQueue.add(comp.getFunction());
		closeWindowQueue.add(comp);
		updateLog(comp.toString());
	}
	
	public void addComponentForTest(switchOnComponent comp){
		functionQueue.add(comp.getFunction());
		switchOnQueue.add(comp);
		updateLog(comp.toString());
	}
	
	public void addComponentForTest(switchOffComponent comp){
		functionQueue.add(comp.getFunction());
		switchOffQueue.add(comp);
		updateLog(comp.toString());
	}
	
	public void addComponentForTest(getPartyComponent comp){
		functionQueue.add(comp.getFunction());
		getPartyQueue.add(comp);
		updateLog(comp.toString());
	}
	
	public void addComponentForTest(getChoiceComponent comp){
		functionQueue.add(comp.getFunction());
		recieveChoiceQueue.add(comp);
		updateLog(comp.toString());
	}
	
	public void addComponentForTest(sublistComponent comp){
		functionQueue.add(comp.getFunction());
		sublistQueue.add(comp);
		updateLog(comp.toString());
	}
	
	public void addComponentForTest(whiteNotePartyComponent comp){
		functionQueue.add(comp.getFunction());
		whiteNoteQueue.add(comp);
		updateLog(comp.toString());
	}
	
	public void addRetireCall(){
		CallerComponent caller = new ChooseListRetireComponent();
		driverCalls.add(caller);
		updateLog(caller.toString());
	}
	
	public void addChooseList(IParty result){
		CallerComponent caller = new ChooseListCallerComponent(result);
		driverCalls.add(caller);
		updateLog(caller.toString());
	}
	
	public void addWaitChooseList(){
		CallerComponent caller = new ChooseListCallerWaitComponent();
		driverCalls.add(caller);
		updateLog(caller.toString());
	}
	
	public void printExpectedLog(){
		for(String str : testLog){
			System.out.println(str);
		}
		System.out.println();
	}
	
	public boolean checkCalling(ChoosingListFunction callerFunction){
		ChoosingListFunction shouldBeCaller = functionQueue.poll();
		if(!callerFunction.equals(shouldBeCaller)){
			System.out.println("was: "+callerFunction+", should be: "+shouldBeCaller);
		}
		return callerFunction.equals(shouldBeCaller);
	}

	public IChoosingWindowFactory getChoosingWindowFactory() {
		return new ChoosingListWindowStubFactory(this,
				conformationQueue,conformationWithPartyQueue,
				closeWindowQueue,switchOnQueue,switchOffQueue,
				getPartyQueue,recieveChoiceQueue);
	}

	public IPartiesListFactory getPartiesFactory(int size) {
		return new PartiesListStubFactory(this,size,sublistQueue,whiteNoteQueue);
	}

	public void rutTest(IChoosingList tested) {
		while(!driverCalls.isEmpty()){
			driverCalls.poll().activate(tested);
		}
		Assert.assertTrue(functionQueue.isEmpty());
	}
	
}
