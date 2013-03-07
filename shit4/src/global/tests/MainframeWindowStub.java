package global.tests;

import org.junit.Assert;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import global.gui.StationPanel;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import mainframe.gui.IMainframeWindow;
import mainframe.logic.IMainframe;

public class MainframeWindowStub extends StationPanel implements IMainframeWindow {

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
	public void showHistogram(IPartiesList parties) {
		Assert.assertEquals(expectedPartiesList, parties);
	}

	@Override
	public void showTable(IPartiesList parties) {
		Assert.assertEquals(expectedPartiesList, parties);
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public void init() {

	}

	public void setExpectedPartiesList(IPartiesList expectedPartiesList) {
		this.expectedPartiesList = expectedPartiesList;
		
	}
	
	

}
