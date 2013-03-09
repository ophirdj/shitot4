package integrationTests.practiceStationChoosingList;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Assert;

import global.dictionaries.Messages;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import choosingList.gui.IChoosingWindow;
import choosingList.logic.ChooseType;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;

public class ChoosingListWindowStub implements IChoosingWindow{

	private Queue<IParty> partiesQueue = new LinkedBlockingQueue<IParty>();
	private Queue<ChooseType> typeQueue = new LinkedBlockingQueue<ChooseType>();
	
	private long timeUntilMistake;

	private Queue<Boolean> confirmationAnswers = new LinkedBlockingQueue<Boolean>();
	private Boolean defualtConfirmationAnswer = true;
	private Thread thread;
	
	public ChoosingListWindowStub(long maxWaiting) {
		this.timeUntilMistake = maxWaiting;
	}
	
	/**
	 * Add amount of the given answer to return when Confirmation is required
	 * 
	 * @param answer: The needed result (yes/no)
	 * @param amount: The amount of times to return that answer.
	 */
	public void addConfirmationsResults(Boolean answer, int amount){
		for (int i = 0; i < amount; i++) {
			confirmationAnswers.add(answer);			
		}
	}
	
	/**
	 * Add the given type amount times.
	 * 
	 * @param type: The chosen action.
	 * @param amount: Amount of time
	 */
	public void addType(ChooseType type, int amount){
		for(int i = 0; i < amount; i++){
			this.typeQueue.add(type);
		}
	}
	
	/**
	 * Add a party to be return when using getParty.
	 * @param party: The party to be returned.
	 */
	public void addParty(IParty party){
		this.partiesQueue.add(party);
	}
	
	@Override
	public IParty getParty() {
		return partiesQueue.peek();
	}

	@Override
	public ChooseType receiveChoiceSymbol(IPartiesList partiesToShow)
			throws ChoosingInterruptedException {
		ChooseType type = typeQueue.poll();
		if(type == null){
			synchronized (this) {
				this.thread = Thread.currentThread();
				try {
					Thread.sleep(timeUntilMistake);
					Assert.fail();
					return null;
				} catch (InterruptedException e) {
					this.thread = null;
					throw new ChoosingInterruptedException();
				}
			}
		}else{
			if(type == ChooseType.Party){
				Assert.assertTrue(partiesListContain(partiesToShow, partiesQueue.peek()));
			}
			return type;
		}
	}

	/**
	 * Check that the list contain the needed party.
	 * @param partiesList: The list.
	 * @param neededParty: The party.
	 * @return true if the list contain the party, false otherwise.
	 */
	private boolean partiesListContain(IPartiesList partiesList, IParty neededParty) {
		if(partiesList.getWhiteNoteParty() == neededParty) return true;
		for(IParty party : partiesList){
			if(party == neededParty) return true;
		}
		return false;
	}

	@Override
	public void switchOff() {
		
	}

	@Override
	public void switchOn() {
		
	}

	@Override
	public void closeWindow() {
		if(thread != null) thread.interrupt();
	}

	@Override
	public Boolean getConfirmation(String confirmationMessage) {
		Assert.fail();
		return null;
	}

	@Override
	public boolean printConfirmationMessage(Messages message) {
		if(confirmationAnswers.isEmpty()) return defualtConfirmationAnswer;
		return confirmationAnswers.poll();
	}

	@Override
	public boolean printConfirmationMessage(Messages message, IParty party) {
		Assert.assertEquals(partiesQueue.remove(), party);
		if(confirmationAnswers.isEmpty()){
			return defualtConfirmationAnswer;
		}
		return confirmationAnswers.poll();
	}

	@Override
	public void printError(String errorMessage) {
		Assert.fail();
	}

	@Override
	public void printErrorMessage(Messages message) {

	}

	@Override
	public void printInfoMessage(Messages message, IParty party) {
		
	}

	@Override
	public void printInfoMessage(Messages message) {
		if(message == Messages.Are_you_sure_you_dont_want_to_vote_for_anyone){
			partiesQueue.remove();
		}
	}

	@Override
	public void printMessage(String message) {
		Assert.fail();
	}

	@Override
	public String translate(Messages message) {
		Assert.fail();
		return null;
	}

}
