package practiceStation.tests;

import java.util.NoSuchElementException;
import java.util.Queue;

import partiesList.model.IParty;
import choosingList.logic.IChoosingList;

public class PracticeTest_ChoosingListStub implements IChoosingList {

	private PracticeStationTestEnvironment testEnviroment;
	private Queue<ChooseListComponent> chooseListQueue;
	private Queue<ChoosingListRetireComponent> retireQueue;
	private Thread choosingThread;
	
	public PracticeTest_ChoosingListStub(
			PracticeStationTestEnvironment testEnviroment,
			Queue<ChooseListComponent> chooseListQueue,
			Queue<ChoosingListRetireComponent> retireQueue) {
		
		this.testEnviroment = testEnviroment;
		this.chooseListQueue = chooseListQueue;
		this.retireQueue = retireQueue;
	}
	
	public static class ChooseListComponent{
		private IParty returnValue;
		
		public ChooseListComponent(IParty returnValue) {
			this.returnValue = returnValue;
		}
		
		public IParty getReturnValue() throws InterruptedException{
			return returnValue;
		}
		
		@Override
		public String toString() {
			return "choosingList.chooseList(), return " + returnValue.toString(); 
		}
	}
	
	public static class ChooseListLongComponent extends ChooseListComponent{
		
		private long milliSeconds2Wait;
		
		public ChooseListLongComponent(IParty party, long milliSeconds2Wait) {
			super(party);
			this.milliSeconds2Wait = milliSeconds2Wait;
		}
		
		@Override
		public IParty getReturnValue() throws InterruptedException{
			try{
				Thread.sleep(milliSeconds2Wait);
			}catch (InterruptedException e) {
				throw new AssertionError();
			}
			return super.getReturnValue();
		}
		
		@Override
		public String toString() {
			return "wait " + milliSeconds2Wait + "millis, " + super.toString(); 
		}
	}
	
	public static class ChooseListTooLongComponent extends ChooseListComponent{
		
		private long milliSeconds2Wait;
		
		public ChooseListTooLongComponent(long milliSeconds2Wait) {
			super(null);
			this.milliSeconds2Wait = milliSeconds2Wait;
		}
		
		@Override
		public IParty getReturnValue() throws InterruptedException{
			try{
				Thread.sleep(milliSeconds2Wait);
				throw new AssertionError();
			}catch (InterruptedException e) {
				throw e;
			}
		}
		
		@Override
		public String toString() {
			return "wait " + milliSeconds2Wait + "millis, " +super.toString() + "shouldn't return. ";
		}
	}
	
	@Override
	public IParty chooseList() throws ChoosingInterruptedException {
		PracticeStationTestEnvironment.assertTrue(testEnviroment.checkCalling(PracticeTestFunction.ChoosingList_ChooseList));
		choosingThread = Thread.currentThread();
		IParty chosenParty;
		try{
			chosenParty = chooseListQueue.remove().getReturnValue();
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}catch (InterruptedException e) {
			throw new ChoosingInterruptedException();
		}
		choosingThread = null;
		return chosenParty;
	}
	
	public static class ChoosingListRetireComponent{
		@Override
		public String toString() {
			return "choosingList.retire()";
		}
	}

	@Override
	public void retire() {
		PracticeStationTestEnvironment.assertTrue(testEnviroment.checkCalling(PracticeTestFunction.ChoosingList_retire));
		if(choosingThread != null){
			choosingThread.interrupt();
		}
		try{
			retireQueue.remove();
		}catch (NoSuchElementException e) {
			 throw new AssertionError();
		}
	}

}
