package choosingList.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import choosingList.logic.ChooseType;



import partiesList.model.IParty;


/**
 * Listen to the buttons in choosing list.
 * When pushed, inform the window on what was pushed
 * and signal it to continue.
 */
public class ClickParty implements ActionListener{
	private ChooseType return_type;
	private IParty button_party;
	private ChoosingListWindow original_window;
	private Object lock;
	
	public ClickParty(ChooseType type, IParty given_party, 
						Object window_lock, ChoosingListWindow caller) {
		button_party = given_party;
		lock = window_lock;
		original_window = caller;
		return_type = type;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		synchronized (lock) {
			lock.notify();
		}
		original_window.setResult(button_party,return_type);
	}
	
}
