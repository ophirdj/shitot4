package unitTests;

import partiesList.IPartiesList;
import partiesList.IParty;
import choosingList.ChooseType;
import choosingList.IChoosingWindow;
import choosingList.IChoosingList.ChoosingInterruptedException;

public class ChoosingWindowStub implements IChoosingWindow {

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
	public void closeWindow() {
		// TODO Auto-generated method stub

	}

	@Override
	public ChooseType receiveChoiceSymbol(IPartiesList partiesToShow)
			throws ChoosingInterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IParty getParty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void switchOn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void switchOff() {
		// TODO Auto-generated method stub

	}

}
