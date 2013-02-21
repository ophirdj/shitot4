package mainframe.gui;

import global.gui.IWindow;
import partiesList.model.IPartiesList;

public interface IMainframeWindow extends IWindow, Runnable {

	/**
	 * Show a histogram of votes to the parties.
	 * 
	 * @param parties
	 */
	void showHistogram(IPartiesList parties);

	/**
	 * Show a table of votes to the parties.
	 * 
	 * @param parties
	 */
	void showTable(IPartiesList parties);
	
	/**
	 * 
	 * @return the entered id
	 */
	int getID();
	
	/**
	 * Start the main loop.
	 */
	void init();
}
