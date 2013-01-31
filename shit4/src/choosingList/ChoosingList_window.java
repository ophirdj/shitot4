package choosingList;

import GUI.BasicPanel;
import GUI.Global_Window;
import GUI.Main_Window;

import java.lang.String;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;

import partiesList.IPartiesList;
import partiesList.IParty;
import partiesList.Party;


public class ChoosingList_window extends BasicPanel implements IChoosingWindow {

private static final long serialVersionUID = 23L;
private final Object lock = new Object();
private Main_Window window;  
private IParty current_party;
private ChooseType return_type;
private boolean was_pushed = false;  



private void add_party_button(JPanel panel,String name,ChooseType type,IParty party){
	  JButton button = new JButton(name);
	  button.addActionListener(new ClickParty(type,party,lock,this));
	  panel.add(button);
}

private void make_special_panel(JPanel special_panel, IParty whiteNote){
	  add_party_button(special_panel,"previous parties",ChooseType.Prev,IChoosingList.NO_PARTY);
	  add_party_button(special_panel,"white note",ChooseType.Party,whiteNote);
	  add_party_button(special_panel,"next parties",ChooseType.Next,IChoosingList.NO_PARTY);
}

private void make_parties_panel(JPanel parties_panel,IPartiesList partiesToShow){
	  for (IParty party : partiesToShow) {
		  if(party==null){
			  JButton nullButton = new JButton();
			  parties_panel.add(nullButton);
		  }
		  else{
			  add_party_button(parties_panel,party.getSymbol(),ChooseType.Party,party);
		  }
	  }
}


  public ChoosingList_window(){
    window = Global_Window.main_window;
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
  
  public ChooseType receiveChoiceSymbol(IPartiesList partiesToShow){
	was_pushed = false;
	current_party = null;
	return_type = null;
	
	JPanel choose_panel = new JPanel(new BorderLayout());  
	JPanel parties_panel = new JPanel(new GridLayout(partiesToShow.size(),1));
	JPanel special_panel = new JPanel(new GridLayout(1,2));
	
	make_parties_panel(parties_panel,partiesToShow);
	make_special_panel(special_panel,partiesToShow.getWhiteNoteParty());
	choose_panel.add(parties_panel,BorderLayout.CENTER);
	choose_panel.add(special_panel,BorderLayout.SOUTH);
	this.add(choose_panel);
	window.show_if_current(this);
	
	try{
		synchronized (lock) {
			lock.wait();
		}
	}
	catch(InterruptedException e){}
	remove(choose_panel);
	return return_type;
  }
}