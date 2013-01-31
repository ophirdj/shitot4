package GUI;

public interface IWindow{
	//Prints on the screen the message with a "yes" and a "no" button, waits for the user to click
	//returns true if the user clicks on yes, false if he clicks on no
	public Boolean getConfirmation(String confirmationMessage);
	
	//Prints the error message with a button "ok" and waits for the user to click on it
	public void printError(String errorMessage);
	
	//Prints the message with a button "ok" and waits for the user to click on it
	public void printMessage(String message);
	
	//close the window
	public void closeWindow();
}
