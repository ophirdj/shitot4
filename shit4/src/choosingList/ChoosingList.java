package choosingList;



import GUI.Global_Window;

import javax.swing.JPanel;

import partiesList.IPartiesList;
import partiesList.IParty;
import partiesList.PartiesList;
import partiesList.Party;


public class ChoosingList implements IChoosingList{
	private PartiesList parties;
	private ChoosingList_window window;
	private JPanel caller_panel;
	private int place;
	final static private int MAX_PARTIES = 9;
	
	public ChoosingList(IPartiesList parties, JPanel stationPanel){
		this.parties = (PartiesList) parties;
		this.window = new ChoosingList_window();
		caller_panel = stationPanel;
		this.place = 0;
	};
	
	public IParty chooseList(){
		IParty chosenParty = null;
		Boolean hasConfirmed = false;
		Global_Window.main_window.switch_panels(caller_panel, window);
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
		Global_Window.main_window.switch_panels(window, caller_panel);
		return chosenParty;
	}
}
