package votingStation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VoteClick implements ActionListener {
	private VotingStation_window button_window;
	private boolean test_vote;
	private Object lock;
	
	public VoteClick(VotingStation_window station, boolean is_test_vote, Object window_lock) {
		button_window = station;
		test_vote = is_test_vote;
		lock = window_lock;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(test_vote){
			button_window.setAction(VotingStationAction.TEST_VOTE);
		}
		else{
			button_window.setAction(VotingStationAction.VOTING);
		}
		synchronized (lock) {
			lock.notify();
		}
	}
}
