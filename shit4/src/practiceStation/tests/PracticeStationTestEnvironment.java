package practiceStation.tests;

import global.dictionaries.Languages;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import choosingList.factories.IChoosingListFactory;

import practiceStation.factories.IImagePanelFactory;
import practiceStation.factories.IPracticeStationWindowFactory;
import practiceStation.logic.IPracticeStation;
import practiceStation.tests.PracticeTest_ChoosingListStub.ChooseListComponent;
import practiceStation.tests.PracticeTest_ChoosingListStub.ChoosingListRetireComponent;
import practiceStation.tests.PracticeTest_ImagePanelStub.ImagePanelRetireComponent;
import practiceStation.tests.PracticeTest_ImagePanelStub.showFirstImageComponent;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.ConformationWithPartyComponent;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.PrintConformationMessageComponent;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.printErrorMessageComponent;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.printInfoMessageComponent;

public class PracticeStationTestEnvironment {
	
	public static void assertTrue(boolean b){
		if(!b) throw new AssertionError();
	}
	
	private Queue<PracticeTestFunction> functionQueue;
	private Queue<ChooseListComponent> choosingList_chooseListQueue;
	private Queue<ChoosingListRetireComponent> choosingList_retireQueue;
	private Queue<showFirstImageComponent> ImagePanel_showFirstImageQueue;
	private Queue<ImagePanelRetireComponent> ImagePanel_retireQueue;
	private Queue<printErrorMessageComponent> PracticeWindow_printErrorMessageQueue;
	private Queue<PrintConformationMessageComponent> PracticeWindow_printConformationMessageQueue;
	private Queue<ConformationWithPartyComponent> PracticeWindow_ConformationWithPartyQueue;
	private Queue<printInfoMessageComponent> PracticeWindow_printInfoMessageQueue;
	
	private Queue<PracticeTestDriverCalls> driverCalls;
	private String testName;
	
	public PracticeStationTestEnvironment(String testName) {
		this.testName = testName;
		this.functionQueue = new LinkedBlockingQueue<PracticeTestFunction>();
		this.choosingList_chooseListQueue = new LinkedBlockingQueue<ChooseListComponent>();
		this.choosingList_retireQueue = new LinkedBlockingQueue<ChoosingListRetireComponent>();
		this.choosingList_retireQueue = new LinkedBlockingQueue<ChoosingListRetireComponent>();
		this.ImagePanel_showFirstImageQueue = new LinkedBlockingQueue<showFirstImageComponent>();
		this.ImagePanel_retireQueue = new LinkedBlockingQueue<ImagePanelRetireComponent>();
		this.PracticeWindow_printErrorMessageQueue = new LinkedBlockingQueue<printErrorMessageComponent>();
		this.PracticeWindow_printConformationMessageQueue = new LinkedBlockingQueue<PrintConformationMessageComponent>();
		this.PracticeWindow_ConformationWithPartyQueue = new LinkedBlockingQueue<ConformationWithPartyComponent>();
		this.PracticeWindow_printInfoMessageQueue = new LinkedBlockingQueue<printInfoMessageComponent>();
		
		this.driverCalls = new LinkedBlockingQueue<PracticeTestDriverCalls>();
	}
	
	public void addComponentForTest(ChooseListComponent component){
		functionQueue.add(PracticeTestFunction.ChoosingList_ChooseList);
		choosingList_chooseListQueue.add(component);
	}
	
	public void addComponentForTest(
			ChoosingListRetireComponent component){
		functionQueue.add(PracticeTestFunction.ChoosingList_retire);
		choosingList_retireQueue.add(component);
	}
	
	public void addComponentForTest(showFirstImageComponent component){
		functionQueue.add(PracticeTestFunction.ImagePanel_showFirstImage);
		ImagePanel_showFirstImageQueue.add(component);
	}
	
	public void addComponentForTest(
			ImagePanelRetireComponent component){
		functionQueue.add(PracticeTestFunction.ImagePanel_retire);
		ImagePanel_retireQueue.add(component);
	}
	
	public void addComponentForTest(
			printErrorMessageComponent component){
		functionQueue.add(PracticeTestFunction.PracticeWindow_printErrorMessage);
		PracticeWindow_printErrorMessageQueue.add(component);
	}
	
	public void addComponentForTest(
			PrintConformationMessageComponent component){
		functionQueue.add(PracticeTestFunction.PracticeWindow_printConformationMessage);
		PracticeWindow_printConformationMessageQueue.add(component);
	}
	
