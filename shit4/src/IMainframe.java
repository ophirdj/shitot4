import java.util.List;

public interface IMainframe {
	
	enum VoterStatus{identified, unidentified, voted;}
	
	/**
	 * Clean boot of the system.
	 */
	void initialize();
	
	
	/**
	 * Boot system from backup.
	 */
	void restore();
	
	
	/**
	 * check if list of voters in the mainframe is the same as the ones in the voting stations
	 */
	void compareLists();
	
	
	/**
	 * count all the votes (parties list will hold that knowledge)
	 */
	void countVotes();
	
	
	/**
	 * 
	 */
	void shutDown();
	
	/**
	 * check if voter is in the voters list and if he isn't there - add him to the unregistered voters list
	 * @param id
	 */
	void identification(int id);
	
	
	
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
	void markVoted(int id);
	
	/**
	 * Return the status of the voter with the given ID.
	 * @param id - The voter's ID.
	 * @return The voter status.
	 */
	VoterStatus getVoterStatus(int id);


}