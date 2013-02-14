package practiceStation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PracticeClick implements ActionListener {
	private PracticeStationWindow button_window;
	private PracticeStationAction practice_action;
	private Object lock;
	
	public PracticeClick(PracticeStationWindow station, PracticeStationAction action, Object window_lock) {
		button_window = station;
		practice_action = action;
		lock = window_lock;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		button_window.setAction(practice_action);
		synchronized (lock) {
			lock.notify();
		}
	}
}
