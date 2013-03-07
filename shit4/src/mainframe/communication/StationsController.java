package mainframe.communication;

import java.util.ArrayList;
import java.util.List;
import partiesList.model.IPartiesList;
import votingStation.factories.IVotingStationFactory;
import votingStation.logic.IVotingStation;
import mainframe.logic.IMainframe;
import mainframe.logic.IMainframe.VoterDoesNotExist;
import mainframe.logic.IMainframe.VoterStatus;
import mainframe.logic.IMainframe.VoterStartedVote;

/**
 * Implementation of IStationsController
 * @author Daniel Eidel
 *
 */
public class StationsController implements IStationsController {
	private List<IVotingStation> stations;
	private IVotingStation firstStation;
	private IMainframe mainframe;

	/**
	 * Create a new controller 
	 * @param mainframe: mainframe that will use this controller
	 * @param votingStationFactory: factory to create stations
	 * @param passwords: passwords for test vote in stations
	 * @param numStations: number of voting stations
	 */
	public StationsController(IMainframe mainframe,
			IVotingStationFactory votingStationFactory,
			List<String> passwords, int numStations) {
		this.mainframe = mainframe;
		this.stations = new ArrayList<IVotingStation>();
		firstStation = votingStationFactory.createInstance(passwords);
		for (int i = 1; i < numStations; i++) {
			stations.add(votingStationFactory.createInstance(passwords));
		}
	}

	@Override
	public void peep() {
		System.out.println("=========================");
		System.out.println("Peep of StationsController");
		System.out.println("=========================");
		firstStation.peep();
		for(IVotingStation s: stations){
			s.peep();
		}
	}

	@Override
	public void initialize(IPartiesList parties) {
		firstStation.initialize(parties.copy(), this);
		for(IVotingStation s: stations){
			s.initialize(parties.zeroCopy(), this);
		}
	}

	@Override
	public void retire() {
		firstStation.retire();
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
	public void markStartedVote(int id) throws VoterDoesNotExist, VoterStartedVote {
		mainframe.markStartedVote(id);
	}

	@Override
	public IPartiesList hotBackup() {
		IPartiesList all = firstStation.getPartiesList().copy();;
		for(IVotingStation s: stations){
			all = all.joinLists(s.getPartiesList());
		}
		return all;
	}

	@Override
	public boolean checkParties(IPartiesList partiesList) {
		for(IVotingStation station: stations){
			if(!station.getPartiesList().zeroCopy().equals(partiesList)) return false;
		}
		return true;
	}

}
