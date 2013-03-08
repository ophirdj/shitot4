package unitTests.votingStation;

import org.junit.Assert;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import global.gui.StationPanel;
import partiesList.model.IParty;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;
import votingStation.gui.IVotingStationWindow;

public class VotingStationWindowStub extends IVotingStationWindow {

	private static final long serialVersionUID = 1L;
	
	
	public String password = null;
	public Messages errorMessage = null;
	public Messages message = null;
	public IParty party = null;
	public int id = 0;
	public boolean throwID = false;
	public boolean interrupt = false;
	public boolean retired = false;
	
	
	public VotingStationWindowStub() {
		super();
	}

	@Override
	public Boolean getConfirmation(StationPanel station, String confirmationMessage) {
		return null;
	}

	@Override
	public void printError(StationPanel station, String errorMessage) {

	}

	@Override
	public void printMessage(StationPanel station, String message) {

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
		Assert.assertEquals(errorMessage, message);

	}

	@Override
	public void printInfoMessage(Messages message, IParty party) {
		Assert.assertEquals(this.message, message);
		Assert.assertSame(this.party, party);

	}

	@Override
	public void printInfoMessage(Messages message) {

	}

	@Override
	public boolean printConfirmationMessage(Messages message) {
		return false;
	}

	@Override
	public boolean printConfirmationMessage(Messages message, IParty party) {
		return false;
	}

	@Override
	public void closeWindow() {

	}

	@Override
	public void startLoop() {

	}

	@Override
	public int getID() throws ChoosingInterruptedException,IllegalIdException {
		if(interrupt) throw new ChoosingInterruptedException();
		if(throwID) throw new IllegalIdException();
		return id;
	}

	@Override
	public String getPassword() throws ChoosingInterruptedException {
		if(interrupt || password == null) throw new ChoosingInterruptedException();
		return password;
	}

	@Override
	public void endLoop() {
		retired = true;
	}

}
