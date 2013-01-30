/*
 * creator: Ophir De Jager
 * date: 30.1.13
 * editors:
 */

import java.util.ArrayList;
import java.util.List;


public class Mainframe implements IMainframe {
	IVotersList voters;
	IPartiesList parties;
	IVotersList unregisteredVoters;
	List<IVotingStation> votingStations;
	

	

	@Override
	public void initialize() {
		voters = loadVotersList();
		parties = loadPartiesList();
		unregisteredVoters = new VotersList();
		// TODO is it going to be like that? aren't we supposed to get the stations list as a parameter?
		votingStations = new ArrayList<IVotingStation>();
	}
	
	private IVotersList loadVotersList(){
		// TODO load list from file
		return null;
	}
	
	private IPartiesList loadPartiesList(){
		// TODO load list from file
		return null;
	}

	@Override
	public void check() {
		// TODO WTF???
		
	}

	@Override
	public void hotbackup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void restore() {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub

	}
	

	@Override
	public void identification(int id) {
		if(voters.inList(id)){
			//ok
		}
		else{
			unregisteredVoters.addVoter(new VoterData(id, null));
		}
	}

	@Override
	public void peep() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Integer> getAuthorizedIdList() {
		// TODO Auto-generated method stub
		return null;
	}

}
