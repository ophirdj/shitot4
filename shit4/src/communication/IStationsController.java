package communication;

import partiesList.IPartiesList;
import votingStation.IVotingStation;
import mainframe.IMainframe.VoterDoesNotExist;
import mainframe.IMainframe.VoterStatus;

public interface IStationsController extends Iterable<IVotingStation>{

	
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
	 * This method should perform the hot backup routine. I.e. it should go over
	 * all voting stations and receive their parties lists. Then it should
	 * create a parties list that is the merge of all these lists and save that
	 * to the local parties list (not just change the reference) by using the
	 * joinLists method of IPartyList.
	 */
	IPartiesList hotBackup();
}
