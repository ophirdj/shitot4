package votingStation.test;

import java.util.Iterator;

import partiesList.model.IPartiesList;
import votingStation.logic.IVotingStation;
import mainframe.communication.IStationsController;
import mainframe.logic.IMainframe;
import mainframe.logic.IMainframe.VoterDoesNotExist;
import mainframe.logic.IMainframe.VoterStatus;

public class Driver_StationsContoller implements IStationsController{

	private int place = 0;

	@Override
	public void peep() {
		assert(false);
	}

	@Override
	public void markVoted(int id) throws VoterDoesNotExist {
		
	}
	
	@Override
	public VoterStatus getVoterStatus(int id) {
		final IMainframe.VoterStatus statuses[] = {IMainframe.VoterStatus.identified,IMainframe.VoterStatus.identified,
				IMainframe.VoterStatus.unidentified,IMainframe.VoterStatus.voted};
		if(place >= statuses.length){
			place = 0;
		}
		return statuses[place++];
	}

	@Override
	public Iterator<IVotingStation> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialize(IPartiesList parties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void retire() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IPartiesList hotBackup() {
		// TODO Auto-generated method stub
		return null;
	}
}
