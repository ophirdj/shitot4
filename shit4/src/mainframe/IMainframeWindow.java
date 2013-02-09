package mainframe;

import GUI.IWindow;
import partiesList.IPartiesList;

public interface IMainframeWindow extends IWindow, Runnable {

	public enum MainframeAction {
		initialize, restore, countVotes, shutDown, identification
	}

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
	 * Start the main loop.
	 */
	void init();
}
