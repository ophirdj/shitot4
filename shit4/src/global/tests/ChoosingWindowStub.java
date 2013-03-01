package global.tests;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import global.dictionaries.Languages;
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
	private Queue<Boolean> conformation = new LinkedBlockingQueue<Boolean>();
	private Boolean defaultConformation = true;

	@Override
	public Boolean getConfirmation(String confirmationMessage) {
		if(conformation.isEmpty()) return defaultConformation;
		return conformation.poll();
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
		
	}

	@Override
	public void printInfoMessage(Messages message, IParty party) {
		
	}

	@Override
	public void printInfoMessage(Messages message) {
		
	}

	@Override
	public boolean printConformationMessage(Messages message) {
		if(conformation.isEmpty()) return defaultConformation;
		return conformation.poll();
	}

	@Override
	public boolean printConformationMessage(Messages message, IParty party) {
		if(conformation.isEmpty()) return defaultConformation;
		return conformation.poll();
	}

	@Override
	public void closeWindow() {

	}

	@Override
	public ChooseType receiveChoiceSymbol(IPartiesList partiesToShow)
			throws ChoosingInterruptedException {
		ChooseType type = defaultChooseType;
		if(!chooseType.isEmpty()) type = chooseType.poll();
		if(type == ChooseType.Party && symbol != null)
			try {
				this.party = partiesToShow.getPartyBySymbol(symbol);
			} catch (PartyDoesNotExist e) {
				throw new AssertionError();
			}
		if(type == ChooseType.Party && symbol == null)
			this.party = partiesToShow.getWhiteNoteParty();
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

	public void setConformation(boolean b) {
		this.conformation.add(b);
	}

}
