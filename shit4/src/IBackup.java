/**
 * 
 * @author Ophir De Jager
 * This is the class for backup of voters list and parties list.
 * Its main method (i.e. Run() should contain a loop that every 2 minutes
 * wakes up, backs up those lists, and goes back to sleep for the next 2 minutes.
 * Note: constructor will be given references to those lists.
 */
public interface IBackup extends Runnable{
	
	/**
	 * Called by the mainframe, this method should do a last backup of the system
	 * and then end the thread's run (the thread will be joined from the mainframe).
	 */
	void retire();
	
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
