package votingStation;

import java.util.List;
import java.util.ArrayList;

import javax.swing.JPanel;

import communication.IStationsController;

import choosingList.IChoosingList;
import choosingList.IChoosingList.ChoosingInterruptedException;

import partiesList.IPartiesList;
import partiesList.IParty;
import votingStation.VotingRecord;
import factories.IChoosingListFactory;
import factories.IChoosingWindowFactory;
import factories.IVotingStationWindowFactory;
import mainframe.IMainframe;
import mainframe.IMainframe.VoterDoesNotExist;

public class VotingStation implements IVotingStation {
	private IStationsController controller;
	private IPartiesList parties;
	private List<VotingRecord> localVotersList; 
	private List<String> passwords;
	
	private IChoosingList choosingList;
	
	private IVotingStationWindow votingStationWindow;
	private IChoosingWindowFactory choosingWindowFactory;
	private IChoosingListFactory choosingListFactory;

	public VotingStation(List<String> passwords,String name, IChoosingListFactory choseFactory, IChoosingWindowFactory choseWindowFactory, IVotingStationWindowFactory stationWindowFactory){
		this.passwords = passwords;
		votingStationWindow = stationWindowFactory.createInstance(name, this);
		choosingWindowFactory = choseWindowFactory;
		choosingListFactory = choseFactory;
	};

	public void initialize(IPartiesList parties,IStationsController controller){
		this.controller = controller;
		this.parties = parties;
		localVotersList = new ArrayList<VotingRecord>();
		choosingList = choosingListFactory.createInstance(parties, (JPanel)votingStationWindow, choosingWindowFactory);
		votingStationWindow.startLoop();
	}

	public IPartiesList getPartiesList(){
		return parties.copy();
	}
	
	private VotingRecord getVotingRecord(int id){
		IMainframe.VoterStatus status = controller.getVoterStatus(id);
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

	public void voting() throws ChoosingInterruptedException{
		int id;
		try{
			id = votingStationWindow.getID();
		}catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			votingStationWindow.printError("Error: id must be a number!");
			return;
		}
		
		VotingRecord voter = getVotingRecord(id);
		if(voter == null) return;
		IParty lastParty = choosingList.chooseList();
		try {
			controller.markVoted(id);
			if(!localVotersList.contains(voter)) localVotersList.add(voter);
		} catch (VoterDoesNotExist e) {
			// shouldn't happen
			e.printStackTrace();
			votingStationWindow.printError("Error: Chuck Norris removed you from existance.");
		}
		voter.vote(lastParty);
		votingStationWindow.printMessage("You successfully voted for the party " + lastParty.getName());
		
	}


	public void testVoting() throws ChoosingInterruptedException{
		String password = votingStationWindow.getPassword();
		if (!passwords.contains(password)){
			votingStationWindow.printError("Error: wrong password");
			return;
		}
		
		int id;
		try{
			id = votingStationWindow.getID();
		}catch (NumberFormatException e) {
			votingStationWindow.printError("Error: id must be a number!");
			return;
		}
		
		VotingRecord voter = getVotingRecord(id);
		if(voter == null) return;
		IParty lastParty = choosingList.chooseList();
		votingStationWindow.printMessage("You successfully test vote for the party " + lastParty.getName());
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

	@Override
	public void retire() {
		choosingList.retire();
		votingStationWindow.endLoop();
	}
}

