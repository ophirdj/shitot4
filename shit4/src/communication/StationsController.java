package communication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import GUI.Main_Window;

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
			List<String> stationsNames, List<String> passwords,
			Main_Window mainWindow) {
		this.mainframe = mainframe;
		this.stations = new ArrayList<IVotingStation>();
		for (String name : stationsNames) {
			stations.add(votingStationFactory.createInstance(passwords, name,
					choosingListFactory, choosingWindowFactory,
					votingStationWindowFactory, mainWindow));
		}
	}

	@Override
	public void peep() {
		System.out.println("=========================");
		System.out.println("Peep of StationsController");
		System.out.println("=========================");
		for (IVotingStation s : stations) {
			s.peep();
		}
	}

	@Override
	public void initialize(IPartiesList parties) {
		boolean flag = true;
		for (IVotingStation s : stations) {
			if (flag) {
				s.initialize(parties, this);
				flag = false;
			} else {
				s.initialize(parties.zeroCopy(), this);
			}
		}
	}

	@Override
	public void retire() {
		for (IVotingStation s : stations) {
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
		boolean first = true;
		for (IVotingStation s : stations) {
			if (first) {
				all = s.getPartiesList().copy();
				first = false;
			} else {
				all = all.joinLists(s.getPartiesList());
			}
		}
		return all;
	}

}
