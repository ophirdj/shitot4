package mainframe.gui;

import mainframe.logic.MainframeAction.MainframeState;
import global.gui.IWindow;
import partiesList.model.IPartiesList;

/**
 * Interface for window of mainframe station
 * @author Ophir De Jager
 *
 */
public interface IMainframeWindow extends IWindow, Runnable {
	
	/**
	 * exception in case that the id that the user is gave as input is of wrong type:
	 * not of length 9
	 * not positive number
	 * 
	 */
	class IllegalIdException extends Exception{
		private static final long serialVersionUID = 1L;
	}
	
	

	/**
	 * Show a histogram of votes to the parties.
	 * 
	 * @param parties
	 */
	void setDataDisplay(IPartiesList parties);
	
	/**
	 * 
	 * @return the entered id
	 */
	int getID() throws IllegalIdException;
	
	/**
	 * signal the Mainframe window 
	 */
	void setState(MainframeState state);

	/**
	 * display the histogram of the final vote counting
	 */
	void displayHistogram();
	
	/**
	 * display the table of the final vote counting
	 */
	void displayTable();
}
