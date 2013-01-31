
public interface IBackup {

	/**
	 * Save the voters list and parties list as an XML file(s).
	 * 
	 * @param voters
	 * @return 
	 */
	boolean backupVoters(IVotersList voters, IPartiesList parties);
	
	/**
	 * 
	 * @return The voters list saved in backup file or an empty IVotersList otherwise.
	 */
	IVotersList restoreVoters();
	
	/**
	 * 
	 * @return The parties list saved in backup file or an empty IPartiesList otherwise.
	 */
	IPartiesList restoreParties();
}
