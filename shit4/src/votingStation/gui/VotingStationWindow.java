package votingStation.gui;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import global.gui.Main_Window;
import global.gui.StationPanel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import choosingList.logic.IChoosingList.ChoosingInterruptedException;

import votingStation.logic.IVotingStation;
import votingStation.logic.VotingStationAction;


/**
 * The window of a voting station
 * @author Ziv Ronen
 *
 */
public class VotingStationWindow extends StationPanel implements
		IVotingStationWindow {
	private static final long serialVersionUID = 1L;
	private VotingStationAction chosen_action;
	private boolean was_pushed = false;
	private final Color VotingBackGround = new Color(255, 255, 255);
	private IVotingStation callerStation;
	private boolean keepRunning;

	private boolean was_called = false;

	private static final int NUM_OF_PERSONAL_ROWS = VotingStationAction.maxRow();
	private static final int NUM_OF_TOTAL_ROWS = NUM_OF_PERSONAL_ROWS + StationPanel.GLOBAL_ROWS_NUM;
	
	private int id;
	
	
	
	/**
	 * Create a new window for voting station
	 * @param id: station (and window) ID
	 * @param caller: related voting station
	 * @param main_window: window were this window will show in
	 */
	public VotingStationWindow(int id, IVotingStation caller,
			Main_Window main_window) {
		super(Messages.voting_station,id, main_window);
		callerStation = caller;
		this.id = id;
	}
	
	
	/**
	 * Set action to be performed to <action>
	 * @param action: action to be performed
	 */
	public void setAction(VotingStationAction action) {
		if (!was_pushed) {
			chosen_action = action;
		}
		was_pushed = true;
	}

	/**
	 * Set was_called flag
	 */
	public void call() {
		was_called = true;
	}

	/**
	 * Create a button in the given panel.
	 * 
	 * @param voting_panel: The panel in which we want to add the button.
	 * @param action: The action pressing the button will activate.
	 * @param lock: The lock we need to notify when the button is pressed
	 */
	private void make_voting_button(JPanel voting_panel, VotingStationAction action,
			Object lock) {
		JButton action_button = new JButton(action.getString(dictionary));
		action_button.addActionListener(new VoteClick(this, action, lock));
		voting_panel.add(action_button);
	}

	/**
	 * Make all the buttons for actions in the voting station. 
	 * 
	 * @param rows: an array of panels, each represent a rows of buttons
	 * @param lock: The lock we need to notify when a button is pressed
	 */
	private void make_voting_panel(JPanel rows[], Object lock) {
		for (VotingStationAction action : VotingStationAction.values()) {
			make_voting_button(rows[action.getRow()], action, lock);
		}
	}

	/**
	 * Wait for the user to choose an action. 
	 * Show the choosing panel (the voting station main panel).
	 * After chooseAction, chosen_action will contain either the correct action
	 * or null if no action was chosen.
	 */
	public synchronized void chooseAction() {
		was_pushed = false;
		chosen_action = null;
		JPanel voting_panel = new JPanel(new GridLayout(NUM_OF_TOTAL_ROWS,1));
		JPanel rows[] = new JPanel[NUM_OF_PERSONAL_ROWS];
		for (int i = 0; i < rows.length; i++) {
			rows[i] = new JPanel(new FlowLayout());
			rows[i].setBackground(VotingBackGround);
		}
		make_voting_panel(rows, this);
		this.removeAll();
		voting_panel.setBackground(VotingBackGround);
		this.setBackground(VotingBackGround);
		for (int i = 0; i < rows.length; i++) {
			voting_panel.add(rows[i]);
		}
		this.addGlobalRows(voting_panel, VotingBackGround);
		this.add(voting_panel);
		window.show_if_current(this, this);
		try {
			if (keepRunning == false)
				return;
			this.wait();
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Make the panel for entering id/password.
	 * 
	 * @param input_panel: the panel we make.
	 * @param textField: where the text should be entered.
	 * @param lock: the lock we notify after the user finish.
	 * @param message: the message we want to show on the button (its name).
	 */
	private void make_input_panel(JPanel input_panel, JTextField textField,
			Object lock, Messages name) {
		input_panel.add(textField);
		JButton button = new JButton(dictionary.translate(name));
		button.addActionListener(new VotingWaitForAction(lock, this));
		input_panel.add(button);
	}

	@Override
	public String getPassword() throws ChoosingInterruptedException {
		was_called = false;
		JPanel password_panel = new JPanel(new GridLayout(2, 1));
		JPasswordField textField = new JPasswordField();
		make_input_panel(password_panel, textField, this, Messages.enter_password);
		this.removeAll();
		this.add(password_panel);
		window.show_if_current(this, this);
		try {
			synchronized (this) {
				while (!was_called) {
					if (keepRunning == false)
						throw new ChoosingInterruptedException();
					this.wait();
				}
			}
		} catch (InterruptedException e) {
		}
		return new String(textField.getPassword());
	}

	@Override
	public int getID() throws ChoosingInterruptedException, IllegalIdException {
		was_called = false;
		JPanel id_panel = new JPanel(new GridLayout(2, 1));
		JTextField textField = new JTextField();
		make_input_panel(id_panel, textField, this, Messages.enter_ID);
		this.removeAll();
		this.add(id_panel);
		window.show_if_current(this, this);
		try {
			synchronized (this) {
				while (!was_called) {
					if (keepRunning == false)
						throw new ChoosingInterruptedException();
					this.wait();
				}
			}
		} catch (InterruptedException e) {
		}

		String id = textField.getText();
		if(id.length() != 9){
			throw new IVotingStationWindow.IllegalIdException();
		}
		try{
			int numId = Integer.parseInt(id);
			if(numId < 0){
				throw new IVotingStationWindow.IllegalIdException();
			}
			return numId;
		}catch(NumberFormatException e){
			throw new IVotingStationWindow.IllegalIdException();
		}
	}

	@Override
	public void run() {
		try {
			while (keepRunning) {
				chooseAction();
				if(chosen_action != null) chosen_action.activate(callerStation,this);
			}
		} catch (ChoosingInterruptedException e) {
			printInfoMessage(Messages.You_quit_in_the_process_of_voting);
		}
		closeWindow();
	}

	@Override
	public void startLoop() {
		keepRunning = true;
		new Thread(this).start();
	}

	@Override
	public synchronized void endLoop() {
		keepRunning = false;
		this.notify();
	}
	
	@Override
	public void setLanguage(Languages language) {
		super.setLanguage(language);
		getButton().setText(translate(Messages.voting_station)+id);
	}
}
