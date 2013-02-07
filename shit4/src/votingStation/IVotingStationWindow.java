package votingStation;

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
	int getID();
	
	/**
	 * 
	 * @return the password the user entered
	 */
	String getPassword();
	
	/**
	 * finish the loop. 
	 */
	void endLoop();
}
