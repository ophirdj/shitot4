package mainframe.logic;

/**
 * Interface of the mainframe class in our project
 * @author Ophir De Jager
 *
 */
public interface IMainframe{
	
	/**
	 * Exception in case of error in the identification process
	 */
	static class IdentificationError extends Exception{
		private static final long serialVersionUID = 1L;
		}
	
	/**
	 * Exception in case the voter does not exist
	 */
	static class VoterDoesNotExist extends Exception{
		private static final long serialVersionUID = 1L;
		}
	
	/**
	 * Exception in case that the voter has started to vote and we try to mark him again as
	 * someone who has started to vote
	 */
	static class VoterStartedVote extends Exception{
		private static final long serialVersionUID = 1L;
		}
	
	/**
	 * Enum that represents the voter's status
	 */
	enum VoterStatus{
		/**
		 * Voter has identified in mainframe
		 */
		identified,
		/**
		 * Voter did not identify yet
		 */
		unidentified,
		/**
		 * Voter has already voted
		 */
		voted,
		/**
		 * Voter has started to vote (but not finished)
		 */
		startedVote;}
	
	/**
	 * Clean boot of the system.
	 */
	void initialize();
	
	
	/**
	 * Boot system from backup.
	 */
	void restore();
	
	
	/**
	 * Count all the votes (parties list will hold that knowledge)
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
	 * Check if voter is in the voters list and if he isn't there - add him to the unregistered voters list
	 * @param id: voter ID
	 */
	void identification(int id) throws IdentificationError;
	
	/**
	 * Our very much loved peep method
	 */
	void peep();	
	
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

	/**
	 * Check that the initialization was done successfully 
	 * and that internal communication is on. 
	 * @return true if the initialization succeed, false otherwise
	 */
	boolean checkInit();

}