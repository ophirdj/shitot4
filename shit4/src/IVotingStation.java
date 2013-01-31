public interface IVotingStation {
	
	
	void initialize(IPartiesList parties,IMainframe mainframe);
	
	
	IPartiesList getPartiesList(); // also remove old voting data from last voters list
	
	/**
	 * Start the voting sequence in the station's window
	 */
	void voting();
	
	
	void testVoting();
	
	
	/*
	 * additions by Ophir De Jager - needs to be implemented
	 */
	
	class CannotVoteHere extends Exception{
		private static final long serialVersionUID = 1L;
		}
	
	/**
	 * Check if voter can vote in this station
	 * @param id
	 * @return True if voter can vote in station. false otherwise.
	 */
	boolean canVote(int id);

	
	/**
	 * our loved peephole method
	 */
	void peep();


}
