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
	private Backup backup;
	
	public Mainframe(){
		
	}
	

	@Override
	public void initialize() {
		voters = loadVotersList();
		parties = loadPartiesList();
		unregisteredVoters = new VotersList();
		// TODO is it going to be like that? aren't we supposed to get the stations list as a parameter?
		votingStations = new ArrayList<IVotingStation>();
		backup = new Backup();
	}
	
	//
	private IVotersList loadVotersList(){
		ArrayList<Integer> votersIdList = ReadXMLFile.readXMLVotersList();
		IVotersList voterList = new VotersList();
		for (Integer id : votersIdList) {
			voterList.addVoter(new VoterData(id));			
		}
		
		return voterList;
		
	}
	
	private IPartiesList loadPartiesList(){
		ArrayList<Party> res = ReadXMLFile.readXMLvotingRecords();
		//TODO convert to PartiesList after implementation
		return null;
	}

	
	private void check() {
		// TODO WTF???
		
	}

	
	private void hotbackup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void restore() {
		backup = new Backup();
		voters = backup.restore();
		parties = loadPartiesList();
		unregisteredVoters = new VotersList();
		// TODO is it going to be like that? aren't we supposed to get the stations list as a parameter?
		votingStations = new ArrayList<IVotingStation>();
	}

	@Override
	public void compareLists() {
		for(IVotingStation station : votingStations){
			IVotersList stationsVotersList = station.getVotersList();
			if(!stationsVotersList.compareWith(voters)){
				System.out.println("mainframe's voters list: " + voters);
				System.out.println("mainframe's unregistered voters : " + unregisteredVoters);
				System.out.println("voting station's voters list" + stationsVotersList);
				throw new Error("voting station's voters list doesn't match the one on mainframe");
			}
		}
		//if reached here voters lists are the same in mainframe and all other voting stations
	}

	@Override
	public void countVotes() {
		for(VoterData voter: voters){
			if(voter.getLastChosenParty() != null){
				parties.getPartyBySymbol(voter.getLastChosenParty().getSymbol()).increaseVoteNumber();
			}
		}
	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub

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

	/**
	 * From XML File
	 */
	@Override
	public List<Integer> getAuthorizedIdList() {
		return ReadXMLFile.readXMLVotersList();
	}

}
