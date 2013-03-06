package mainframe.communication;

import partiesList.model.IPartiesList;
import votingStation.logic.IVotingStation;
import mainframe.logic.IMainframe.VoterDoesNotExist;
import mainframe.logic.IMainframe.VoterStartedVote;
import mainframe.logic.IMainframe.VoterStatus;

public interface IStationsController{

	
	/**
	 * Standard peep.
	 */
	void peep();
	
	
	/**
	 * Initialize all the voting stations.
	 * @param parties: the list of parties.
	 */
	void initialize(IPartiesList parties);
	
	
	/**
	 * Retire all stations.
	 */
	void retire();
	
	
	/**
	 * Check voter status.
	 * @param id: voter's ID number.
	 * @return the status of the voter as apears in the mainframe.
	 */
	VoterStatus getVoterStatus(int id);
	
	
	/**
	 * Mark that voter has voted in a voting station.
	 * @param id - Voter's ID.
	 */
	void markVoted(int id) throws VoterDoesNotExist;
	
	
	/**
	 * Mark that voter has started voting in a voting station.
	 * @param id - Voter's ID.
	 */
	void markStartedVote(int id) throws VoterDoesNotExist, VoterStartedVote;
	
	
	/**
	 * This method should perform the hot backup routine. I.e. it should go over
	 * all voting stations and receive their parties lists. Then it should
	 * create a parties list that is the merge of all these lists and save that
	 * to the local parties list (not just change the reference) by using the
	 * joinLists method of IPartyList.
	 */
	IPartiesList hotBackup();


	/**
	 * Check that parties lists in all stations have the same parties as in the given list.
	 * @param partiesList
	 */
	boolean checkParties(IPartiesList partiesList);
}
