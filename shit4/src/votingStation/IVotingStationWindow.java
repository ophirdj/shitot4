package votingStation;

import choosingList.IChoosingList.ChoosingInterruptedException;
import GUI.IWindow;

public interface IVotingStationWindow extends  IWindow, Runnable{
	/**
	 * run the station on different thread. 
	 */
	void startLoop();
	
	/**
	 * 
	 * @return the id the user entered
	 */
	int getID() throws ChoosingInterruptedException;
	
	/**
	 * 
	 * @return the password the user entered
	 */
	String getPassword() throws ChoosingInterruptedException;
	
	/**
	 * finish the loop. 
	 */
	void endLoop();
}
