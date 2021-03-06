package votersList.model;

/**
 * IVoterData is an interface that represents a voter
 * @author Emil
 *
 */
public interface IVoterData {
	
	/**
	 * an exception in case we try to identify the same voter twice
	 * @author Emil
	 *
	 */
	public static class AlreadyIdentified extends Exception{
		private static final long serialVersionUID = 1L;
		}
	
	/**
	 * an exception in case we try to do illegal operation on non-identified voter
	 * like 'markVoted' or 'markStartedVote' 
	 * @author Emil
	 *
	 */
	public static class Unidentified extends Exception{
		private static final long serialVersionUID = 1L;
		}

	
	/**
	 * 
	 * @return Voter ID.
	 */
	int getId();
	
	
	/**
	 * Check if voter has identified himself.
	 * @return true if voter has identified himself, false otherwise.
	 */
	boolean isIdentified();
	
	
	/**
	 * Check if the voter has already started voting in a station.
	 * @return true if the voter has started voting, false otherwise.
	 */
	boolean hasStartedVote();
	
	
	/**
	 * Check if voter has voted.
	 * @return true if voter has voted, false otherwise.
	 */
	public boolean hasVoted();
	
	
	/**
	 * Mark voter as identified (unidentified voter cannot vote).
	 */
	void markIdentified() throws AlreadyIdentified;
	
	
	/**
	 * Mark that voter has voted.
	 * @throws Unidentified if voter hasn't identified himself.
	 * @throws AlreadyVoted if voter has already voted.
	 */
	void markVoted() throws Unidentified;

	
	/**
	 * Perform a deep copy of the voter.
	 * @return A copy of the voter.
	 */
	IVoterData copy();


	/**
	 * Mark the voter started a voting process in one station.
	 * @throws Unidentified
	 */
	void markStartedVote() throws Unidentified;
}
