package mainframe.gui;

import mainframe.logic.MainframeAction.MainframeState;
import global.dictionaries.Messages;
import global.gui.Main_Window;
import global.gui.StationPanel;
import partiesList.model.IPartiesList;

/**
 * Interface for window of mainframe station
 * @author Ophir De Jager
 *
 */
public abstract class IMainframeWindow extends StationPanel{
	
	private static final long serialVersionUID = 1L;

	/**
	 * exception in case that the id that the user is gave as input is of wrong type:
	 * not of length 9
	 * not positive number
	 * 
	 */
	public class IllegalIdException extends Exception{
		private static final long serialVersionUID = 1L;
	}
	
	/**
	 * For test only.
	 */
	public IMainframeWindow() {
		super();
	}

	public IMainframeWindow(Messages mainFrame, Main_Window main_window) {
		super(mainFrame, main_window);
	}

	/**
	 * Show a histogram of votes to the parties.
	 * 
	 * @param parties
	 */
	public abstract void setDataDisplay(IPartiesList parties);
	
	/**
	 * 
	 * @return the entered id
	 */
	public abstract int getID() throws IllegalIdException;
	
	/**
	 * signal the Mainframe window 
	 */
	public abstract void setState(MainframeState state);

	/**
	 * display the histogram of the final vote counting
	 */
	public abstract void displayHistogram();
	
	/**
	 * display the table of the final vote counting
	 */
	public abstract void displayTable();
}
