package global.tests;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import global.gui.StationPanel;
import partiesList.model.IParty;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;
import votingStation.gui.IVotingStationWindow;
import votingStation.logic.IVotingStation;

public class VotingStationWindowStub extends StationPanel implements IVotingStationWindow {

	private static final long serialVersionUID = 1L;
	private IVotingStation caller;
	private int id;
	private String password;
	private boolean wasWrongPassword;

	public VotingStationWindowStub(IVotingStation caller) {
		this.caller = caller;
	}

	@Override
	public Boolean getConfirmation(String confirmationMessage) {
		return null;
	}

	@Override
	public void printError(String errorMessage) {

	}

	@Override
	public void printMessage(String message) {

	}

	@Override
	public void setLanguage(Languages language) {
	}

	@Override
	public String translate(Messages message) {
		return null;
	}

	@Override
	public void printErrorMessage(Messages message) {
		switch (message) {
		case wrong_password:
			setWasWrongPassword(true);
			break;

		default:
			break;
		}
	}

	@Override
	public void printInfoMessage(Messages message, IParty party) {

	}

	@Override
	public void printInfoMessage(Messages message) {

	}

	@Override
	public boolean printConformationMessage(Messages message) {
		return false;
	}

	@Override
	public boolean printConformationMessage(Messages message, IParty party) {
		return false;
	}

	@Override
	public void closeWindow() {

	}

	@Override
	public void run() {

	}

	@Override
	public void startLoop() {

	}

	@Override
	public int getID() throws ChoosingInterruptedException {
		return id;
	}

	@Override
	public String getPassword() throws ChoosingInterruptedException {
		return password;
	}

	@Override
	public void endLoop() {

	}
	
	
	

	public void setId(int i) {
		this.id = i;
	}

	public void makeVote() {
		try {
			caller.voting();
		} catch (ChoosingInterruptedException e) {}
	}

	public void testVote() {
		try {
			caller.testVoting();
		} catch (ChoosingInterruptedException e) {}
		
	}

	public void setPassword(String string) {
		this.password = string;
		
	}

	public boolean isWasWrongPassword() {
		return wasWrongPassword;
	}

	private void setWasWrongPassword(boolean wasWrongPassword) {
		this.wasWrongPassword = wasWrongPassword;
	}

}
