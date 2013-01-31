package votersList;

public interface IVotersList extends Iterable<IVoterData>{
	
	
	static class VoterDoesntExist extends Exception{
		private static final long serialVersionUID = 1L;
		}
	
	
	/**
	 * adds a voter to the list.
	 */
	void addVoter(IVoterData toAdd);
	
	
	/**
	 * Return the actual (not a clone) object of the requested voter.
	 * @param id - the id of the requested voter.
	 * @throws VoterDoesntExist if no matching voter in the list.
	 * @return the requested voter.
	 */
	IVoterData findVoter(int id) throws VoterDoesntExist;
	
	
	/**
	 * our standard peep. shows information about the contents of this object.
	 */
	void peep();
	
	
	/**
	 * 
	 * @param id
	 * @return true if the voter is in the list
	 */
	boolean inList(int id);	
	
	
	/**
	 * Perform a deep copy.
	 * @return a copy of this list.
	 */
	IVotersList copy();
}
