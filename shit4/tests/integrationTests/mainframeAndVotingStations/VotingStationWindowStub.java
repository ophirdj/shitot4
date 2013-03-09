package integrationTests.mainframeAndVotingStations;

import org.junit.Assert;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import partiesList.model.IParty;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;
import votingStation.gui.IVotingStationWindow;
import votingStation.logic.IVotingStation;

public class VotingStationWindowStub extends IVotingStationWindow {

	private static final long serialVersionUID = 1L;
	private IVotingStation caller;
	private int id;
	private String password;
	private boolean wasWrongPassword;
	private boolean initialized;
	private boolean retired;
	private IntegrationTest test;

	public VotingStationWindowStub(IVotingStation caller, IntegrationTest test) {
		this.caller = caller;
		this.initialized = false;
		this.retired = false;
		this.test = test;
	}

	@Override
	public Boolean getConfirmation(String confirmationMessage) {
		Assert.fail();
		return null;
	}

	@Override
	public void printError(String errorMessage) {
		Assert.fail();
	}

	@Override
	public void printMessage(String message) {
		Assert.fail();
	}

	@Override
	public void setLanguage(Languages language) {
		Assert.fail();
	}

	@Override
	public String translate(Messages message) {
		Assert.fail();
		return null;
	}

	@Override
	public void printErrorMessage(Messages message) {
		test.setMessage(message);
	}

	@Override
	public void printInfoMessage(Messages message, IParty party) {}

	@Override
	public void printInfoMessage(Messages message) {
		Assert.fail();
	}

	@Override
	public boolean printConfirmationMessage(Messages message) {
		Assert.fail();
		return false;
	}

	@Override
	public boolean printConfirmationMessage(Messages message, IParty party) {
		Assert.fail();
		return false;
	}

	@Override
	public void closeWindow() {
		Assert.fail();
	}

	@Override
	public void startLoop() {
		this.initialized = true;
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
		this.retired = true;
	}
	
	
	

	public void setId(int id) {
		this.id = id;
	}

	public void makeVote() throws ChoosingInterruptedException {
		caller.voting();
	}

	public void testVote() {
		Assert.fail();
	}

	public void setPassword(String string) {
		this.password = string;
	}

	public boolean wasWrongPassword() {
		return wasWrongPassword;
	}
	
	public boolean isInitialized(){
		return initialized;
	}
	
	public boolean isRetired(){
		return retired;
	}

}
