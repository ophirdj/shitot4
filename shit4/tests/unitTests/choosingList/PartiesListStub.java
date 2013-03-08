package unitTests.choosingList;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

import org.junit.Assert;

import partiesList.model.IPartiesList;
import partiesList.model.IParty;

public class PartiesListStub implements IPartiesList{

	private ChoosingListTestEnvironment testEnvironment;
	private Queue<sublistComponent> sublistQueue;
	private Queue<whiteNotePartyComponent> whiteNoteQueue;
	private int partiesAmount;
	

	public PartiesListStub(ChoosingListTestEnvironment testEnvironment,int partiesAmount,
			Queue<sublistComponent> sublistQueue,
			Queue<whiteNotePartyComponent> whiteNoteQueue) {
		this.testEnvironment = testEnvironment;
		this.partiesAmount = partiesAmount;
		this.sublistQueue = sublistQueue;
		this.whiteNoteQueue = whiteNoteQueue;
	}
	
	@Override
	public int size() {
		return partiesAmount;
	}
	
	public static class sublistComponent{
		
		private int shouldBeStart;
		private int shouldBeEnd;
		private IPartiesList returnVal;
		private ChoosingListTestEnvironment testEnvironment;

		public sublistComponent(int start, int end, IPartiesList returnVal) {
			this.shouldBeStart = start;
			this.shouldBeEnd =end;
			this.returnVal = returnVal;
		}
		
		public void setTestEnvironment(ChoosingListTestEnvironment testEnvironment){
			this.testEnvironment = testEnvironment;
		}
		
		@Override
		public String toString() {
			return "parties.sublist("+shouldBeStart+","+shouldBeEnd+"), return: "+returnVal;
		}
		
		public IPartiesList checkAndReturn(int start, int end){
			Assert.assertEquals(shouldBeStart, start);
			Assert.assertEquals(shouldBeEnd, end);
			testEnvironment.updateRunningTestLog(this.toString());
			return returnVal;
		}
		
		public ChoosingListFunction getFunction(){
			return ChoosingListFunction.PartiesList_sublist;
		}
	}

	@Override
	public IPartiesList sublist(int start, int end) {
		testEnvironment.checkCalling(ChoosingListFunction.PartiesList_sublist);
		try{
			return sublistQueue.remove().checkAndReturn(start, end);
		}catch(NoSuchElementException e){
			throw new AssertionError();
		}
	}
	
	public static class whiteNotePartyComponent{
		
		private IParty returnVal;
		private ChoosingListTestEnvironment testEnvironment;

		public whiteNotePartyComponent(IParty returnVal) {
			this.returnVal = returnVal;
		}
		
		public void setTestEnvironment(ChoosingListTestEnvironment testEnvironment){
			this.testEnvironment = testEnvironment;
		}
		
		@Override
		public String toString() {
			return "parties.getWhiteVote(), return " + returnVal;
		}
		
		public IParty checkAndReturn(){
			testEnvironment.updateRunningTestLog(this.toString());
			return returnVal;
		}
		
		public ChoosingListFunction getFunction(){
			return ChoosingListFunction.PartiesList_getWhiteNote;
		}
	}
	
	@Override
	public IParty getWhiteNoteParty() {
		testEnvironment.checkCalling(ChoosingListFunction.PartiesList_getWhiteNote);
		try{
			return whiteNoteQueue.remove().checkAndReturn();
		}catch(NoSuchElementException e){
			throw new AssertionError();
		}
	}
	
	
	
	@Override
	public void addParty(IParty party) {
		throw new AssertionError();
	}

	@Override
	public IPartiesList copy() {
		throw new AssertionError();
	}

	@Override
	public IParty getPartyBySymbol(String symbol) throws PartyDoesNotExist {
		throw new AssertionError();
	}

	@Override
	public int getTotalVotes() {
		throw new AssertionError();
	}

	@Override
	public IPartiesList joinLists(IPartiesList partiesList) {
		throw new AssertionError();
	}

	@Override
	public void peep() {
		throw new AssertionError();
	}

	@Override
	public IPartiesList zeroCopy() {
		throw new AssertionError();
	}

	@Override
	public Iterator<IParty> iterator() {
		throw new AssertionError();
	}

}
