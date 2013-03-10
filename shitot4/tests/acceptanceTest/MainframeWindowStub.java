package acceptanceTest;

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

	public MainframeWindowStub(IMainframe callerStation) {
		super();
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

	}

	@Override
	public void printInfoMessage(Messages message, IParty party) {

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
	public int getID() {
		return 0;
	}

	public void setExpectedPartiesList(IPartiesList expectedPartiesList) {
		this.expectedPartiesList = expectedPartiesList;
		
	}

	@Override
	public void setState(MainframeState state) {
		
	}

	@Override
	public void displayHistogram() {
		
	}

	@Override
	public void displayTable() {
		
	}

	@Override
	public void setDataDisplay(IPartiesList parties) {
		Assert.assertEquals(expectedPartiesList, parties);
	}
	
	

}
