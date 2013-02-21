package mainframe.communication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import partiesList.model.IPartiesList;
import votingStation.factories.IVotingStationFactory;
import votingStation.logic.IVotingStation;
import mainframe.logic.IMainframe;
import mainframe.logic.IMainframe.VoterDoesNotExist;
import mainframe.logic.IMainframe.VoterStatus;

public class StationsController implements IStationsController {
	private List<IVotingStation> stations;
	private IMainframe mainframe;

	public StationsController(IMainframe mainframe,
			IVotingStationFactory votingStationFactory,
			List<String> passwords, int stationAmounts) {
		this.mainframe = mainframe;
		this.stations = new ArrayList<IVotingStation>();
		for (int i = 0; i < stationAmounts; i++) {
			stations.add(votingStationFactory.createInstance(passwords));
		}
	}

	@Override
	public void peep() {
		System.out.println("=========================");
		System.out.println("Peep of StationsController");
		System.out.println("=========================");
		for(IVotingStation s: stations){
			s.peep();
		}
	}

	@Override
	public void initialize(IPartiesList parties) {
		boolean flag = true;
		for(IVotingStation s: stations){
			if(flag){
				s.initialize(parties, this);
				flag = false;
			}
			else{
				s.initialize(parties.zeroCopy(), this);
			}
		}
	}

	@Override
	public void retire() {
		for(IVotingStation s: stations){
			s.retire();
		}
	}

	@Override
	public VoterStatus getVoterStatus(int id) {
		return mainframe.getVoterStatus(id);
	}

	@Override
	public void markVoted(int id) throws VoterDoesNotExist {
		mainframe.markVoted(id);
	}

	@Override
	public Iterator<IVotingStation> iterator() {
		return stations.iterator();
	}

	@Override
	public IPartiesList hotBackup() {
		IPartiesList all = null;
		boolean first=true;
		for(IVotingStation s: stations){
			if(first){ 
				all = s.getPartiesList().copy();
				first = false;
			}
			else{
				all = all.joinLists(s.getPartiesList());
			}
		}
		return all;
	}

}
