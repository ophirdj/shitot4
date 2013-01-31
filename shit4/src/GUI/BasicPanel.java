package GUI;


import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public abstract class BasicPanel extends JPanel {
	
	public BasicPanel() {
		setMinimumSize(new Dimension(500,300));
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
		  Global_Window.main_window.remove_panel(this);
	  }
}
