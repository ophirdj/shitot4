package votingStation.test;
import java.util.Iterator;

import mainframe.communication.IStationsController;
import mainframe.logic.IMainframe;
import mainframe.logic.IMainframe.VoterDoesNotExist;
import mainframe.logic.IMainframe.VoterStatus;
import partiesList.model.IPartiesList;
import votingStation.logic.IVotingStation;

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
