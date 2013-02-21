package unitTests;

import partiesList.IPartiesList;
import partiesList.IParty;
import choosingList.ChooseType;
import choosingList.IChoosingWindow;
import choosingList.IChoosingList.ChoosingInterruptedException;
import dictionaries.Languages;
import dictionaries.Messages;

public class ChoosingWindowStub implements IChoosingWindow {

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
	public void closeWindow() {
	}

	@Override
	public ChooseType receiveChoiceSymbol(IPartiesList partiesToShow) throws ChoosingInterruptedException {
		return null;
	}

	@Override
	public IParty getParty() {
		return null;
	}

	@Override
	public void switchOn() {
	}

	@Override
	public void switchOff() {
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

}
