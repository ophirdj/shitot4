package votingStation.tests;

import org.junit.Assert;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import global.gui.StationPanel;
import partiesList.model.IParty;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;
import votingStation.gui.IVotingStationWindow;

public class VotingStationWindowStub extends StationPanel implements IVotingStationWindow {

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
		Assert.assertEquals(errorMessage, message);

	}

	@Override
	public void printInfoMessage(Messages message, IParty party) {
		Assert.assertEquals(this.message, message);
		Assert.assertSame(this.party, party);

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
		if(interrupt) throw new ChoosingInterruptedException();
		if(throwID) throw new NumberFormatException();
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
