package choosingList;

import javax.swing.JPanel;

import GUI.IWindow;
import partiesList.IPartiesList;
import partiesList.IParty;

public interface IChoosingWindow extends IWindow{
		
	/**
	 * show the parties and wait for user action
	 * @param partiesToShow give 9 parties to show
	 * @return the action chosen by user
	 */
	public ChooseType receiveChoiceSymbol(IPartiesList partiesToShow);
	
	/**
	 * if the user chose a party, this return the party he chose
	 * @return the chosen party
	 */
	public IParty getParty();
	
	/**
	 * change the view in main window to this panel
	 * @param switchFrom the panel to switch from
	 */
	public void switchOn(JPanel switchFrom);
	
	/**
	 * return the view to the given panel
	 * @param switchTo the panel to switch to
	 */
	public void switchOff(JPanel switchTo);

}
