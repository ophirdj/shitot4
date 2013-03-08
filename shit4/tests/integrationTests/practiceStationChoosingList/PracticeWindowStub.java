package integrationTests.practiceStationChoosingList;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Assert;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import global.gui.StationPanel;
import partiesList.model.IParty;
import practiceStation.gui.IPracticeStationWindow;

public class PracticeWindowStub extends IPracticeStationWindow{

	private static final long serialVersionUID = 1L;

	private Queue<IParty> expectedParties = new LinkedBlockingQueue<IParty>();
	private Queue<Boolean> confirmationAnswers = new LinkedBlockingQueue<Boolean>();
	private Boolean defualtConfirmationAnswer = true;
	
	@Override
	public void closeWindow() {
		
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
	 * Add amount of the given answer to return when Confirmation is required
	 * 
	 * @param answer: The needed result (yes/no)
	 * @param amount: The amount of times to return that answer.
	 */
	public void addExpectedParties(IParty expectedParty){
		expectedParties.add(expectedParty);			
	}

	@Override
	public Boolean getConfirmation(StationPanel stationPanel,
			String confirmationMessage) {
		Assert.fail();
		return false;
	}

	@Override
	public boolean printConfirmationMessage(Messages message) {
		if(confirmationAnswers.isEmpty()) return defualtConfirmationAnswer;
		return confirmationAnswers.poll();
	}

	@Override
	public boolean printConfirmationMessage(Messages message, IParty party) {
		Assert.assertEquals(expectedParties.remove(), party);
		if(confirmationAnswers.isEmpty()) return defualtConfirmationAnswer;
		return confirmationAnswers.poll();
	}

	@Override
	public void printError(StationPanel stationPanel, String errorMessage) {
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
		
	}

	@Override
	public void printMessage(StationPanel stationPanel, String message) {
		
	}

	@Override
	public void setLanguage(Languages language) {
		
	}

	@Override
	public String translate(Messages message) {
		Assert.fail();
		return null;
	}

}
