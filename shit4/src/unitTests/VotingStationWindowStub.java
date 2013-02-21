package unitTests;

import javax.swing.JPanel;

import partiesList.IParty;

import dictionaries.Languages;
import dictionaries.Messages;

import GUI.Main_Window;
import GUI.StationPanel;
import votingStation.IVotingStationWindow;

public class VotingStationWindowStub extends JPanel implements IVotingStationWindow{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int i;
	private static String message;

	@Override
	public Boolean getConfirmation(String confirmationMessage) {
		return null;
	}

	@Override
	public void printError(String errorMessage){
		if (errorMessage.equals("You can't change your vote anymore"))
				throw new UnchangeableVoteError();
		if (errorMessage.equals("You need to identify yourself in the mainframe"))
				throw new UnidentifiedError();
		if (errorMessage.equals("Error: wrong password"))
				throw new WrongPasswordError();
		if (errorMessage.equals("You can't vote here"))
				throw new WrongStationError();
	}

	@Override
	public void printMessage(String message) {
		setMessage(message);
	}

	@Override
	public void closeWindow() {
	}

	@Override
	public void run() {
	}

	@Override
	public void startLoop() {
	}

	@Override
	public int getID(){
		return 1;
	}

	@Override
	public String getPassword(){
		return "password"+i;
	}

	@Override
	public void endLoop() {		
	}
	public static int get_i(){
		return i;
	}
	public static void set_i(int i){
		VotingStationWindowStub.i=i;
	}
	private void setMessage(String message){
		VotingStationWindowStub.message = message;
	}
	public static String getMessage(){
		return message;
	}

	@Override
	public void setLanguage(Languages language) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String translate(Messages message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printErrorMessage(Messages message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printInfoMessage(Messages message, IParty party) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printInfoMessage(Messages message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean printConformationMessage(Messages message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean printConformationMessage(Messages message, IParty party) {
		// TODO Auto-generated method stub
		return false;
	}
}
