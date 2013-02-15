package votingStation;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import choosingList.IChoosingList.ChoosingInterruptedException;
import dictionaries.IDictionary.Messages;

import GUI.Main_Window;
import GUI.StationPanel;
import GUI.WaitForClick;

public class VotingStation_window extends StationPanel implements IVotingStationWindow{
	private static final long serialVersionUID = 1L;
	private VotingStationAction chosen_action;
	private boolean was_pushed = false;
	private final Color VotingBackGround = new Color(255,255,255); 
	private IVotingStation callerStation;
	private boolean keepRunning;
	private Main_Window mainWindow;
	
	public void setAction(VotingStationAction action){
		if(!was_pushed){
			chosen_action = action;
		}
		was_pushed = true;
	}
	
	
	  public VotingStation_window(String name, IVotingStation caller, Main_Window mainWindow){
		  super(name, mainWindow);
		  callerStation = caller;
		  this.mainWindow = mainWindow;
	  }
	  
	  void make_voting_button(JPanel voting_panel, VotingStationAction action ,Object lock){
		  JButton action_button = new JButton(action.getString(mainWindow));
		  action_button.addActionListener(new VoteClick(this,action,lock));
		  voting_panel.add(action_button);
	  }
	  
	  private void make_voting_panel(JPanel voting_panel, Object lock){
		  for(VotingStationAction action : VotingStationAction.values()){
			  make_voting_button(voting_panel,action,lock);
		  }
	  }
	  
	  public void chooseAction(){
			was_pushed = false;
			chosen_action = null;
			JPanel voting_panel = new JPanel(new FlowLayout());
			make_voting_panel(voting_panel,this);
			this.removeAll();
			voting_panel.setBackground(VotingBackGround);
			this.setBackground(VotingBackGround);
			this.add(voting_panel);
			window.show_if_current(this,this);
			try{
				synchronized (this) {
					if(keepRunning == false) return;
					this.wait();
				}
			}
			catch(InterruptedException e){}
	  }
	  
	  private void make_input_panel(JPanel input_panel,JTextField comp, Object lock,String name){
		  input_panel.add(comp);
			JButton button = new JButton(name);
			button.addActionListener(new WaitForClick(lock));
			input_panel.add(button);
	  }
	  
	  public String getPassword() throws ChoosingInterruptedException{
		  	JPanel password_panel = new JPanel(new GridLayout(2,1));
		  	JPasswordField textField = new JPasswordField();
		  	make_input_panel(password_panel,textField,this,mainWindow.translate(Messages.enter_password));
			this.removeAll();
			this.add(password_panel);
			window.show_if_current(this,this);
			try{
				synchronized (this) {
					if(keepRunning == false) throw new ChoosingInterruptedException(); 
					this.wait();
					if(keepRunning == false) throw new ChoosingInterruptedException(); 
				}
			}
			catch(InterruptedException e){}
			return new String(textField.getPassword());
	  }
	  
	  public int getID() throws ChoosingInterruptedException{
		  	JPanel id_panel = new JPanel(new GridLayout(2,1));
		  	JTextField textField = new JTextField();
		  	make_input_panel(id_panel,textField,this,mainWindow.translate(Messages.enter_ID));
			this.removeAll();
			this.add(id_panel);
			window.show_if_current(this,this);
			try{
				synchronized (this) {
					if(keepRunning == false) throw new ChoosingInterruptedException();
					this.wait();
					if(keepRunning == false) throw new ChoosingInterruptedException();
				}
			}
			catch(InterruptedException e){}
			
			String id = textField.getText();
			return Integer.parseInt(id);
	  }
	  
		public void run(){
			try {
				while(keepRunning){
					chooseAction();
					if(!keepRunning){
						break;
					}
					chosen_action.activate(callerStation);
				}
			} catch (ChoosingInterruptedException e) {
				printMessage(mainWindow.translate(Messages.You_quit_in_the_process_of_voting));
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
			this.notifyAll();
		}
}
