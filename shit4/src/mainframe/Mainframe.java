package mainframe;
/*
 * creator: Ophir De Jager
 * date: 30.1.13
 * editors:
 */


import java.util.ArrayList;
import java.util.List;

import partiesList.IPartiesList;
import partiesList.IParty;
import partiesList.PartiesList;
import partiesList.Party;
import votersList.IVoterData;
import votersList.IVotersList;
import votersList.VoterData;
import votersList.VotersList;
import votersList.IVoterData.AlreadyIdentified;
import votersList.IVoterData.AlreadyVoted;
import votersList.IVoterData.Unidentified;
import votersList.IVotersList.VoterDoesntExist;
import votingStation.IVotingStation;

import backup.Backup;
import backup.IBackup;
import backup.ReadXMLFile;



public class Mainframe implements IMainframe {
	private IVotersList voters;
	private IPartiesList parties;
	private IVotersList unregisteredVoters;
	private List<IVotingStation> votingStations;
	private IBackup backup;
	private Thread backupThread;
	

	@Override
	public void initialize() {
		voters = loadVotersList();
		parties = loadPartiesList();
		unregisteredVoters = new VotersList();
		votingStations = new ArrayList<IVotingStation>();
		backup = new Backup(voters, parties);
		backupThread = (new Thread(backup));
		backupThread.start();
	}
	
	
	private IVotersList loadVotersList(){
		ArrayList<Integer> votersIdList = ReadXMLFile.readXMLVotersList();
		IVotersList voterList = new VotersList();
		for(Integer id : votersIdList) {
			voterList.addVoter(new VoterData(id));			
		}
		return voterList;
	}
	
	private IPartiesList loadPartiesList(){
		ArrayList<Party> parties = ReadXMLFile.readXMLvotingRecords();
		IPartiesList partieslist = new PartiesList();
		for(Party party: parties){
			partieslist.addParty(party);
		}
		return partieslist;
	}

	@Override
	public void restore() {
		voters = new VotersList();
		parties = new PartiesList();
		unregisteredVoters = new VotersList();
		votingStations = new ArrayList<IVotingStation>();
		backup = new Backup(voters, parties);
		backupThread = (new Thread(backup));
		backupThread.start();
		for(IVoterData voter: backup.restoreVoters()){
			voters.addVoter(voter);
		}
		for(IParty party: backup.restoreParties()){
			parties.addParty(party);
		}
	}

	@Override
	public void countVotes() {
		// TODO
	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
		backup.retire();
		try {
			backupThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	@Override
	public void identification(int id) throws IdentificationError{
		if(voters.inList(id)){
			try {
				voters.findVoter(id).markIdentified();
			} catch (AlreadyIdentified e) {
				throw new IdentificationError();
			} catch (VoterDoesntExist e) {
				// won't happen
				e.printStackTrace();
			}
		}
		else{
			IVoterData voter = new VoterData(id);
			try {
				voter.markIdentified();
			} catch (AlreadyIdentified e) {
				//won't happen
				return;
			}
			unregisteredVoters.addVoter(voter);
		}
	}

	@Override
	public void peep() {
		System.out.println("=========================");
		System.out.println("Peep of Mainframe");
		System.out.println("=========================");
		System.out.println("voters:");
		voters.peep();
		System.out.println("parties:");
		parties.peep();
		System.out.println("unregistered voters:");
		unregisteredVoters.peep();
		System.out.println("voting stations:");
		for(IVotingStation station: votingStations){
			station.peep();
		}
	}
	
	
	private IVoterData getVoter(int id) throws VoterDoesNotExist{
		synchronized (voters) {
			boolean inVoters = voters.inList(id), inUnregisteredVoters = unregisteredVoters.inList(id);
			if(!inVoters && !inUnregisteredVoters){
				throw new VoterDoesNotExist();
			}
			IVoterData voter = null;
			if(inVoters){
				try {
					voter = voters.findVoter(id);
				} catch (VoterDoesntExist e) {
					// won't happen
					e.printStackTrace();
				}
			}
			else{
				try {
					voter = unregisteredVoters.findVoter(id);
				} catch (VoterDoesntExist e) {
					// won't happen
					e.printStackTrace();
				}
			}
			return voter;
		}
	}


	@Override
	public void markVoted(int id) throws VoterDoesNotExist, VoterAlreadyVoted {
		IVoterData voter = getVoter(id);
		try {
			voter.markVoted();
		} catch (Unidentified e) {
			// won't happen
			e.printStackTrace();
		} catch (AlreadyVoted e) {
			throw new VoterAlreadyVoted();
		}
	}


	@Override
	public VoterStatus getVoterStatus(int id){
		IVoterData voter;
		try {
			voter = getVoter(id);
		} catch (VoterDoesNotExist e) {
			return VoterStatus.unidentified;
		}
		if(voter.hasVoted()) return VoterStatus.voted;
		if(voter.isIdentified()) return VoterStatus.identified;
		return VoterStatus.unidentified;
	}

}
