import java.util.Date;


public interface IVoterData {

	/**
	 * 
	 * @return Voter ID.
	 */
	int getId();

	/**
	 * Mark voter as identified (unidentified voter cannot vote)
	 */
	void setIdentified();

	/**
	 * Perform a deep copy of the voter.
	 * @return A copy of the voter.
	 */
	IVoterData copy();
	
	
	
	
}
