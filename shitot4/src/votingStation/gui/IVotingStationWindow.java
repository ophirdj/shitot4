package votingStation.gui;

import global.dictionaries.Messages;
import global.gui.Main_Window;
import global.gui.StationPanel;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;

/**
 * Window for voting station
 * @author Ophir De Jager
 *
 */
public abstract class IVotingStationWindow extends  StationPanel{
	
	private static final long serialVersionUID = 1L;

	
	public class IllegalIdException extends Exception{
		private static final long serialVersionUID = 1L;
	}
	
	public IVotingStationWindow(){
		super();
	}
	
	public IVotingStationWindow(Messages votingStation, int id,
			Main_Window main_window) {
		super(votingStation, id, main_window);
	}

	/**
	 * Run the station on different thread. 
	 */
	public abstract void startLoop();
	
	/**
	 * Get voter's ID
	 * @return ID
	 * @throws ChoosingInterruptedException if interrupted (by endLoop or something)
	 * @throws IllegalIdException if ID is bad
	 */
	public abstract int getID() throws ChoosingInterruptedException, IllegalIdException;
	
	/**
	 * Get Password from user to verify he's a committee member
	 * @return password
	 * @throws ChoosingInterruptedException if interrupted (by endLoop or something)
	 */
	public abstract String getPassword() throws ChoosingInterruptedException;
	
	/**
	 * End execution
	 */
	public abstract void endLoop();
}
