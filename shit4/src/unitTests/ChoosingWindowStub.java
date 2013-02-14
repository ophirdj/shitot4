package unitTests;

import partiesList.IPartiesList;
import partiesList.IParty;
import choosingList.ChooseType;
import choosingList.IChoosingWindow;
import choosingList.IChoosingList.ChoosingInterruptedException;

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

}
