package votingStation.logic;
import mainframe.communication.IStationsController;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;

import partiesList.model.IPartiesList;

/**
 * Interface of all voting stations as seen from the mainframe and UI
 * @author Ophir De Jager
 *
 */
public interface IVotingStation{
	
	class CannotVoteHere extends Exception{
		private static final long serialVersionUID = 1L;
		}
	
	
	/**
	 * Initialize the voting station - the first method to be called.
	 * @param parties - The parties list
	 * @param controller - The stations controller (for communication with the mainframe)
	 */
	void initialize(IPartiesList parties,IStationsController controller);
	
	/**
	 * Stop the station from running.
	 */
	void retire();
	
	
	/**
	 * 
	 * @return The local parties list.
	 */
	IPartiesList getPartiesList();
	
	
	/**
	 * Start the voting sequence in the station's window.
	 */
	void voting() throws ChoosingInterruptedException;
	
	
	/**
	 * Start the test voting sequence in the station's window.
	 */
	void testVoting() throws ChoosingInterruptedException;

	
	/**
	 * Our loved peep method
	 */
	void peep();


}
