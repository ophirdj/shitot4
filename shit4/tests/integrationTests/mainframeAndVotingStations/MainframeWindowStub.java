package integrationTests.mainframeAndVotingStations;

import org.junit.Assert;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import mainframe.gui.IMainframeWindow;
import mainframe.logic.IMainframe;
import mainframe.logic.MainframeAction.MainframeState;

public class MainframeWindowStub extends IMainframeWindow {

	private static final long serialVersionUID = 1L;
	private IPartiesList expectedPartiesList;
	private boolean initialized;
	private boolean votesCounted;
	private IPartiesList parties;

	public MainframeWindowStub(IMainframe callerStation) {
		super();
		initialized = votesCounted = false;
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
		Assert.fail();
	}

	@Override
	public void printInfoMessage(Messages message, IParty party) {
		Assert.fail();
	}

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
	public int getID() {
		Assert.fail();
		return 0;
	}

	public void setExpectedPartiesList(IPartiesList expectedPartiesList) {
		this.expectedPartiesList = expectedPartiesList;
	}

	@Override
	public void setState(MainframeState state) {
		switch(state){
		case AfterInit: this.initialized = true;
		break;
		case VotesCounted: this.votesCounted = true;
		break;
		default:;
		}
		
	}

	@Override
	public void displayHistogram() {
		Assert.fail();
	}

	@Override
	public void displayTable() {
		Assert.fail();
	}

	@Override
	public void setDataDisplay(IPartiesList parties) {
		this.parties = parties;
	}

	public boolean isInitialized() {
		return initialized;
	}
	
	public boolean isVotesCounted() {
		return votesCounted;
	}
	
	public IPartiesList getParties(){
		return parties;
	}
	
	

}
