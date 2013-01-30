public interface IVotingStation {
public void initialize(IPartiesList parties,IMainframe mainframe);
public IPartiesList getPartiesList(); // also remove old voting data from last voters list
public void voting(int id);
public void testVoting(int id,String password);


/*
 * additions by Ophir De Jager - needs to be implemented
 */

/**
 * 
 * @return a copy of local voters list
 */
IVotersList getVotersList();


}
