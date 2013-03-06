package mainframe.logic;
public interface IMainframe{
	
	static class IdentificationError extends Exception{
		private static final long serialVersionUID = 1L;
		}
	
	static class VoterDoesNotExist extends Exception{
		private static final long serialVersionUID = 1L;
		}
	
	static class VoterStartedVote extends Exception{
		private static final long serialVersionUID = 1L;
		}
	
	enum VoterStatus{identified, unidentified, voted, startedVote;}
	
	/**
	 * Clean boot of the system.
	 */
	void initialize();
	
	
	/**
	 * Boot system from backup.
	 */
	void restore();
	
	
	/**
	 * count all the votes (parties list will hold that knowledge)
	 */
	void countVotes();
	
	
	/**
	 * Safely shut down mainframe
	 */
	void shutDown();
	
	
	/**
	 * Violently shut down mainframe (for tests only)
	 */
	void crash();
	
	/**
	 * check if voter is in the voters list and if he isn't there - add him to the unregistered voters list
	 * @param id
	 */
	void identification(int id) throws IdentificationError;
	
	
	
	/**
	 * our very much loved peep method
	 */
	void peep();

	
	
	/*
	 * Additional methods - by Ophir De Jager
	 */
	
	
	/**
	 * Mark that voter has voted in a voting station.
	 * @param id - Voter's ID.
	 */
	void markVoted(int id) throws VoterDoesNotExist;
	
	/**
	 * Mark that voter started to vote in a voting station.
	 * @param id - Voter's ID.
	 */
	void markStartedVote(int id) throws VoterDoesNotExist, VoterStartedVote;
	
	/**
	 * Return the status of the voter with the given ID.
	 * @param id - The voter's ID.
	 * @return The voter status.
	 */
	VoterStatus getVoterStatus(int id);
	
	/**
	 * Check that the parties in every station match the parties in the mainframe.
	 * @return true if they match, false otherwise.
	 */
	boolean checkParties();

}