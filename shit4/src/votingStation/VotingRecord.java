package votingStation;

import java.util.Date;

import partiesList.IPartiesList;
import partiesList.IParty;
import partiesList.Party;


/**
 * 
 * @author Ophir De Jager
 * This class is local to this package.
 */
class VotingRecord {
	private int id;
	private Date firstVote;
	private IParty party;
	private int numVotes;
	
	private final int maxVotes = 3;
	
	public VotingRecord(int id){
		this.id = id;
		firstVote = null;
		party = new Party("white vote party", IParty.WHITE_VOTE_SYMBOL);
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
	
	public void vote(IParty party){
		if(firstVote == null) firstVote = new Date();
		else{
			this.party.decreaseVoteNumber();
		}
		this.party = party;
		this.party.increaseVoteNumber();
		numVotes++;
	}
	
	public String toString(){
		return "voting record: id " + id + " voted to " + party +
				". This is his vote number " + numVotes + ". He first voted on " + firstVote + "\n";
	}
}
