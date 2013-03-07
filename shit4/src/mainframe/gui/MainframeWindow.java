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
	
	private JScrollPane histogramScroll;
	private StationPanel histogramWraper;
	private StationPanel tableWraper;
	
	private boolean was_init = false;
	
	private final int NUM_OF_PERSONAL_LAYER = MainframeAction.maxRow();
	private final int NUM_OF_TOTAL_LAYER = MainframeAction.maxRow()+StationPanel.GLOBAL_ROWS_NUM;
	private final Color MainframeBackGround = new Color(255,255,255); 
	
	public MainframeWindow(IMainframe callerStation, Main_Window main_window) {
		super(Messages.Main_frame,main_window);
		this.callerStation = callerStation;
		new Thread(this).start();
	}
	
	@Override
	public void init() {
		was_init = true;
	}

	@Override
	public void showHistogram(IPartiesList parties) {
		if(histogramWraper == null){
			histogramPanel = new HistogramPanel();
			histogramScroll = new JScrollPane(histogramPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			histogramWraper = new StationPanel(Messages.histogram,window);
			histogramScroll.setLayout(new ScrollPaneLayout());
			histogramWraper.setLayout(new BorderLayout());
			histogramWraper.add(histogramScroll, BorderLayout.CENTER);
		}
		histogramScroll.setPreferredSize(histogramScroll.getParent().getSize());
		histogramPanel.showHistogram(parties);
		window.show_if_current(histogramWraper,histogramWraper);
	}

	@Override
	public void showTable(IPartiesList parties) {
		
		if(tableWraper == null){
			tablePanel = new TablePanel();
			tableWraper = new StationPanel(Messages.table,window);
		}
		tablePanel.showTable(parties);
		window.show_if_current(tableWraper,tablePanel);
	}
	
	void setAction(MainframeAction action){
		if(!was_pushed){
			was_pushed=true;
			chosen_action = action;
		}
	}
	
	private void make_mainframe_button(JPanel mainframe_panel, MainframeAction action, Object lock){
		JButton button = new JButton(action.getString(dictionary));
		
		button.addActionListener(new MainframeClick(this,action,lock));
		mainframe_panel.add(button);
	}
	
	 private void make_mainframe_panel(JPanel mainframe_panel[], Object lock){
		for(MainframeAction action : MainframeAction.values()){
			if(was_init && action.isAfterInit() || !was_init && action.isBeforeInit()){
				make_mainframe_button(mainframe_panel[action.getRow(was_init)],action,lock);
			}
		}
	  }
	
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
	
	private void make_id_panel(JPanel id_panel,JTextField textField, Object lock,Messages message){
		id_panel.add(textField);
		
		JButton button = new JButton(dictionary.translate(message));
		button.addActionListener(new WaitForClick(lock));
		id_panel.add(button);
	}

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
	public void closeWindow() {
		if(histogramWraper != null) window.remove_panel(histogramWraper);
		if(tableWraper != null) window.remove_panel(tableWraper);
		super.closeWindow();
	}
	
	@Override
	public void setLanguage(Languages language) {
		super.setLanguage(language);
		this.getButton().setText(translate(Messages.Main_frame));
		if(histogramWraper!=null) histogramWraper.getButton().setText(translate(Messages.histogram));
		if(tableWraper!=null) tableWraper.getButton().setText(translate(Messages.table));
		window.MAINFRAME_LANGUAGE = language;
	}

}
