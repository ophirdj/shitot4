package votingStation.model;

import partiesList.model.IParty;

/**
 * Data of voter relevant to voting station
 * @author Ophir De Jager
 *
 */
public interface IVotingRecord {

	/**
	 * Get voter ID
	 * @return voter ID
	 */
	int getID();

	/**
	 * Get last party voter voted for (or null if didn't vote) 
	 * @return last party voter voted for (or null if didn't vote)
	 */
	IParty getParty();

	/**
	 * Check if voter can vote
	 * @return true if can, false otherwise
	 */
	boolean canVote();

	/**
	 * Vote to a party
	 * @param party
	 */
	void vote(IParty party);

}