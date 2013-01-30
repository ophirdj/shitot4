
public interface IVotersList {
	
	
	
	/**
	 * adds a voter to the list.
	 */
	public void addVoter(VoterData toAdd);
	
	
	/**
	 * merging the list of the object with the list of toMerge
	 * @param toMerge - the list to merge
	 */
	public void merge(VotersList toMerge);
	
	
	/**
	 * return the actual (not a clone) object of the requested voter
	 * @param id - the id of the requested voter
	 * @return the requested voter
	 * @throws Exception - if the voter id isn't in the list
	 */
	public VoterData findVoter(int id) throws Exception;
	
	/**
	 * replace current list with given one.
	 */
	public void replaceWith (VotersList toReplace);
	
	/**
	 * our standard peep. shows information about the contents of this object.
	 */
	public void peep();

	/*
	 * more methods that need to be implemented - by Ophir De Jager
	 */
	
	/**
	 * 
	 * @param voters
	 * @return true if list has same elements as this one. false otherwise.
	 */
	public boolean compareWith(IVotersList voters);
	
	/**
	 * 
	 * @param id
	 * @return true if the voter is in the list
	 */
	public boolean inList(int id);
	
}
