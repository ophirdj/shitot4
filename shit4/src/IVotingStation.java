public interface IVotingStation {
	
	
	void initialize(IPartiesList parties,IMainframe mainframe);
	
	
	IPartiesList getPartiesList(); // also remove old voting data from last voters list
	
	
	void voting(int id);
	
	
	void testVoting(int id,String password);
	
	
	/*
	 * additions by Ophir De Jager - needs to be implemented
	 */
	
	/**
	 * 
	 * @return a copy of local voters list
	 */
	IVotersList getVotersList();
	
	/**
	 * our loved peephole method
	 */
	void peep();


}
