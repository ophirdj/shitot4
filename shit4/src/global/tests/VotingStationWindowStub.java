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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printError(String errorMessage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void printMessage(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLanguage(Languages language) {
		// TODO Auto-generated method stub

	}

	@Override
	public String translate(Messages message) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub

	}

	@Override
	public void printInfoMessage(Messages message) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean printConformationMessage(Messages message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean printConformationMessage(Messages message, IParty party) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void closeWindow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startLoop() {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

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
