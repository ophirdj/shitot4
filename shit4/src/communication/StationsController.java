package communication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import partiesList.IPartiesList;

import factories.IChoosingListFactory;
import factories.IChoosingWindowFactory;
import factories.IVotingStationFactory;
import factories.IVotingStationWindowFactory;
import votingStation.IVotingStation;
import mainframe.IMainframe;
import mainframe.IMainframe.VoterDoesNotExist;
import mainframe.IMainframe.VoterStatus;

public class StationsController implements IStationsController {
	private List<IVotingStation> stations;
	private IMainframe mainframe;

	public StationsController(IMainframe mainframe,
			IVotingStationFactory votingStationFactory,
			IChoosingListFactory choosingListFactory,
			IChoosingWindowFactory choosingWindowFactory,
			IVotingStationWindowFactory votingStationWindowFactory,
			List<String> stationsNames, List<String> passwords) {
		this.mainframe = mainframe;
		this.stations = new ArrayList<IVotingStation>();
		for (String name: stationsNames) {
			stations.add(votingStationFactory.createInstance(passwords,
					name, choosingListFactory,
					choosingWindowFactory, votingStationWindowFactory));
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
		for(IVotingStation s: stations){
			s.initialize(parties.copy(), this);
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
		// TODO Auto-generated method stub
		// Daniel, that's your part
		return null;
	}

}
