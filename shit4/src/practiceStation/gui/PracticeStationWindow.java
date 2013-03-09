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


/**
 * Implements IPracticeStationWindow
 * @author Ziv Ronen
 *
 */
public class PracticeStationWindow extends IPracticeStationWindow implements Runnable {
	final static long serialVersionUID = 1L;

	private PracticeStationAction chosen_action;
	private boolean was_pushed = false;
	private final Color PracticeBackground = new Color(255, 255, 255);
	private IPracticeStation callerStation;
	private final int NUM_OF_PERSONAL_ROWS = PracticeStationAction.maxRow();
	private final int NUM_OF_TOTAL_ROWS = NUM_OF_PERSONAL_ROWS + StationPanel.GLOBAL_ROWS_NUM;
	private int id;

	/**
	 * Build the station.
	 * 
	 * @param id if we have more then one practice station, this is there id.
	 * @param caller the caller station
	 * @param main_window The main window in which the station is showed.
	 */
	public PracticeStationWindow(int id, IPracticeStation caller, Main_Window main_window) {
		super(Messages.practiceStation,id,main_window);
		callerStation = caller;
		callerStation.setLanguage(window.MAINFRAME_LANGUAGE);
		this.id = id;
		(new Thread(this)).start();
	}
	
	/**
	 * Change the chosen action to the given action
	 * @param action The given action
	 */
	public void setAction(PracticeStationAction action) {
		if (!was_pushed) {
			chosen_action = action;
		}
		was_pushed = true;
	}

	/**
	 * Create a button in the given panel.
	 * 
	 * @param practice_panel The panel in which we want to add the button.
	 * @param action The action pressing the button will activate.
	 * @param lock The lock we need to notify when the button is pressed
	 */
	private void make_voting_button(JPanel practice_panel, PracticeStationAction action,
			Object lock) {
		JButton action_button = new JButton(action.getString(dictionary));
		action_button.addActionListener(new PracticeClick(this, action, lock));
		practice_panel.add(action_button); 
	}

	/**
	 * Make all the buttons for actions in the practice station. 
	 * 
	 * @param rows an array of panels, each represent a rows of buttons
	 * @param lock The lock we need to notify when a button is pressed
	 */
	private void make_practice_panel(JPanel rows[], Object lock) {
		for (PracticeStationAction action : PracticeStationAction.values()) {
			make_voting_button(rows[action.getRow()], action, lock);
		}
	}

	/**
	 * Wait for the user to choose an action. 
	 * Show the choosing panel (the practice station main panel).
	 * After chooseAction, chosen_action will contain either the correct action
	 * or null if no action was chosen.
	 */
	private void chooseAction() {
		was_pushed = false;
		chosen_action = null;
		showPracticePanel();
		try {
			synchronized (this) {
				this.wait();
			}
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Show the practice main panel 
	 */
	private void showPracticePanel() {
		JPanel practice_panel = new JPanel(new GridLayout(NUM_OF_TOTAL_ROWS,1));
		JPanel rows[] = new JPanel[NUM_OF_PERSONAL_ROWS];
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
		this.addGlobalRows(practice_panel, PracticeBackground);
		this.add(practice_panel);
		window.show_if_current(this, this);
	}

	@Override
	public void run() {
		while (chosen_action != PracticeStationAction.shut_down) {
			chooseAction();
			if(chosen_action != null) chosen_action.activate(callerStation, this);
		}
	}
	
	@Override
	public void setLanguage(Languages language) {
		super.setLanguage(language);
		callerStation.setLanguage(language);
		this.getButton().setText(translate(Messages.practiceStation)+id);
	}
}
