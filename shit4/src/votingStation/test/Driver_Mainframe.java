package votingStation.test;

import mainframe.logic.IMainframe;

public class Driver_Mainframe implements IMainframe{

	private static int place;
	
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
	public void markVoted(int id) throws VoterDoesNotExist {
		
	}
	
	@Override
	public VoterStatus getVoterStatus(int id) {
		final IMainframe.VoterStatus statuses[] = {IMainframe.VoterStatus.identified,IMainframe.VoterStatus.unidentified,IMainframe.VoterStatus.voted};
		return statuses[place];
	}
	
	public static void setPlace(int place){
		Driver_Mainframe.place = place;
	}
}
