package votingStation.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO add javadoc
public class VotingWaitForAction implements ActionListener{
	private Object lock;
	private boolean was_pushed;
	private VotingStationWindow station;
	
	public VotingWaitForAction(Object window_lock, VotingStationWindow station) {
		lock = window_lock;
		was_pushed = false;
		this.station = station;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		synchronized (lock) {
			station.call();
			lock.notify();
		}
		was_pushed = true;
	}
	
	/**
	 * Check if button was pushed
	 * @return true if button was pushed, false otherwise
	 */
	public boolean was_pusehd(){
		return was_pushed;
	}
}
	
