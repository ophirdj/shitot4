package global.gui;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import partiesList.model.IParty;

public interface IWindow{

	/**
	 * Prints on the screen the message with a "yes" and a "no" button, waits for the user to click.
	 * @param confirmationMessage: the message to print
	 * @return true if the user clicks on yes, false if he clicks on no
	 */
	public Boolean getConfirmation(String confirmationMessage);
	
	/**
	 * Prints the error message with a button "ok" and waits for the user to click on it
	 * @param errorMessage: the message to print
	 */
	public void printError(String errorMessage);
	
	/**
	 * Prints the message with a button "ok" and waits for the user to click on it
	 * @param message: the message to print
	 */
	public void printMessage(String message);
	
	/**
	 * change the language of the station to the given language
	 * @param language: the language for the station
	 */
	public void setLanguage(Languages language);
	
	/**
	 * 
	 * @param message; the message
	 * @return string representing the message in the correct language
	 */
	public String translate(Messages message);
	
	/**
	 * display the given message as error message
	 * @param message: message to display
	 */
	void printErrorMessage(Messages message);
	
	/**
	 * display the given message as info message
	 * @param party: the chosen party
	 * @param message: message to display
	 */
	void printInfoMessage(Messages message, IParty party);
	
	/**
	 * display the given message as info message
	 * @param message: message to display
	 */
	void printInfoMessage(Messages message);
	
	/**
	 * display the given message as info message
	 * @param message: message to display
	 * @return the voter choice
	 */
	boolean printConformationMessage(Messages message);
	
	/**
	 * display the given message as info message
	 * @param message: message to display
	 * @param party: the party that was chosen
	 * @return the voter choice
	 */
	boolean printConformationMessage(Messages message, IParty party);
	
	/**
	 * close the window
	 */
	void closeWindow();
}
