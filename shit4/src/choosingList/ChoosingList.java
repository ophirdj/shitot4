package choosingList;

import javax.swing.JPanel;

import factories.IChoosingWindowFactory;
import partiesList.IPartiesList;
import partiesList.IParty;


public class ChoosingList implements IChoosingList{
	private IPartiesList parties;
	private IChoosingWindow window;
	private int place;
	
	final static private int MAX_PARTIES = 9;
	
	//for test without graphic: stationPanel = null
	public ChoosingList(IPartiesList parties, JPanel stationPanel, IChoosingWindowFactory windowFactory){
		this.parties = parties;
		this.window = windowFactory.createInstance(stationPanel);
		this.place = 0;
	};
	
	@Override
	public IParty chooseList() throws ChoosingInterruptedException{
		IParty chosenParty = null;
		Boolean hasConfirmed = false;
		window.switchOn();
		while (!hasConfirmed){
			int end_for_print = place+MAX_PARTIES;
			if(end_for_print > parties.size())  end_for_print = parties.size();
			IPartiesList partiesToShow = parties.sublist(place, end_for_print);
			ChooseType type = window.receiveChoiceSymbol(partiesToShow);
			if(type == ChooseType.Next){
				place+=MAX_PARTIES;
				if (place >= parties.size()){
					place=0;
				}
			}
			else if(type == ChooseType.Prev){
				place-=MAX_PARTIES;
				if (place < 0){
					place=parties.size()-parties.size()%MAX_PARTIES;
				}
			}
			else if(type == ChooseType.Party){
				chosenParty = window.getParty();
				if(chosenParty == parties.getWhiteNoteParty()){
					hasConfirmed = window.getConfirmation("Are you sure you don't want to vote for anyone?");
				}
				else{
					hasConfirmed = window.getConfirmation("Are you sure you want to vote for " + chosenParty.getName() + "?");
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
