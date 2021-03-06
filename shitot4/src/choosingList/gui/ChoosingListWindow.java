package choosingList.gui;

import global.dictionaries.Messages;
import global.gui.BasicPanel;
import global.gui.Main_Window;
import global.gui.StationPanel;

import java.lang.String;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import choosingList.logic.ChooseType;
import choosingList.logic.IChoosingList;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;



import partiesList.model.IPartiesList;
import partiesList.model.IParty;


public class ChoosingListWindow extends BasicPanel implements IChoosingWindow {

private static final long serialVersionUID = 23L;
private IParty current_party;
private ChooseType return_type;
private boolean was_pushed = false;
private boolean keep_running = true;
private StationPanel stationPanel;

public static final Color ChoosingBackGroundColor = new Color(255,255,255);

/**
 * Add a button that represent a choice for an action in the choosing list panel.
 * 
 * @param panel The panel to whom the button should be added.
 * @param name The text on the button.
 * @param type The button type (next, previous or party).
 * @param party The party (should be null if type!=party)
 * @param color The color for the button.
 */
private void add_party_button(JPanel panel,String name,ChooseType type,IParty party,Color color){
	JButton button = new JButton(name);
	  button.addActionListener(new ClickParty(type,party,stationPanel,this));
	  if(color != null)
		  button.setBackground(color);
	  panel.add(button);
}

/**
 * Add the three special button (previous, white note, next) to the given panel.
 * 
 * @param special_panel The given panel.
 * @param whiteNote The IParty that represent "white note". 
 */
private void make_special_panel(JPanel special_panel, IParty whiteNote){
	special_panel.setLayout(new GridLayout(1,3,20,10));
	add_party_button(special_panel,translate(Messages.previous),ChooseType.Prev,IChoosingList.NO_PARTY,null);
	add_party_button(special_panel,translate(Messages.white_note),ChooseType.Party,whiteNote,Color.WHITE);
	add_party_button(special_panel,translate(Messages.next),ChooseType.Next,IChoosingList.NO_PARTY,null);
}

/**
 * Add all the needed buttons to the given panel. 
 * 
 * @param parties_panel
 * @param partiesToShow
 */
private void make_parties_panel(JPanel parties_panel,IPartiesList partiesToShow){
	parties_panel.setLayout(new GridLayout(3,3,30,15));
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

/**
 * Make The choosing panel (choosing window main panel).
 * The panel include up to 9 buttons for party choosing,
 * Buttons to move back and forward in the parties,
 * and an option to choose white note (no party).
 * 
 * @param partiesToShow as much as 9 parties we want to show. 
 * @return A panel that enable the user to choose all the options mention above.
 */
private JPanel makeChoosingPanel(IPartiesList partiesToShow) {
	JPanel choose_panel = new JPanel(new BorderLayout(30,30));
	choose_panel.setBackground(ChoosingBackGroundColor);
	JPanel parties_panel = new JPanel();
	parties_panel.setBackground(ChoosingBackGroundColor);
	JPanel special_panel = new JPanel();
	special_panel.setBackground(ChoosingBackGroundColor);
	
	make_parties_panel(parties_panel,partiesToShow);
	make_special_panel(special_panel,partiesToShow.getWhiteNoteParty());
	choose_panel.add(parties_panel,BorderLayout.CENTER);
	choose_panel.add(special_panel,BorderLayout.SOUTH);
	return choose_panel;
}


  public ChoosingListWindow(StationPanel stationPanel, Main_Window window){
	super(window);
    this.stationPanel = stationPanel;
  }
  
  /**
   * Inform the window on the chosen action.
   * 
   * @param choosen_party The party that was chosen (if any).
   * @param type The action type (Party, Next or Previous).
   */
  public void setResult(IParty choosen_party, ChooseType type){
	  if(!was_pushed){
		  current_party = choosen_party;
		  return_type = type;
	  }
	  was_pushed = true;
  }
  
  @Override
  public IParty getParty(){
	  return current_party;
  }
  
  @Override
  public ChooseType receiveChoiceSymbol(IPartiesList partiesToShow) throws ChoosingInterruptedException{
	was_pushed = false;
	current_party = null;
	return_type = null;
	
	JPanel choose_panel = makeChoosingPanel(partiesToShow);
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
	window.show_if_current(stationPanel, this);
}

@Override
public void switchOff() {
	window.show_if_current(stationPanel, stationPanel);
}

@Override
public void closeWindow() {
	synchronized (stationPanel) {
		keep_running = false;
		stationPanel.notify();
	}
}

@Override
public String translate(Messages message){
	return stationPanel.translate(message);
}

}