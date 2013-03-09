package votingStation.model;

import java.util.Date;

import partiesList.model.IParty;


/**
 * Implements IVotingRecord
 * @author Ophir De Jager
 */
public class VotingRecord implements IVotingRecord {
	private int id;
	private Date firstVote;
	private IParty party;
	private int numVotes;
	
	private final int maxVotes = 3;
	private final long maxVotingTimeSeconds;
	
	public VotingRecord(int id, long maxVotingTimeSeconds){
		this.id = id;
		this.firstVote = null;
		this.party = null;
		this.numVotes = 0;
		this.maxVotingTimeSeconds = maxVotingTimeSeconds;
	}
	
	
	@Override
	public int getID(){
		return id;
	}
	
	
	@Override
	public IParty getParty(){
		return party;
	}
	
	/**
	 * Change mili seconds to seconds.
	 * 
	 * @param milisecs: The given time in milliseconds
	 * @return the time in second.
	 */
	private long miliseconds2seconds(long milisecs){
		return milisecs / (1000);
	}
	
	
	@Override
	public boolean canVote(){
		if(firstVote == null) return true;
		Date now = new Date();
		long timeInSeconds = miliseconds2seconds(now.getTime() - firstVote.getTime());
		return (timeInSeconds < maxVotingTimeSeconds ) && numVotes < maxVotes;
	}
	
	
	@Override
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
