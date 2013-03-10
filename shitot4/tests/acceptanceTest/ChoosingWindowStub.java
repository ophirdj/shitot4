package acceptanceTest;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Assert;

import global.dictionaries.Messages;
import partiesList.model.IPartiesList;
import partiesList.model.IPartiesList.PartyDoesNotExist;
import partiesList.model.IParty;
import choosingList.gui.IChoosingWindow;
import choosingList.logic.ChooseType;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;

public class ChoosingWindowStub implements IChoosingWindow {

	private String symbol;
	private Queue<ChooseType> chooseType = new LinkedBlockingQueue<ChooseType>();
	private ChooseType defaultChooseType = ChooseType.Party;
	private IParty party;
	private Queue<Boolean> confirmation = new LinkedBlockingQueue<Boolean>();
	private Boolean defaultConfirmation = true;

	@Override
	public Boolean getConfirmation(String confirmationMessage) {
		if(confirmation.isEmpty()) return defaultConfirmation;
		return confirmation.poll();
	}

	@Override
	public void printError(String errorMessage) {

	}

	@Override
	public void printMessage(String message) {

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
		if(confirmation.isEmpty()) return defaultConfirmation;
		return confirmation.poll();
	}

	@Override
	public boolean printConfirmationMessage(Messages message, IParty party) {
		if(confirmation.isEmpty()) return defaultConfirmation;
		return confirmation.poll();
	}

	@Override
	public void closeWindow() {

	}

	@Override
	public ChooseType receiveChoiceSymbol(IPartiesList partiesToShow)
			throws ChoosingInterruptedException {
		Assert.assertTrue(partiesToShow.size() <= 9);
		ChooseType type = defaultChooseType;
		if(!chooseType.isEmpty()) type = chooseType.poll();
		if(type == ChooseType.Party && symbol != null)
			try {
				this.party = partiesToShow.getPartyBySymbol(symbol);
			} catch (PartyDoesNotExist e) {
				throw new AssertionError();
			}
		if(type == ChooseType.Party && symbol == null){
			this.party = partiesToShow.getWhiteNoteParty();
		}
		return type;
	}

	@Override
	public IParty getParty() {
		return party;
	}

	@Override
	public void switchOn() {
		
	}

	@Override
	public void switchOff() {
		
	}
	
	public void setChooseType(ChooseType type) {
		this.chooseType.add(type);
	}

	public void setParty(String string) {
		this.symbol = string;
	}

	public void setConfirmation(boolean b) {
		this.confirmation.add(b);
	}

}
