package unitTests;

import GUI.StationPanel;
import votingStation.IVotingStationWindow;

public class VotingStationWindowStub extends StationPanel implements IVotingStationWindow{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int i;
	private static String message;

	public VotingStationWindowStub(String name) {
		super(name);
		closeWindow();
	}

	@Override
	public Boolean getConfirmation(String confirmationMessage) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startLoop() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		
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
}
