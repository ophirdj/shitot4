package choosingList.tests;

import choosingList.logic.IChoosingList;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;
import partiesList.model.IParty;

public class ChoosingListCaller {
	public interface CallerComponent{
		public void activate(IChoosingList choosingList);
	}
	
	public static class ChooseListCallerComponent implements CallerComponent{
		private IParty shouldBeParty;

		public ChooseListCallerComponent(IParty party) {
			this.shouldBeParty = party;
		}
		
		@Override
		public String toString() {
			return "call chooseList(). should return: " + shouldBeParty;
		}
		
		public void activate(IChoosingList choosingList) {
			try{
				IParty party = choosingList.chooseList();
				ChoosingListTestEnvironment.assertTrue(party.equals(shouldBeParty));
			}catch(ChoosingInterruptedException e){
				throw new AssertionError();
			}
		}
	}
	
	public static class CloseChoosingList implements Runnable{

		private final long shortTime = 10;
		private IChoosingList choosingList;

		public CloseChoosingList(IChoosingList choosingList) {
			this.choosingList = choosingList;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(shortTime);
				choosingList.retire();
			} catch (InterruptedException e) {
				throw new AssertionError();
			}
		}
		
	}
	
	public static class ChooseListCallerWaitComponent extends ChooseListCallerComponent{
		
		public ChooseListCallerWaitComponent() {
			super(null);
		}
		
		@Override
		public String toString() {
			String str = "call chooseList(). should throw ChoosingInterruptedException";
			return str;
		}
		
		@Override
		public void activate(IChoosingList choosingList) {
			Thread closer = new Thread(new CloseChoosingList(choosingList));
			try{
				closer.start();
				choosingList.chooseList();
				throw new AssertionError();
			}catch (ChoosingInterruptedException e) {
			}
		}
	}
	
	public static class ChooseListRetireComponent implements CallerComponent{

		@Override
		public String toString() {
			return "choosingList.retire()";
		}
		
		@Override
		public void activate(IChoosingList choosingList) {
			choosingList.retire();
		}
		
	}
}
