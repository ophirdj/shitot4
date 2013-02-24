package votingStation.logic;

import global.dictionaries.Messages;
import global.gui.StationPanel;

import java.util.List;
import java.util.ArrayList;

import choosingList.factories.IChoosingListFactory;
import choosingList.logic.IChoosingList;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;




import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import votingStation.factories.IVotingStationWindowFactory;
import votingStation.gui.IVotingStationWindow;
import votingStation.model.VotingRecord;
import mainframe.communication.IStationsController;
import mainframe.logic.IMainframe;
import mainframe.logic.IMainframe.VoterDoesNotExist;
import mainframe.logic.IMainframe.VoterStartedVote;

public class VotingStation implements IVotingStation {
	private IStationsController controller;
	private IPartiesList parties;
	private List<VotingRecord> localVotersList; 
	private List<String> passwords;
	
	private IChoosingList choosingList;
	
	private IVotingStationWindow votingStationWindow;
	private IChoosingListFactory choosingListFactory;

	public VotingStation(List<String> passwords, IChoosingListFactory chooseFactory, IVotingStationWindowFactory stationWindowFactory){
		this.passwords = passwords;
		votingStationWindow = stationWindowFactory.createInstance(this);
		choosingListFactory = chooseFactory;
	};

	public void initialize(IPartiesList parties,IStationsController controller){
		this.controller = controller;
		this.parties = parties;
		localVotersList = new ArrayList<VotingRecord>();
		choosingList = choosingListFactory.createInstance(parties, (StationPanel)votingStationWindow);
		votingStationWindow.startLoop();
	}

	public IPartiesList getPartiesList(){
		return parties.copy();
	}
	
	private VotingRecord getVotingRecord(int id){
		IMainframe.VoterStatus status = controller.getVoterStatus(id);
		switch (status) {
		case unidentified:
			votingStationWindow.printErrorMessage(Messages.You_need_to_identify_yourself_in_the_mainframe);
			return null;
		case identified:
			return new VotingRecord(id);
		case voted:
			for(VotingRecord voter: localVotersList){
				if(voter.getID() == id && voter.canVote()) return voter;
				if(voter.getID() == id){
					votingStationWindow.printErrorMessage(Messages.You_cannot_change_your_vote_anymore);
					return null;
				}
			}
			votingStationWindow.printErrorMessage(Messages.You_cannot_vote_here);
			return null;
		case startedVote:
			votingStationWindow.printErrorMessage(Messages.You_cannot_vote_here);
			return null;
		default:
			break;
		}
		return null;
	}

	public void voting() throws ChoosingInterruptedException{
		int id;
		try{
			id = votingStationWindow.getID();
		}catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			votingStationWindow.printErrorMessage(Messages.ID_must_be_a_number);
			return;
		}
		
		VotingRecord voter = getVotingRecord(id);
		if(voter == null) return;
		try {
			controller.markStartedVote(id);
		} catch (VoterDoesNotExist e1) {
		} catch (VoterStartedVote e) {
			return;
		}
		IParty lastParty = choosingList.chooseList();
		try {
			controller.markVoted(id);
			if(!localVotersList.contains(voter)) localVotersList.add(voter);
		} catch (VoterDoesNotExist e) {
			// shouldn't happen
			e.printStackTrace();
			votingStationWindow.printErrorMessage(Messages.Chuck_Norris_removed_you_from_existance);
		}
		voter.vote(lastParty);
		votingStationWindow.printInfoMessage(Messages.You_successfully_voted_for_the_party,lastParty);
		
	}


	public void testVoting() throws ChoosingInterruptedException{
		String password = votingStationWindow.getPassword();
		if (!passwords.contains(password)){
			votingStationWindow.printErrorMessage(Messages.wrong_password);
			return;
		}
		
		int id;
		try{
			id = votingStationWindow.getID();
		}catch (NumberFormatException e) {
			votingStationWindow.printErrorMessage(Messages.ID_must_be_a_number);
			return;
		}
		
		VotingRecord voter = getVotingRecord(id);
		if(voter == null) return;
		IParty lastParty = choosingList.chooseList();
		votingStationWindow.printInfoMessage(Messages.You_successfully_test_voted_for_the_party,lastParty);
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

