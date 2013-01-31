package votingStation;

import mainframe.IMainframe;

public class Driver_Mainframe implements IMainframe{

	private int place = 0;
	
	@Override
	public void initialize() {
		assert(false);
	}

	@Override
	public void restore() {
		assert(false);
	}

	@Override
	public void countVotes() {
		assert(false);
	}

	@Override
	public void shutDown() {
		assert(false);
	}

	@Override
	public void identification(int id) throws IdentificationError {
		assert(false);
	}

	@Override
	public void peep() {
		assert(false);
	}

	@Override
	public void markVoted(int id) throws VoterDoesNotExist, VoterAlreadyVoted {
		assert(false);
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
	
}
