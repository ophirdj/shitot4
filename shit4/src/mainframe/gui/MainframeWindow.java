package mainframe.gui;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import global.gui.Main_Window;
import global.gui.StationPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;

import mainframe.logic.IMainframe;
import mainframe.logic.MainframeAction;
import mainframe.logic.MainframeAction.MainframeState;


import partiesList.model.IPartiesList;

/**
 * Implementation of IMainframeWindow
 * @author Ziv Ronen & Ophir De Jager
 *
 */
public class MainframeWindow extends StationPanel implements IMainframeWindow {

	static final long serialVersionUID = 1L;
	private IMainframe callerStation;
	private boolean was_pushed=true;
	private MainframeAction chosen_action;
	private HistogramPanel histogramPanel;
	private TablePanel tablePanel;
	
	private JButton histogramExitButton;
	private JButton tableExitButton;
	
	private JScrollPane histogramScroll;
	private JPanel histogramWraper;
	private JPanel tableWraper;
	
	private MainframeState state = MainframeState.BeforeInit;
	
	private final int NUM_OF_PERSONAL_LAYER = MainframeAction.maxRow();
	private final int NUM_OF_TOTAL_LAYER = MainframeAction.maxRow()+StationPanel.GLOBAL_ROWS_NUM;
	private final Color MainframeBackGround = new Color(255,255,255); 
	
	/**
	 * Create the mainframe window and add it to the main window.
	 * 
	 * @param callerStation: The mainframe that build it and that it related to.
	 * @param main_window: The main window for showing the system (build1 only).
	 */
	public MainframeWindow(IMainframe callerStation, Main_Window main_window) {
		super(Messages.Main_frame,main_window);
		this.callerStation = callerStation;
		new Thread(this).start();
	}
	
	@Override
	public void setState(MainframeState state) {
		this.state = state;
	}
	
	@Override
	public void setDataDisplay(IPartiesList parties) {
		if(histogramWraper == null){
			buildHistogram();
		}
		if(tableWraper == null){
			buildTable();
		}
		histogramPanel.setParties(parties);
		tablePanel.setParties(parties);
	}
	