	public void addComponentForTest(
			ConformationWithPartyComponent component){
		functionQueue.add(PracticeTestFunction.PracticeWindow_printConformationMessageWithParty);
		PracticeWindow_ConformationWithPartyQueue.add(component);
	}
	
	public void addComponentForTest(
			printInfoMessageComponent component){
		functionQueue.add(PracticeTestFunction.PracticeWindow_printInfoMessage);
		PracticeWindow_printInfoMessageQueue.add(component);
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
	
	public boolean checkCalling(PracticeTestFunction callerFunction){
		PracticeTestFunction shouldBeCaller = functionQueue.poll();
		return (callerFunction.equals(shouldBeCaller));
	}
	
	public void addPracticeVoteCall(){
		driverCalls.add(PracticeTestDriverCalls.PracticeVote);
	}
	
	public void addSetLanguage(Languages language){
		switch(language){
		case Hebrew: driverCalls.add(PracticeTestDriverCalls.SetLangugeHebrew);
			break;
		case English: driverCalls.add(PracticeTestDriverCalls.SetLangugeEnglish);
			break;
		}
	}
	
	public void addRetire(){
		driverCalls.add(PracticeTestDriverCalls.Retire);
	}
	
	/**
	 * before using runTest: 
	 * 	showing the expected function call of practice station to is stubs
	 */
	public void testLog(){
		System.out.println("about to run test: "+ testName);
		
		Iterator<ChooseListComponent> ChooseListIterator = choosingList_chooseListQueue.iterator();
		Iterator<ChoosingListRetireComponent> ChoosingListRetireIterator = choosingList_retireQueue.iterator();
		Iterator<showFirstImageComponent> showFirstImageIterator = ImagePanel_showFirstImageQueue.iterator();
		Iterator<ImagePanelRetireComponent> ImagePanelRetireIterator = ImagePanel_retireQueue.iterator();
		Iterator<printErrorMessageComponent> printErrorMessageIterator= PracticeWindow_printErrorMessageQueue.iterator();
		Iterator<PrintConformationMessageComponent> PrintConformationMessageIterator = PracticeWindow_printConformationMessageQueue.iterator();
		Iterator<ConformationWithPartyComponent> conformationWithPartyIterator =  PracticeWindow_ConformationWithPartyQueue.iterator();
		Iterator<printInfoMessageComponent> printInfoMessageIterator = PracticeWindow_printInfoMessageQueue.iterator();
		
		int stubCallId = 0, driverCallId=0;
		
		System.out.println("calls to stub: ");
		
		for (Iterator<PracticeTestFunction> iterator = functionQueue.iterator(); iterator.hasNext();) {
			PracticeTestFunction function = iterator.next();
			Iterator<?> correctIterator;
			switch(function){
			case ChoosingList_ChooseList:
				correctIterator = ChooseListIterator;
				break;
			case ChoosingList_retire:
				correctIterator = ChoosingListRetireIterator;
				break;
			case ImagePanel_showFirstImage:
				correctIterator = showFirstImageIterator;
				break;
			case ImagePanel_retire:
				correctIterator = ImagePanelRetireIterator;
				break;
			case PracticeWindow_printConformationMessage:
				correctIterator = PrintConformationMessageIterator;
				break;
			case PracticeWindow_printConformationMessageWithParty:
				correctIterator = conformationWithPartyIterator;
				break;
			case PracticeWindow_printErrorMessage:
				correctIterator = printErrorMessageIterator;
				break;
			case PracticeWindow_printInfoMessage:
				correctIterator = printInfoMessageIterator;
				break;
			default:
				System.out.println("wrong usage.");
				throw new RuntimeException();
			}
			
			if(!correctIterator.hasNext()){
				System.out.println("wrong usage.");
				throw new RuntimeException();
			}
			System.out.println("\t"+stubCallId+". "+correctIterator.next());
			stubCallId++;
		}
		
		System.out.println();
		System.out.println("call from driver");
		for (Iterator<PracticeTestDriverCalls> iterator = driverCalls.iterator(); iterator.hasNext();) {
			PracticeTestDriverCalls call =  iterator.next();
			System.out.println("\t"+driverCallId+". "+call);
			driverCallId++;
		}
		System.out.println();
		System.out.println("end of test: " + testName);
		System.out.println();
		System.out.println();
	}
	
	public void runTest(IPracticeStation testedStation){
		
		addRetire();
		while(!driverCalls.isEmpty()){
			driverCalls.poll().activate(testedStation);
		}
		PracticeStationTestEnvironment.assertTrue(functionQueue.isEmpty());
	}
}
