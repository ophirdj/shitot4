package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import votingStation.VotingStation_window;

public class VotingWaitForAction implements ActionListener{
	private Object lock;
	private boolean was_pushed;
	private VotingStation_window station;
	
	public VotingWaitForAction(Object window_lock, VotingStation_window station) {
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
	
	public boolean was_pusehd(){
		return was_pushed;
	}
}
	
