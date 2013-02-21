package GUI;


import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public abstract class BasicPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	protected Main_Window window;
	
	public BasicPanel(Main_Window window) {
		setLayout(new GridBagLayout());
		setBackground(Color.WHITE);
		this.window = window;
	}
	
	public Boolean getConfirmation(String confirmationMessage){
		  int n= JOptionPane.showConfirmDialog(this,
				  	confirmationMessage,
		            "Ask for conformation",
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE,
		            null);
		  if(n==JOptionPane.YES_OPTION)
			  return true;
		  else
			  return false;
	  }
	  
	  public void printError(String errorMessage) {
		  JOptionPane.showMessageDialog(this, errorMessage, "ERROR!", JOptionPane.ERROR_MESSAGE);
	  }
	  
	  public void printMessage(String message) {
		  JOptionPane.showMessageDialog(this, message, "FYI", JOptionPane.INFORMATION_MESSAGE);
	  }
	  
	  public void closeWindow() {
		  window.remove_panel(this);
	  }
}
