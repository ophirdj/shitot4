import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class VotingStation implements IVotingStation {
	
	
	
	
	public static class VotingRecord {
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
		
		public String toString(){
			return "voting record: id " + id + " voted to " + party +
					". This is his vote number " + numVotes + ". He first voted on " + firstVote + "\n";
		}
	}
	
	
	
	
	private IMainframe mainframe;
	private IPartiesList parties;
	private List<VotingRecord> loaclVotersList;
	private List<String> passwords;
	private IWindow votingStationWindow;

	public VotingStation(List<String> passwords){
		this.passwords = passwords;
	};

	public void initialize(IPartiesList parties,IMainframe mainframe){
		this.mainframe = mainframe;
		this.parties = parties;
		loaclVotersList = new ArrayList<VotingRecord>();
	}

	public IPartiesList getPartiesList(){
		return parties.copy();
	}
	
	public boolean canVote(int id){
		IMainframe.VoterStatus status = mainframe.getVoterStatus(id);
		if(status == IMainframe.VoterStatus.unidentified){
			return false;
		}
		if(status == IMainframe.VoterStatus.identified){
			return true;
		}
		for(VotingRecord voter: loaclVotersList){
			if(voter.getID() == id) return voter.canVote();
		}
		return false;
	}

	public void voting(){
		// TODO wait for Ziv to finish things up in Window 
	}


	public void testVoting(){
		// TODO wait for Ziv to finish things up in Window
	}

	@Override
	public void peep() {
		System.out.println("=========================");
		System.out.println("Peep of Voting Station");
		System.out.println("=========================");
		System.out.println("Voters that votes here: ");
		System.out.println(loaclVotersList);
		System.out.println("Parties are: ");
		parties.peep();
		System.out.println("Passwords for test voting are: ");
		System.out.println(passwords);
	}

}

