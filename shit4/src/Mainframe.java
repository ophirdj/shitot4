/*
 * creator: Ophir De Jager
 * date: 30.1.13
 * editors:
 */

import java.util.ArrayList;
import java.util.List;


public class Mainframe implements IMainframe {
	private IVotersList voters;
	private IPartiesList parties;
	private IVotersList unregisteredVoters;
	private List<IVotingStation> votingStations;
	private IBackup backup;
	private Thread backupThread;
	
	public Mainframe(){
		
	}
	

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
	public void identification(int id) {
		if(voters.inList(id)){
			voters.getVoter(id).setIdentified();
		}
		else{
			unregisteredVoters.addVoter(new VoterData(id, null));
			voters.getVoter(id).setIdentified();
			unregisteredVoters.getVoter(id).setIdentified();//not necessary...
		}
	}

	@Override
	public void peep() {
		System.out.println("=========================");
		System.out.println("Peep of Mainframe");
		System.out.println("=========================");
		System.out.println("parties:");
		parties.peep();
		System.out.println("voters:");
		voters.peep();
		System.out.println("unregistered voters:");
		unregisteredVoters.peep();
		System.out.println("voting stations:");
		for(IVotingStation station: votingStations){
			station.peep();
		}
	}


	@Override
	public void markVoted(int id) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public VoterStatus getVoterStatus(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
