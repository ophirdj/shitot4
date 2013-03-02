package votingStation.model;

import partiesList.model.IParty;

public interface IVotingRecord {

	/**
	 * 
	 * @return voter ID
	 */
	int getID();

	/**
	 * 
	 * @return last party voter voted for (or white note if didn't vote)
	 */
	IParty getParty();

	/**
	 * 
	 * @return can the voter vote
	 */
	boolean canVote();

	/**
	 * vote to a party
	 * @param party
	 */
	void vote(IParty party);

}