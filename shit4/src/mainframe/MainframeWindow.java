package mainframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;

import GUI.StationPanel;
import GUI.View;
import GUI.WaitForClick;
import partiesList.IPartiesList;

public class MainframeWindow extends StationPanel implements IMainframeWindow {

	static final long serialVersionUID = 1L;
	private IMainframe callerStation;
	private boolean was_pushed=true;
	private MainframeAction chosen_action;
	private HistogramPanel histogramPanel;
	private JScrollPane histogramScroll;
	private JPanel histogramWraper;
	private TablePanel tablePanel;
	
	private final int NUM_OF_LAYER = MainframeAction.maxRow();
	private final Color MainframeBackGround = new Color(255,255,255); 
	
	public MainframeWindow(IMainframe callerStation) {
		super("Main Frame");
		this.callerStation = callerStation;
		
	}
	
	@Override
	public void init() {
		new Thread(this).start();
	}

	@Override
	public void showHistogram(IPartiesList parties) {
		if(histogramPanel == null){
			histogramPanel = new HistogramPanel();
			histogramScroll = new JScrollPane(histogramPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			histogramWraper = new JPanel();
			histogramScroll.setLayout(new ScrollPaneLayout());
			histogramWraper.setLayout(new BorderLayout());
			histogramWraper.add(histogramScroll, BorderLayout.CENTER);
			window.add_button(new View("histogram"), histogramWraper);
		}
		histogramScroll.setPreferredSize(histogramScroll.getParent().getSize());
		histogramPanel.showHistogram(parties);
		window.show_if_current(histogramWraper,histogramWraper);
	}

	@Override
	public void showTable(IPartiesList parties) {
		
		if(tablePanel == null){
			tablePanel = new TablePanel();
			window.add_button(new View("table"), tablePanel);
		}
		tablePanel.showTable(parties);
		window.show_if_current(tablePanel,tablePanel);
	}
	
	void setAction(MainframeAction action){
		if(!was_pushed){
			was_pushed=true;
			chosen_action = action;
		}
	}
	
	private void make_mainframe_button(JPanel mainframe_panel, MainframeAction action, Object lock){
		JButton button = new JButton(action.toString());
		button.addActionListener(new MainframeClick(this,action,lock));
		mainframe_panel.add(button);
	}
	
	 private void make_mainframe_panel(JPanel mainframe_panel[], Object lock){
		for(MainframeAction action : MainframeAction.values()){
			make_mainframe_button(mainframe_panel[action.getRow()],action,lock);
		}
	  }
	
	private void chooseAction(){
		was_pushed = false;
		chosen_action = null;
		JPanel mainframe_panel = new JPanel(new GridLayout(NUM_OF_LAYER,1));
		JPanel layers[] = new JPanel[NUM_OF_LAYER];
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
		this.add(mainframe_panel);
		window.show_if_current(this,this);
		try{
			synchronized (this) {
				this.wait();
			}
		}
		catch(InterruptedException e){}
	}
	
	private void make_id_panel(JPanel id_panel,JTextField textField, Object lock,String name){
		id_panel.add(textField);
		JButton button = new JButton(name);
		button.addActionListener(new WaitForClick(lock));
		id_panel.add(button);
	}

	public int getID(){
		
		JPanel id_panel = new JPanel(new GridLayout(2,1));
	  	JTextField textField = new JTextField();
	  	make_id_panel(id_panel,textField,this,"enter ID");
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
		return Integer.parseInt(id);
	}

	@Override
	public void run() {
		while(chosen_action != MainframeAction.shutDown){
			chooseAction();
			chosen_action.activate(callerStation, this);
		}
	}

}
