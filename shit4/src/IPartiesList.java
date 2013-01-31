public interface IPartiesList extends Iterable<IParty>, Cloneable{
	/*
	 * !!!!!!!!!!!!!!!!!IMPORTANT!!!!!!!!!!!!!!!!!!!
	 * Should contain also the white vote party.
	 * Iterator should iterate over the white vote party as well.
	 * Clone method should perform a deep copy.
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 */
	
	/**
	 * 
	 * @param symbol
	 * @return The party that matches the given symbol
	 */
	IParty getPartyBySymbol(String symbol);
	
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
	 * @return The total sum of the number of votes to ALL parties in list.
	 */
	int getTotalVotes();
	
	/**
	 * 
	 * @param start
	 * @param end
	 * @return A new sublist that includes all parties from this list from location start(inclusive)
	 * to end(exclusive). Note: indexes can be out of range - in that case return the sublist composed
	 * of the parties in legal (in range) indexes and ignore illegal (out of range) indexes.
	 * I.e. for the list (0 1 2) and indexes start = 1, end = 4, should return the list (1 2).
	 * Update: This method shouldn't in any case return the white vote party (i.e. return a sublist
	 * that doesn't contain white vote part).
	 */
	IPartiesList sublist(int start, int end);



}