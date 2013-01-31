import java.util.Map;


public interface IVotersList extends Iterable<IVoterData>{
	
	
	
	/**
	 * adds a voter to the list.
	 */
	void addVoter(IVoterData toAdd);
	
	
	/**
	 * merging the list of the object with the list of toMerge
	 * @param toMerge - the list to merge
	 */
	void merge(VotersList toMerge);
	
	
	/**
	 * return the actual (not a clone) object of the requested voter
	 * @param id - the id of the requested voter
	 * @return the requested voter
	 * @throws Exception - if the voter id isn't in the list
	 */
	IVoterData findVoter(int id) throws Exception;
	
	/**
	 * replace current list with given one.
	 */
	void replaceWith (IVotersList toReplace);
	
	/**
	 * our standard peep. shows information about the contents of this object.
	 */
	void peep();

	/*
	 * more methods that need to be implemented - by Ophir De Jager
	 */
	
	/**
	 * 
	 * @param voters
	 * @return true if list has same elements as this one. false otherwise.
	 */
	boolean compareWith(IVotersList voters);
	
	/**
	 * 
	 * @param id
	 * @return true if the voter is in the list
	 */
	boolean inList(int id);
	
	/**
	 * 
	 * @return a map representing the VotersList, when each VoterData is mapped to
	 * 			the number of its appearances in the list
	 */
	Map<IVoterData,Integer> getVotersMap();
	
	/**
	 * 
	 * @param id - the id of the voter we want to get
	 * @return - the VoterData which represents the voter id
	 */
	IVoterData getVoter( int id );
	
}
