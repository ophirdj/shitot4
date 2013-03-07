package choosingList.tests;

import org.junit.Assert;

import choosingList.logic.IChoosingList;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;
import partiesList.model.IParty;

public class ChoosingListCaller {
	
	
	// TODO maybe this interface should move out?
	// TODO add javadoc
	public interface CallerComponent{
		public void activate(IChoosingList choosingList);
	}
	
	// TODO add javadoc
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
	
	// TODO add javadoc
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
	
	// TODO add javadoc
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
	
	// TODO add javadoc
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
