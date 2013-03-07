package votingStation.gui;

import global.gui.IWindow;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;

/**
 * Window for voting station
 * @author Ophir De Jager
 *
 */
public interface IVotingStationWindow extends  IWindow, Runnable{
	
	class IllegalIdException extends Exception{
		private static final long serialVersionUID = 1L;
	}
	
	
	/**
	 * Run the station on different thread. 
	 */
	void startLoop();
	
	/**
	 * Get voter's ID
	 * @return ID
	 * @throws ChoosingInterruptedException if interrupted (by endLoop or something)
	 * @throws IllegalIdException if ID is bad
	 */
	int getID() throws ChoosingInterruptedException, IllegalIdException;
	
	/**
	 * Get Password from user to verify he's a committee member
	 * @return password
	 * @throws ChoosingInterruptedException if interrupted (by endLoop or something)
	 */
	String getPassword() throws ChoosingInterruptedException;
	
	/**
	 * End execution
	 */
	void endLoop();
}
