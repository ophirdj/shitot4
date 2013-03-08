package integrationTests.mainframeBackup;

import java.util.List;

import votingStation.factories.IVotingStationFactory;
import votingStation.logic.IVotingStation;

/**
 * this stub factory returns the same object - and we can make it return a brand new
 * object by invoking restart()
 * @author Emil
 *
 */
public class VotingStationFactoryStub implements IVotingStationFactory {

	/**
	 * where we hold the stationsControllerStub that this factory repeatedly returns 
	 */
	private VotingStationStub votingStationStub = new VotingStationStub();
	
	
	@Override
	public IVotingStation createInstance(List<String> passwords) {
		return this.votingStationStub;
	}
	
	/**
	 * 
	 * @return get the created StationsControllerStub
	 */
	public VotingStationStub getVotingStationStub(){
		return this.votingStationStub;
	}
	
	/**
	 * makes this factory to return a brand new BackupStub object instead of the same one
	 */
	public void reset(){
		this.votingStationStub = new VotingStationStub();
	}

}
