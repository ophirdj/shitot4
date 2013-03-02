package mainframe.tests;

import mainframe.logic.IMainframe;



public class Mainframe_stub implements IMainframe {
	
	public Mainframe_stub() {
		
	}
	
	@Override
	public void countVotes() {
		// TODO Auto-generated method stub
		
		System.out.println("countVotes");
	}

	@Override
	public VoterStatus getVoterStatus(int id) {
		// TODO Auto-generated method stub
		System.out.println("mistake!!!!");
		return null;
	}

	@Override
	public void identification(int id) throws IdentificationError {
		System.out.println("identification with: "+id);
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		System.out.println("initialize");
	}

	@Override
	public void markVoted(int id) throws VoterDoesNotExist {
		// TODO Auto-generated method stub
		System.out.println("mistake!!!!");
	}

	@Override
	public void peep() {
		// TODO Auto-generated method stub
		System.out.println("peep");
	}

	@Override
	public void restore() {
		// TODO Auto-generated method stub
		System.out.println("restore");
	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
		System.out.println("shutDown");
	}

	@Override
	public void markStartedVote(int id) throws VoterDoesNotExist {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void crash() {
		this.shutDown();
		
	}

}
