package votingStation;
import communication.IStationsController;

import choosingList.IChoosingList.ChoosingInterruptedException;
import partiesList.IPartiesList;
import mainframe.IMainframe;

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
	 * stop the station from running.
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
	 * our loved peephole method
	 */
	void peep();


}
