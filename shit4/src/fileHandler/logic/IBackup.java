package fileHandler.logic;
import partiesList.model.IPartiesList;
import votersList.model.IVotersList;

public interface IBackup{
	
	/**
	 * 
	 * @return The voters list saved in backup file or an empty IVotersList otherwise.
	 */
	IVotersList restoreVoters();
	
	
	/**
	 * 
	 * @return The voters list saved in backup file or an empty IVotersList otherwise.
	 */
	IVotersList restoreUnregisteredVoters();
	
	/**
	 * 
	 * @return The parties list saved in backup file or an empty IPartiesList otherwise.
	 */
	IPartiesList restoreParties();
	
	/**
	 * backup the given lists to XML files
	 * @param parties: a list of parties
	 * @param voters: a list of voters
	 */
	void storeState(IPartiesList parties, IVotersList voters, IVotersList unregistered);
}
