package practiceStation.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import practiceStation.logic.PracticeStationAction;

/**
 * Handle clicks in practice station window
 * @author Ophir De Jager
 *
 */
public class PracticeClick implements ActionListener {
	private PracticeStationWindow button_window;
	private PracticeStationAction practice_action;
	private Object lock;
	
	/**
	 * Create new listener to practice station window clicks
	 * @param station related practice station
	 * @param action action to be performed on click
	 * @param window_lock lock to sync actions
	 */
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
