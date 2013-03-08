package integrationTests.mainframeVotingStations;

import java.util.List;

import votingStation.factories.IVotingStationFactory;
import mainframe.communication.IStationsController;
import mainframe.communication.IStationsControllerFactory;
import mainframe.communication.StationsController;
import mainframe.logic.IMainframe;

public class StationsControllerTestConfigurationFactory implements
		IStationsControllerFactory {

	private IVotingStationFactory votingStationFactory;
	private List<String> passwords;
	private int numStations;

	public StationsControllerTestConfigurationFactory(
			IVotingStationFactory votingStationFactory, List<String> passwords, int numStations) {
		this.votingStationFactory = votingStationFactory;
		this.passwords = passwords;
		this.numStations = numStations;
	}

	@Override
	public IStationsController createInstance(IMainframe mainframe) {
		return new StationsController(mainframe, votingStationFactory, passwords, numStations);
	}

}
