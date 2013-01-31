public interface IParty{
	
	static final String WHITE_NOTE_SYMBOL = null;
	static final IParty WHITE_NOTE_PARTY = null;
	
	/**
	 * 
	 * @return The party's symbol
	 */
	String getSymbol();
	
	/**
	 * 
	 * @return The party's name
	 */
	String getName();
	
	/**
	 * Add 1 vote to the party
	 */
	void increaseVoteNumber();
	
	/**
	 * Remove 1 vote from the part. Error if number of votes goes below 0.
	 */
	void decreaseVoteNumber();
	
	/**
	 * 
	 * @return The number of votes to this party
	 */
	int getVoteNumber();
	
	/**
	 * Perform a deep copy.
	 * @return A copy of this party.
	 */
	IParty copy();
}
