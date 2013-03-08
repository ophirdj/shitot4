package votingStation.logic;

import global.dictionaries.Messages;
import java.util.List;
import java.util.ArrayList;
import choosingList.factories.IChoosingListFactory;
import choosingList.logic.IChoosingList;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import votingStation.factories.IVotingRecordFactory;
import votingStation.factories.IVotingStationWindowFactory;
import votingStation.gui.IVotingStationWindow;
import votingStation.gui.IVotingStationWindow.IllegalIdException;
import votingStation.model.IVotingRecord;
import mainframe.communication.IStationsController;
import mainframe.logic.IMainframe;
import mainframe.logic.IMainframe.VoterDoesNotExist;
import mainframe.logic.IMainframe.VoterStartedVote;

/**
 * Implements IVotingStation
 * @author Ophir De Jager
 *
 */
public class VotingStation implements IVotingStation {
	private IStationsController controller;
	private IPartiesList parties;
	private List<IVotingRecord> localVotersList; 
	private List<String> passwords;
	
	private IChoosingList choosingList;
	
	private IVotingStationWindow votingStationWindow;
	private IChoosingListFactory choosingListFactory;
	private IVotingRecordFactory votingRecordFactory;

	/**
	 * Create a new voting station
	 * @param passwords: passwords for test voting
	 * @param chooseFactory: factory for creating station's choosing list
	 * @param stationWindowFactory: factory to create the station's window
	 * @param votingRecordFactory: factory for creating voting records (to spy on voters...MUHAHAHA)
	 */
	public VotingStation(List<String> passwords, IChoosingListFactory chooseFactory, IVotingStationWindowFactory stationWindowFactory, IVotingRecordFactory votingRecordFactory){
		this.passwords = passwords;
		this.votingStationWindow = stationWindowFactory.createInstance(this);
		this.choosingListFactory = chooseFactory;
		this.votingRecordFactory = votingRecordFactory;
	};

	@Override
	public void initialize(IPartiesList parties,IStationsController controller){
		this.controller = controller;
		this.parties = parties;
		localVotersList = new ArrayList<IVotingRecord>();
		choosingList = choosingListFactory.createInstance(parties, votingStationWindow);
		votingStationWindow.startLoop();
	}

	@Override
	public IPartiesList getPartiesList(){
		return parties.copy();
	}
	
	private IVotingRecord getVotingRecord(int id){
		IMainframe.VoterStatus status = controller.getVoterStatus(id);
		switch (status) {
		case unidentified:
			votingStationWindow.printErrorMessage(Messages.You_need_to_identify_yourself_in_the_mainframe);
			return null;
		case identified:
			return votingRecordFactory.createInstance(id);
		case voted:
			for(IVotingRecord voter: localVotersList){
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

	@Override
	public void voting() throws ChoosingInterruptedException{
		int id;
		try{
			id = votingStationWindow.getID();
		}catch (IllegalIdException e) {
			votingStationWindow.printErrorMessage(Messages.ID_must_be_a_number);
			return;
		}
		
		IVotingRecord voter = getVotingRecord(id);
		if(voter == null) return;
		try {
			controller.markStartedVote(id);
		} catch (VoterDoesNotExist e1) {
			return;
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

	@Override
	public void testVoting() throws ChoosingInterruptedException{
		String password = votingStationWindow.getPassword();
		if (!passwords.contains(password)){
			votingStationWindow.printErrorMessage(Messages.wrong_password);
			return;
		}
		
		int id;
		try{
			id = votingStationWindow.getID();
		}catch (IllegalIdException e) {
			votingStationWindow.printErrorMessage(Messages.ID_must_be_a_number);
			return;
		}
		
		IVotingRecord voter = getVotingRecord(id);
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

