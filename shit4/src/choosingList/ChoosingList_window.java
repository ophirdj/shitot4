package choosingList;

import GUI.BasicPanel;
import GUI.Global_Window;
import GUI.Main_Window;

import java.lang.String;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import choosingList.IChoosingList.ChoosingInterruptedException;

import partiesList.IPartiesList;
import partiesList.IParty;


public class ChoosingList_window extends BasicPanel implements IChoosingWindow {

private static final long serialVersionUID = 23L;
private Main_Window window;  
private IParty current_party;
private ChooseType return_type;
private boolean was_pushed = false;
private boolean keep_running = true;
private JPanel stationPanel;

public static final Color ChoosingBackGroundColor = new Color(255,255,255);


private void add_party_button(JPanel panel,String name,ChooseType type,IParty party,Color color){
	  JButton button = new JButton(name);
	  button.addActionListener(new ClickParty(type,party,stationPanel,this));
	  if(color != null)
		  button.setBackground(color);
	  panel.add(button);
}

private void make_special_panel(JPanel special_panel, IParty whiteNote){
	  add_party_button(special_panel,"previous parties",ChooseType.Prev,IChoosingList.NO_PARTY,null);
	  add_party_button(special_panel,"white note",ChooseType.Party,whiteNote,Color.WHITE);
	  add_party_button(special_panel,"next parties",ChooseType.Next,IChoosingList.NO_PARTY,null);
}

private void make_parties_panel(JPanel parties_panel,IPartiesList partiesToShow){
	  for (IParty party : partiesToShow) {
		  if(party==null){
			  JButton nullButton = new JButton();
			  parties_panel.add(nullButton);
		  }
		  else{
			  add_party_button(parties_panel,party.getSymbol(),ChooseType.Party,party,null);
		  }
	  }
}


  public ChoosingList_window(JPanel stationPanel){
    window = Global_Window.main_window;
    this.stationPanel = stationPanel;
  }
  
  public void setResult(IParty choosen_party, ChooseType type){
	  if(!was_pushed){
		  current_party = choosen_party;
	  }
	  was_pushed = true;
	  return_type = type;
  }
  
  public IParty getParty(){
	  return current_party;
  }
  
  public ChooseType receiveChoiceSymbol(IPartiesList partiesToShow) throws ChoosingInterruptedException{
	was_pushed = false;
	current_party = null;
	return_type = null;
	
	JPanel choose_panel = new JPanel(new BorderLayout(30,30));
	choose_panel.setBackground(ChoosingBackGroundColor);
	JPanel parties_panel = new JPanel(new GridLayout(3,3,30,15));
	parties_panel.setBackground(ChoosingBackGroundColor);
	JPanel special_panel = new JPanel(new GridLayout(1,3,20,10));
	special_panel.setBackground(ChoosingBackGroundColor);
	
	make_parties_panel(parties_panel,partiesToShow);
	make_special_panel(special_panel,partiesToShow.getWhiteNoteParty());
	choose_panel.add(parties_panel,BorderLayout.CENTER);
	choose_panel.add(special_panel,BorderLayout.SOUTH);
	this.add(choose_panel);
	window.show_if_current(stationPanel, this);
	
	try{
		synchronized (stationPanel) {
			if(keep_running == false) throw new ChoosingInterruptedException(); 
			stationPanel.wait();
			if(keep_running == false) throw new ChoosingInterruptedException(); 
		}
	}
	catch(InterruptedException e){}
	remove(choose_panel);
	return return_type;
  }

@Override
public void switchOn() {
	synchronized(stationPanel){
		keep_running = true;
	}
	//if(switchFrom != null)
	window.show_if_current(stationPanel, this);
}

@Override
public void switchOff() {
	//if(switchTo != null)
	window.show_if_current(stationPanel, stationPanel);
}

@Override
public void closeWindow() {
	synchronized (stationPanel) {
		keep_running = false;
		stationPanel.notify();
	}
}

}