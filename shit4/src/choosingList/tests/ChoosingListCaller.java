package choosingList.tests;

import org.junit.Assert;

import choosingList.logic.IChoosingList;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;
import partiesList.model.IParty;

public class ChoosingListCaller {
	
	
	
	/**
	 * Interface for CallerComponent: a class that simulate a call
	 * to the tested unit (ChoosingList).
	 */
	public interface CallerComponent{
		
		/**
		 * Send the correct call, update the run log and check return values.
		 * @param choosingList: The unit to be tested
		 */
		public void activate(IChoosingList choosingList);
	}
	
	/**
	 * A class that simulate a chooseList() call.
	 */
	public static class ChooseListCallerComponent implements CallerComponent{
		private IParty shouldBeParty;
		protected ChoosingListTestEnvironment testEnvironment;

		public ChooseListCallerComponent(IParty party, ChoosingListTestEnvironment testEnvironment) {
			this.shouldBeParty = party;
			this.testEnvironment = testEnvironment;
		}
		
		@Override
		public String toString() {
			return "call chooseList(). should return: " + shouldBeParty;
		}
		
		@Override
		public void activate(IChoosingList choosingList) {
			try{
				testEnvironment.updateRunningTestLog(this.toString());
				IParty party = choosingList.chooseList();
				Assert.assertEquals(shouldBeParty, party);
			}catch(ChoosingInterruptedException e){
				throw new AssertionError();
			}
		}
	}
	
	/**
	 * When start running, responsible to call the tested unit 
	 * with retire after some time (to test retire call while voting)  
	 */
	public static class CloseChoosingList implements Runnable{

		private final long shortTime = 10;
		private IChoosingList choosingList;
		private ChoosingListTestEnvironment testEnvironment;

		public CloseChoosingList(ChoosingListTestEnvironment testEnvironment, IChoosingList choosingList) {
			this.choosingList = choosingList;
			this.testEnvironment = testEnvironment;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(shortTime);
				ChooseListRetireComponent closer = new ChooseListRetireComponent(testEnvironment);
				closer.activate(choosingList);
			} catch (InterruptedException e) {
				throw new AssertionError();
			}
		}
		
	}
	
	/**
	 * A class that simulate a chooseList() call that was interrupted.
	 */
	public static class ChooseListCallerWaitComponent extends ChooseListCallerComponent{
		
		public ChooseListCallerWaitComponent(ChoosingListTestEnvironment testEnvironment) {
			super(null,testEnvironment);
		}
		
		@Override
		public String toString() {
			String str = "call chooseList(). should throw ChoosingInterruptedException";
			return str;
		}
		
		@Override
		public void activate(IChoosingList choosingList) {
			Thread closer = new Thread(new CloseChoosingList(testEnvironment,choosingList));
			try{
				closer.start();
				testEnvironment.updateRunningTestLog(this.toString());
				choosingList.chooseList();
				Assert.fail();
			}catch (ChoosingInterruptedException e) {
			}
		}
	}
	
	/**
	 * A class that simulate a retire() call to the tested unit.
	 */
	public static class ChooseListRetireComponent implements CallerComponent{

		private ChoosingListTestEnvironment testEnvironment;

		public ChooseListRetireComponent(ChoosingListTestEnvironment testEnvironment) {
			this.testEnvironment = testEnvironment;
		}
		
		@Override
		public String toString() {
			return "choosingList.retire()";
		}
		
		@Override
		public void activate(IChoosingList choosingList) {
			testEnvironment.updateRunningTestLog(this.toString());
			choosingList.retire();
		}
		
	}
}
