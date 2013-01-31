package choosingList;

import GUI.IWindow;
import partiesList.IPartiesList;
import partiesList.IParty;
import partiesList.Party;

public interface IChoosingWindow extends IWindow{
		
	//Called when a parties list appears on the screen, waits for the user to click 
	//on one of the parties or on next/previous button
	//return the type of action requested
	public ChooseType receiveChoiceSymbol(IPartiesList partiesToShow);
	
	//If receiveChoiceSymbol return ChooseType.Party, we can get it using this function
	public IParty getParty();

}
