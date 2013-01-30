
public interface IWindow {
	
	//Prints on the screen the message with a "yes" and a "no" button, waits for the user to click
	//returns true if the user clicks on yes, false if he clicks on no
	public Boolean getConfirmation(String confirmationMessage);
	
	//Prints the error message with a button "ok" and waits for the user to click on it
	public void printError(String errorMessage);
	
	//Prints a party name and symbol on the screen
	public void printParty(IParty party);
	
	//Erase all what is on the screen but the next button
	public void eraseAll();
		
	//Called when a parties list appears on the screen, waits for the user to click 
	//on one of the parties, in which case it returns the symbol of the chosen party
	//or on the "next" button, in which case it returns a certain predefined symbol
	public String receiveChoiceSymbol();
}
