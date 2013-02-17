package practiceStation;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import GUI.StationPanel;

public class PracticeStationWindow extends StationPanel implements
		IPracticeStationWindow, Runnable {
	final static long serialVersionUID = 1L;

	private PracticeStationAction chosen_action;
	private boolean was_pushed = false;
	private final Color VotingBackGround = new Color(255, 255, 255);
	private IPracticeStation callerStation;

	public void setAction(PracticeStationAction action) {
		if (!was_pushed) {
			chosen_action = action;
		}
		was_pushed = true;
	}

	public PracticeStationWindow(String name, IPracticeStation caller) {
		super(name);
		callerStation = caller;
		(new Thread(this)).start();
	}

	void make_voting_button(JPanel voting_panel, PracticeStationAction action,
			Object lock) {
		JButton action_button = new JButton(action.toString());
		action_button.addActionListener(new PracticeClick(this, action, lock));
		voting_panel.add(action_button);
	}

	private void make_practice_panel(JPanel voting_panel, Object lock) {
		for (PracticeStationAction action : PracticeStationAction.values()) {
			make_voting_button(voting_panel, action, lock);
		}
	}

	public void chooseAction() {
		was_pushed = false;
		chosen_action = null;
		JPanel practice_panel = new JPanel(new FlowLayout());
		make_practice_panel(practice_panel, this);
		this.removeAll();
		practice_panel.setBackground(VotingBackGround);
		this.setBackground(VotingBackGround);
		this.add(practice_panel);
		window.show_if_current(this, this);
		try {
			synchronized (this) {
				this.wait();
			}
		} catch (InterruptedException e) {
		}
	}

	public void run() {
		while (chosen_action != PracticeStationAction.shut_down) {
			chooseAction();
			chosen_action.activate(callerStation, this);
		}
	}
}
