package unitTests;
import java.util.Iterator;

import mainframe.IMainframe;
import mainframe.IMainframe.VoterDoesNotExist;
import mainframe.IMainframe.VoterStatus;
import partiesList.IPartiesList;
import votingStation.IVotingStation;
import communication.IStationsController;

public class StationsControllerStub implements IStationsController{

	private IMainframe mainframe;
	
	public StationsControllerStub(){
		mainframe = new Driver_Mainframe();
	}
	
	@Override
	public Iterator<IVotingStation> iterator() {
		return null;
	}

	@Override
	public void peep() {
	}

	@Override
	public void initialize(IPartiesList parties) {
	}

	@Override
	public void retire() {		
	}

	@Override
	public VoterStatus getVoterStatus(int id) {
		return mainframe.getVoterStatus(id);
	}

	@Override
	public void markVoted(int id) throws VoterDoesNotExist {
	}

	@Override
	public IPartiesList hotBackup() {
		return null;
	}

}
