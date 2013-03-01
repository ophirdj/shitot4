package mainframe.tests;

import java.util.List;

import votingStation.factories.IVotingStationFactory;
import votingStation.logic.IVotingStation;

public class VotingStationFactoryStub implements IVotingStationFactory {

	private VotingStationStub votingStationStub = new VotingStationStub();
	
	
	@Override
	public IVotingStation createInstance(List<String> passwords) {
		return this.votingStationStub;
	}
	
	public VotingStationStub getVotingStationStub(){
		return this.votingStationStub;
	}
	
	public void reset(){
		this.votingStationStub = new VotingStationStub();
	}

}
