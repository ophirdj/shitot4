package votingStation;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import choosingList.IChoosingList.ChoosingInterruptedException;

import GUI.StationPanel;
import GUI.WaitForClick;
import GUI.Global_Window;
import GUI.Main_Window;

public class VotingStation_window extends StationPanel implements IVotingStationWindow{
	private static final long serialVersionUID = 1L;
	private Main_Window window;  
	private VotingStationAction chosen_action;
	private boolean was_pushed = false;
	private final Color VotingBackGround = new Color(255,255,255); 
	private IVotingStation callerStation;
	private boolean keepRunning;
	
	public void setAction(VotingStationAction action){
		if(!was_pushed){
			chosen_action = action;
		}
		was_pushed = true;
	}
	
	
	  public VotingStation_window(String name, IVotingStation caller){
		  super(name);
		  window = Global_Window.main_window;
		  callerStation = caller;
	  }
	  
	  private void make_voting_panel(JPanel voting_panel, Object lock){
		  JButton vote_button = new JButton("make vote");
		  vote_button.addActionListener(new VoteClick(this,false,lock));
		  voting_panel.add(vote_button);
		  JButton test_button = new JButton("test vote");
		  test_button.addActionListener(new VoteClick(this,true,lock));
		  voting_panel.add(test_button);
	  }
	  
	  public VotingStationAction chooseAction(){
			was_pushed = false;
			JPanel voting_panel = new JPanel(new FlowLayout());
			make_voting_panel(voting_panel,this);
			this.removeAll();
			voting_panel.setBackground(VotingBackGround);
			this.setBackground(VotingBackGround);
			this.add(voting_panel);
			window.show_if_current(this);
			try{
				synchronized (this) {
					this.wait();
				}
			}
			catch(InterruptedException e){}
			return chosen_action;
	  }
	  
	  private void make_input_panel(JPanel password_panel,JPasswordField passField, Object lock,String name){
		  	password_panel.add(passField);
			JButton button = new JButton(name);
			button.addActionListener(new WaitForClick(lock));
			password_panel.add(button);
	  }
	  
	  public String getPassword() throws ChoosingInterruptedException{
		  	JPanel password_panel = new JPanel(new GridLayout(2,1));
		  	JPasswordField textField = new JPasswordField();
		  	make_input_panel(password_panel,textField,this,"enter password");
			this.removeAll();
			this.add(password_panel);
			window.show_if_current(this);
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
		  	JPasswordField textField = new JPasswordField();
		  	make_input_panel(id_panel,textField,this,"enter ID");
			this.removeAll();
			this.add(id_panel);
			window.show_if_current(this);
			try{
				synchronized (this) {
					if(!keepRunning) throw new ChoosingInterruptedException();
					this.wait();
					if(keepRunning == false) throw new ChoosingInterruptedException();
				}
			}
			catch(InterruptedException e){}
			
			String id = new String(textField.getPassword());
			return Integer.parseInt(id);
	  }
	  
		public void run(){
			try {
				while(keepRunning){
					VotingStationAction choose = chooseAction();
					if(!keepRunning){
						break;
					}
					switch (choose) {
					case VOTING:
						callerStation.voting();
						break;
					case TEST_VOTE:
						callerStation.testVoting();
						break;
					default:
						break;
					}
				}
			} catch (ChoosingInterruptedException e) {
				printMessage("You quit in the process of voting");
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
