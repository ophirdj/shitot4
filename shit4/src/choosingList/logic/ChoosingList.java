package choosingList.logic;

import global.dictionaries.Messages;
import global.gui.StationPanel;
import choosingList.factories.IChoosingWindowFactory;
import choosingList.gui.IChoosingWindow;


import partiesList.model.IPartiesList;
import partiesList.model.IParty;


public class ChoosingList implements IChoosingList{
	private IPartiesList parties;
	private IChoosingWindow window;
	private int place;
	
	final static private int MAX_PARTIES = 9;
	
	//for test without graphic: stationPanel = null
	public ChoosingList(IPartiesList parties, StationPanel stationPanel, IChoosingWindowFactory windowFactory){
		this.parties = parties;
		this.window = windowFactory.createInstance(stationPanel);
		this.place = 0;
	};
	
	@Override
	public IParty chooseList() throws ChoosingInterruptedException{
		IParty chosenParty = null;
		Boolean hasConfirmed = false;
		place = 0;
		int partiesSize = parties.size();
		IParty whiteNote = parties.getWhiteNoteParty();
		window.switchOn();
		while (!hasConfirmed){
			int end_for_print = place+MAX_PARTIES;
			if(end_for_print > partiesSize)  end_for_print = partiesSize;
			IPartiesList partiesToShow = parties.sublist(place, end_for_print);
			ChooseType type = window.receiveChoiceSymbol(partiesToShow);
			if(type == ChooseType.Next){
				place+=MAX_PARTIES;
				if (place >= partiesSize){
					place=0;
				}
			}
			else if(type == ChooseType.Prev){
				place-=MAX_PARTIES;
				if (place < 0){
					place=partiesSize-partiesSize%MAX_PARTIES;
				}
			}
			else if(type == ChooseType.Party){
				chosenParty = window.getParty();
				if(chosenParty == whiteNote){
					hasConfirmed = window.printConformationMessage(Messages.Are_you_sure_you_dont_want_to_vote_for_anyone);
				}
				else{
					hasConfirmed = window.printConformationMessage(Messages.Are_you_sure_you_want_to_vote_for, chosenParty);
				}
			}
		}
		window.switchOff();
		return chosenParty;
	}
	
	@Override
	public void retire(){
		window.closeWindow();
	}
}
