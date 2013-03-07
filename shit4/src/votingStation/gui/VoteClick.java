package votingStation.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import votingStation.logic.VotingStationAction;

/**
 * Handle button clicks in voting station window
 * @author Ophir De Jager
 *
 */
public class VoteClick implements ActionListener {
	private VotingStationWindow button_window;
	private VotingStationAction voting_action;
	private Object lock;
	
	// TODO add javadoc
	public VoteClick(VotingStationWindow station, VotingStationAction action, Object window_lock) {
		button_window = station;
		voting_action = action;
		lock = window_lock;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		button_window.setAction(voting_action);
		synchronized (lock) {
			lock.notify();
		}
	}
}
