package choosingList.gui;


import global.gui.IWindow;
import choosingList.logic.ChooseType;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;

/**
 * 
 * Graphical interface for voting process.
 *
 */
public interface IChoosingWindow extends IWindow{
		
	/**
	 * show the parties and wait for user action
	 * @param partiesToShow give 9 parties to show
	 * @return the action chosen by user
	 */
	public ChooseType receiveChoiceSymbol(IPartiesList partiesToShow) throws ChoosingInterruptedException;
	
	/**
	 * if the user chose a party, this return the party he chose
	 * @return the chosen party
	 */
	public IParty getParty();
	
	/**
	 * change the view in main window to this panel
	 */
	public void switchOn();
	
	/**
	 * return the view to the given panel
	 */
	public void switchOff();

}
