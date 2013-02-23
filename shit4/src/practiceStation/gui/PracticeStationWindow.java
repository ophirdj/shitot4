package practiceStation.gui;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import global.gui.Main_Window;
import global.gui.StationPanel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import practiceStation.logic.IPracticeStation;
import practiceStation.logic.PracticeStationAction;



public class PracticeStationWindow extends StationPanel implements
		IPracticeStationWindow, Runnable {
	final static long serialVersionUID = 1L;

	private PracticeStationAction chosen_action;
	private boolean was_pushed = false;
	private final Color PracticeBackground = new Color(255, 255, 255);
	private IPracticeStation callerStation;
	private final int NUM_OF_ROW = PracticeStationAction.maxRow();
	private int id;
	
	
	public void setAction(PracticeStationAction action) {
		if (!was_pushed) {
			chosen_action = action;
		}
		was_pushed = true;
	}

	public PracticeStationWindow(int id, IPracticeStation caller, Main_Window main_window) {
		super(Messages.practiceStation,id,main_window);
		callerStation = caller;
		callerStation.setLanguage(window.MAINFRAME_LANGUAGE);
		this.id = id;
		(new Thread(this)).start();
	}

	void make_voting_button(JPanel practice_panel, PracticeStationAction action,
			Object lock) {
		JButton action_button = new JButton(action.getString(dictionary));
		action_button.addActionListener(new PracticeClick(this, action, lock));
		practice_panel.add(action_button); 
	}

	private void make_practice_panel(JPanel rows[], Object lock) {
		for (PracticeStationAction action : PracticeStationAction.values()) {
			make_voting_button(rows[action.getRow()], action, lock);
		}
	}

	public void chooseAction() {
		was_pushed = false;
		chosen_action = null;
		JPanel practice_panel = new JPanel(new GridLayout(NUM_OF_ROW,1));
		JPanel rows[] = new JPanel[NUM_OF_ROW];
		for (int i = 0; i < rows.length; i++) {
			rows[i] = new JPanel(new FlowLayout());
			rows[i].setBackground(PracticeBackground);
		}
		make_practice_panel(rows, this);
		this.removeAll();
		practice_panel.setBackground(PracticeBackground);
		this.setBackground(PracticeBackground);
		for (int i = 0; i < rows.length; i++) {
			practice_panel.add(rows[i]);
		}
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
	
	@Override
	public void setLanguage(Languages language) {
		super.setLanguage(language);
		callerStation.setLanguage(language);
		this.getButton().setText(translate(Messages.practiceStation)+id);
	}
}