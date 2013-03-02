package practiceStation.tests;

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
import practiceStation.tests.PracticeTest_ChoosingListStub.ChooseListComponent;
import practiceStation.tests.PracticeTest_ChoosingListStub.ChoosingListRetireComponent;
import practiceStation.tests.PracticeTest_ImagePanelStub.ImagePanelRetireComponent;
import practiceStation.tests.PracticeTest_ImagePanelStub.ShowFirstImageComponent;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.ConformationWithPartyComponent;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.PrintConformationMessageComponent;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.PrintInfoMessageComponent;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.PrintErrorMessageComponent;

public class PracticeStationTestEnvironment {
	
	private Queue<PracticeTestFunction> functionQueue;
	private Queue<ChooseListComponent> choosingList_chooseListQueue;
	private Queue<ChoosingListRetireComponent> choosingList_retireQueue;
	private Queue<ShowFirstImageComponent> ImagePanel_showFirstImageQueue;
	private Queue<ImagePanelRetireComponent> ImagePanel_retireQueue;
	private Queue<PrintErrorMessageComponent> PracticeWindow_printErrorMessageQueue;
	private Queue<PrintConformationMessageComponent> PracticeWindow_printConformationMessageQueue;
	private Queue<ConformationWithPartyComponent> PracticeWindow_ConformationWithPartyQueue;
	private Queue<PrintInfoMessageComponent> PracticeWindow_printInfoMessageQueue;
	
	private Queue<PracticeTestDriverCalls> driverCalls;
	private String testName;
	private List<String> expectedTestLog = new LinkedList<String>();
	private int instructionCount = 1;
	
	public PracticeStationTestEnvironment(String testName) {
		this.testName = testName;
		this.functionQueue = new LinkedBlockingQueue<PracticeTestFunction>();
		this.choosingList_chooseListQueue = new LinkedBlockingQueue<ChooseListComponent>();
		this.choosingList_retireQueue = new LinkedBlockingQueue<ChoosingListRetireComponent>();
		this.choosingList_retireQueue = new LinkedBlockingQueue<ChoosingListRetireComponent>();
		this.ImagePanel_showFirstImageQueue = new LinkedBlockingQueue<ShowFirstImageComponent>();
		this.ImagePanel_retireQueue = new LinkedBlockingQueue<ImagePanelRetireComponent>();
		this.PracticeWindow_printErrorMessageQueue = new LinkedBlockingQueue<PrintErrorMessageComponent>();
		this.PracticeWindow_printConformationMessageQueue = new LinkedBlockingQueue<PrintConformationMessageComponent>();
		this.PracticeWindow_ConformationWithPartyQueue = new LinkedBlockingQueue<ConformationWithPartyComponent>();
		this.PracticeWindow_printInfoMessageQueue = new LinkedBlockingQueue<PrintInfoMessageComponent>();
		
		this.driverCalls = new LinkedBlockingQueue<PracticeTestDriverCalls>();
	}
	
	private void updateLog(String str, List<String> testLog){
		testLog.add("\t" + instructionCount +". "+str);
		instructionCount++;
	}
	
	public void addComponentForTest(ChooseListComponent component){
		functionQueue.add(component.getFunction());
		choosingList_chooseListQueue.add(component);
		updateLog(component.toString(),expectedTestLog);
	}
	
	public void addComponentForTest(
			ChoosingListRetireComponent component){
		functionQueue.add(component.getFunction());
		choosingList_retireQueue.add(component);
		updateLog(component.toString(),expectedTestLog);
	}
	
	public void addComponentForTest(ShowFirstImageComponent component){
		functionQueue.add(component.getFunction());
		ImagePanel_showFirstImageQueue.add(component);
		updateLog(component.toString(),expectedTestLog);
	}
	
	public void addComponentForTest(
			ImagePanelRetireComponent component){
		functionQueue.add(component.getFunction());
		ImagePanel_retireQueue.add(component);
		updateLog(component.toString(),expectedTestLog);
	}
	
	public void addComponentForTest(
			PrintErrorMessageComponent component){
		functionQueue.add(component.getFunction());
		PracticeWindow_printErrorMessageQueue.add(component);
		updateLog(component.toString(),expectedTestLog);
	}
	
	public void addComponentForTest(
			PrintConformationMessageComponent component){
		functionQueue.add(component.getFunction());
		PracticeWindow_printConformationMessageQueue.add(component);
		updateLog(component.toString(),expectedTestLog);
	}
	
	public void addComponentForTest(
			ConformationWithPartyComponent component){
		functionQueue.add(component.getFunction());
		PracticeWindow_ConformationWithPartyQueue.add(component);
		updateLog(component.toString(),expectedTestLog);
	}
	
	public void addComponentForTest(
			PrintInfoMessageComponent component){
		
		functionQueue.add(component.getFunction());
		PracticeWindow_printInfoMessageQueue.add(component);
		updateLog(component.toString(),expectedTestLog);
	}
	
	public IPracticeStationWindowFactory getPracticeWindowFactory(){
		return new PracticeTest_PracticeStationWindowStubFactory(this,
				PracticeWindow_printErrorMessageQueue,
				PracticeWindow_printConformationMessageQueue,
				PracticeWindow_ConformationWithPartyQueue,
				PracticeWindow_printInfoMessageQueue);
	}
	
	public IImagePanelFactory getImagePanelFactory(){
		return new PracticeTest_ImagePanelStubFactory(this,
				ImagePanel_showFirstImageQueue,
				ImagePanel_retireQueue);
	}
	
	public IChoosingListFactory getChoosingListFactory(){
		return new PracticeTest_choosingListStubFactory(this,
				choosingList_chooseListQueue,
				choosingList_retireQueue);
	}
	
	public void checkCalling(PracticeTestFunction callerFunction){
		PracticeTestFunction shouldBeCaller = functionQueue.poll();
		Assert.assertEquals(shouldBeCaller, callerFunction);
	}
	
	public void addPracticeVoteCall(){
		driverCalls.add(PracticeTestDriverCalls.PracticeVote);
		updateLog(PracticeTestDriverCalls.PracticeVote.toString(),expectedTestLog);
	}
	
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
	
	public void runTest(IPracticeStation testedStation){
		
		addRetire();
		while(!driverCalls.isEmpty()){
			driverCalls.poll().activate(testedStation);
		}
		Assert.assertTrue(functionQueue.isEmpty());
	}
}
