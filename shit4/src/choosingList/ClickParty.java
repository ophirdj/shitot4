package choosingList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import partiesList.IParty;



public class ClickParty implements ActionListener{
	private ChooseType return_type;
	private IParty button_party;
	private ChoosingList_window original_window;
	private Object lock;
	
	public ClickParty(ChooseType type, IParty given_party, 
						Object window_lock, ChoosingList_window caller) {
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