	/**
	 * Build the histogram panel after first vote count
	 */
	private void buildHistogram() {
		histogramPanel = new HistogramPanel();
		histogramScroll = new JScrollPane(histogramPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		histogramWraper = new JPanel();
		histogramScroll.setLayout(new ScrollPaneLayout());
		histogramWraper.setLayout(new BorderLayout());
		histogramWraper.add(histogramScroll, BorderLayout.CENTER);
		histogramExitButton = new JButton();
		histogramExitButton.addActionListener(new WaitForClick(this));
		histogramWraper.add(histogramExitButton,BorderLayout.SOUTH);
	}

	/**
	 * Build the table panel after first vote count
	 */
	private void buildTable() {
		tablePanel = new TablePanel();
		tableWraper = new JPanel();
		tableExitButton = new JButton();
		tableExitButton.addActionListener(new WaitForClick(this));
		tableWraper.setLayout(new BorderLayout());
		tableWraper.add(tableExitButton,BorderLayout.SOUTH);
		tableWraper.add(tablePanel,BorderLayout.CENTER);
	}
	
	/**
	 * Set the current action to perform to given action.
	 * 
	 * @param action: The action to perform.
	 */
	void setAction(MainframeAction action){
		if(!was_pushed){
			was_pushed=true;
			chosen_action = action;
		}
	}
	
	/**
	 * Create a button in the given panel.
	 * 
	 * @param mainframe_panel: The panel in which we want to add the button.
	 * @param action: The action pressing the button will activate.
	 * @param lock: The lock we need to notify when the button is pressed
	 */
	private void make_mainframe_button(JPanel mainframe_panel, MainframeAction action, Object lock){
		JButton button = new JButton(action.getString(dictionary));
		
		button.addActionListener(new MainframeClick(this,action,lock));
		mainframe_panel.add(button);
	}
	
	/**
	 * Make all the buttons for actions in the mainframe. 
	 * 
	 * @param mainframe_panel: an array of panels, each represent a rows of buttons
	 * @param lock: The lock we need to notify when a button is pressed
	 */
	 private void make_mainframe_panel(JPanel mainframe_panel[], Object lock){
		for(MainframeAction action : MainframeAction.values()){
			if(action.needToShow(state)){
				make_mainframe_button(mainframe_panel[action.getRow()],action,lock);
			}
		}
	  }
	
	/**
	 * Wait for the user to choose an action. 
	 * Show the choosing panel (the mainframe main panel).
	 * After chooseAction, chosen_action will contain either the correct action
	 * or null if no action was chosen.
	 */
	private void chooseAction(){
		was_pushed = false;
		chosen_action = null;
		JPanel mainframe_panel = new JPanel(new GridLayout(NUM_OF_TOTAL_LAYER,1));
		JPanel layers[] = new JPanel[NUM_OF_PERSONAL_LAYER];
		for (int i = 0; i < layers.length; i++) {
			layers[i] = new JPanel(new FlowLayout());
			layers[i].setBackground(MainframeBackGround);
		}
		make_mainframe_panel(layers,this);
		this.removeAll();
		mainframe_panel.setBackground(MainframeBackGround);
		this.setBackground(MainframeBackGround);
		for (int i = 0; i < layers.length; i++) {
			mainframe_panel.add(layers[i]);
		}
		this.addGlobalRows(mainframe_panel, MainframeBackGround);
		this.add(mainframe_panel);
		window.show_if_current(this,this);
		try{
			synchronized (this) {
				this.wait();
			}
		}
		catch(InterruptedException e){}
	}
	
	/**
	 * Make the panel for entering id.
	 * 
	 * @param id_panel: the panel we make.
	 * @param textField: where the text should be entered.
	 * @param lock: the lock we notify after the user finish.
	 * @param message: the message we want to show on the button.
	 */
	private void make_id_panel(JPanel id_panel,JTextField textField, Object lock,Messages message){
		id_panel.add(textField);
		
		JButton button = new JButton(dictionary.translate(message));
		button.addActionListener(new WaitForClick(lock));
		id_panel.add(button);
	}

	/**
	 * Get the id of the user.
	 * 
	 * @return the id that was entered
	 * @throws IllegalIdException if the id is not in the right format
	 * (see javadoc for the IllegalIdException in IMainframeWindow for
	 * more information).
	 */
	public int getID() throws IMainframeWindow.IllegalIdException{
		
		JPanel id_panel = new JPanel(new GridLayout(2,1));
	  	JTextField textField = new JTextField();
	  	make_id_panel(id_panel,textField,this,Messages.enter_ID);
		this.removeAll();
		this.add(id_panel);
		window.show_if_current(this,this);
		try{
			synchronized (this) {
				this.wait();
			}
		}
		catch(InterruptedException e){}
		
		String id = textField.getText();
		if(id.length() != 9) throw new IllegalIdException();
		try{
			int idNum = Integer.parseInt(id);
			if(idNum < 0) throw new IllegalIdException();
			return idNum;
		}catch(NumberFormatException e){
			throw new IllegalIdException();
		}
	}

	@Override
	public void run() {
		while(chosen_action != MainframeAction.shutDown){
			chooseAction();
			if(chosen_action != null) chosen_action.activate(callerStation, this);
		}
	}
	
	@Override
	public void setLanguage(Languages language) {
		super.setLanguage(language);
		this.getButton().setText(translate(Messages.Main_frame));
		window.MAINFRAME_LANGUAGE = language;
	}

	@Override
	public void displayHistogram() {
		histogramExitButton.setText(translate(Messages.Exit));
		histogramScroll.setPreferredSize(histogramScroll.getParent().getSize());
		window.show_if_current(this, histogramWraper);
		synchronized (this) {
			try {
				this.wait();
			} catch (InterruptedException e) {}
		}
		window.show_if_current(this, this);
	}

	@Override
	public void displayTable() {
		tableExitButton.setText(translate(Messages.Exit));
		window.show_if_current(this, tableWraper);
		synchronized (this) {
			try {
				this.wait();
			} catch (InterruptedException e) {}
		}
		window.show_if_current(this, this);
	}

}
