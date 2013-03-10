package mainframe.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handle buttons click inside the mainframe.
 * Notify the mainframe when clicked. 
 */
public class WaitForClick implements ActionListener{
	private Object lock;
	private boolean was_pushed;
	
	public WaitForClick(Object window_lock) {
		lock = window_lock;
		was_pushed = false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		synchronized (lock) {
			lock.notify();
		}
		was_pushed = true;
	}
	
	public boolean was_pusehd(){
		return was_pushed;
	}
	
}