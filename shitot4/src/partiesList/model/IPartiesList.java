package partiesList.model;

/**
 * IPartiesList is an interface that represents parties list
 * Important: the implementation of this interface should contain also the white vote party.
 * and also the iterator should NOT iterate over the white note party.
 * @author Emil
 *
 */
public interface IPartiesList extends Iterable<IParty>{
	/*
	 * !!!!!!!!!!!!!!!!!IMPORTANT!!!!!!!!!!!!!!!!!!!
	 * Should contain also the white vote party.
	 * Iterator should NOT iterate over the white note party.
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 */
	
	/**
	 * an exception in case that a method is asked to relate to a party that does not exist
	 * @author Emil
	 *
	 */
	static class PartyDoesNotExist extends Exception{
		private static final long serialVersionUID = 1L;
	}
	
	/**
	 * 
	 * @param symbol
	 * @return The party that matches the given symbol
	 */
	IParty getPartyBySymbol(String symbol) throws PartyDoesNotExist;
	
	/**
	 * 
	 * @param partiesList - a distinct IPartiesList
	 * @return A new IPartiesList that contains all parties from both lists (current and given).
	 * If the same party is in both lists the new list should have only one party that
	 * has the same attributes as the parties except that the number of votes to this
	 * party should be the sum of the number of votes of the parties.
	 */
	IPartiesList joinLists(IPartiesList partiesList);

	/*
	 * additions by Ophir De Jager
	 */
	
	/**
	 * Our most beloved peep method
	 */
	void peep();
	
	/**
	 * Add a party to the list
	 * @param party
	 */
	void addParty(IParty party);
	
	/**
	 * 
	 * @return The total sum of the number of votes to ALL parties in list including white note party.
	 */
	int getTotalVotes();
	
	/**
	 * 
	 * @return A reference the white note party.
	 */
	IParty getWhiteNoteParty();
	
	/**
	 * 
	 * @param start
	 * @param end
	 * @return A new sublist that includes all parties from this list from location start(inclusive)
	 * to end(exclusive). Note: indexes can be out of range - in that case return the sublist composed
	 * of the parties in legal (in range) indexes and ignore illegal (out of range) indexes.
	 * I.e. for the list (0 1 2) and indexes start = 1, end = 4, should return the list (1 2).
	 * Note: The white vote party in the result should be a copy of the white vote part in this list.
	 */
	IPartiesList sublist(int start, int end);
	
	/**
	 * Perform a deep copy of the parties list.
	 * @return A copy of the list.
	 */
	IPartiesList copy();
	
	
	/**
	 * 
	 * @return the number of parties (excluding the white party).
	 */
	int size();
	
	
	/**
	 * 
	 * @return a copy of the parties list in witch the vote count for every party is 0. 
	 */
	IPartiesList zeroCopy();



}