package mainframe.tests;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import mainframe.gui.IMainframeWindow;

public class MainframeWindowStub implements IMainframeWindow {

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
		// TODO Auto-generated method stub

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
	public void showHistogram(IPartiesList parties) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showTable(IPartiesList parties) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
