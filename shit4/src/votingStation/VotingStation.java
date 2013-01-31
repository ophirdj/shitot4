package votingStation;

import java.util.List;
import java.util.ArrayList;

import partiesList.IPartiesList;
import partiesList.IParty;
import votingStation.VotingRecord;
import choosingList.ChoosingList;
import mainframe.IMainframe;
import mainframe.IMainframe.VoterDoesNotExist;

public class VotingStation implements IVotingStation, Runnable {
	private IMainframe mainframe;
	private IPartiesList parties;
	private List<VotingRecord> localVotersList; 
	private List<String> passwords;
	private VotingStation_window votingStationWindow;

	public VotingStation(List<String> passwords,String name){
		this.passwords = passwords;
		votingStationWindow = new VotingStation_window(name,this);
	};

	public void initialize(IPartiesList parties,IMainframe mainframe){
		this.mainframe = mainframe;
		this.parties = parties;
		localVotersList = new ArrayList<VotingRecord>();
		new Thread(this).start();
	}
	
	public void run(){
		while(true){
			VotingStationAction choose = votingStationWindow.chooseAction();
			switch (choose) {
			case VOTING:
				voting();
				break;
			case TEST_VOTE:
				testVoting();
				break;
			default:
				break;
			}
		}
	}

	public IPartiesList getPartiesList(){
		return parties.copy();
	}
	
	private VotingRecord getVotingRecord(int id){
		IMainframe.VoterStatus status = mainframe.getVoterStatus(id);
		switch (status) {
		case unidentified:
			votingStationWindow.printError("You need to identify yourself in the mainframe");
			return null;
		case identified:
			return new VotingRecord(id);
		case voted:
			for(VotingRecord voter: localVotersList){
				if(voter.getID() == id && voter.canVote()) return voter;
				if(voter.getID() == id){
					votingStationWindow.printError("You can't change your vote anymore");
					return null;
				}
			}
			votingStationWindow.printError("You can't vote here");
		}
		return null;
	}

	public void voting(){
		int id;
		try{
			id = votingStationWindow.getID();
		}catch (NumberFormatException e) {
			votingStationWindow.printError("Error: id must be a number!");
			return;
		}
		
		VotingRecord voter = getVotingRecord(id);
		if(voter == null) return;
		IParty lastParty = new ChoosingList(parties,votingStationWindow).chooseList();
		try {
			mainframe.markVoted(id);
			if(!localVotersList.contains(voter)) localVotersList.add(voter);
		} catch (VoterDoesNotExist e) {
			// shouldn't happen
			e.printStackTrace();
			votingStationWindow.printError("Error: Chuck Norris removed you from existance.");
		}
		voter.vote(lastParty);
		votingStationWindow.printMessage("You successfully voted for the party" + lastParty.getName());
		
	}


	public void testVoting(){
		String password = votingStationWindow.getPassword();
//		if (!passwords.contains(password)){
//			votingStationWindow.printError("Error: wrong password");
//			return;
//		}
		
		int id;
		try{
			id = votingStationWindow.getID();
		}catch (NumberFormatException e) {
			votingStationWindow.printError("Error: id must be a number!");
			return;
		}
		
		VotingRecord voter = getVotingRecord(id);
		if(voter == null) return;
		IParty lastParty = new ChoosingList(parties,votingStationWindow).chooseList();
		votingStationWindow.printMessage("You successfully test vote for the party" + lastParty.getName());
	}

	@Override
	public void peep() {
		System.out.println("=========================");
		System.out.println("Peep of Voting Station");
		System.out.println("=========================");
		System.out.println("Voters that votes here: ");
		System.out.println(localVotersList);
		System.out.println("Parties are: ");
		parties.peep();
		System.out.println("Passwords for test voting are: ");
		System.out.println(passwords);
	}

}

