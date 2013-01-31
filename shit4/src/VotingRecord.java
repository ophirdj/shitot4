import java.util.Date;

/**
 * 
 * @author Ophir De Jager
 * 
 * This class is an inner class of the voting station.
 * It cannot and shouldn't be used anywhere outside the
 * voting station.
 */
public class VotingRecord {
	private int id;
	private Date firstVote;
	private IParty party;
	private int numVotes;
	
	
	public static class CantVote extends Exception{
		private static final long serialVersionUID = 1L;
	}
	private final int maxVotes = 3;
	
	public VotingRecord(int id){
		this.id = id;
		firstVote = null;
		party = IParty.WHITE_NOTE_PARTY;
		numVotes = 0;
	}
	
	public int getID(){
		return id;
	}
	
	public IParty getParty(){
		return party;
	}
	
	private long miliseconds2minutes(long milisecs){
		return milisecs / (1000 * 60);
	}
	
	public boolean canVote(){
		if(firstVote == null) return true;
		Date now = new Date();
		long timeInMinutes = miliseconds2minutes(now.getTime() - firstVote.getTime());
		return (timeInMinutes <= 2) && numVotes < maxVotes;
	}
	
	public void vote(IParty party) throws CantVote{
		if(!canVote()){
			throw new CantVote();
		}
		if(firstVote == null) firstVote = new Date();
		this.party = party;
		numVotes++;
	}
}
