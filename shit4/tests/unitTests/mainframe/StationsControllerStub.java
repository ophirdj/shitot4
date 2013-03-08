package unitTests.mainframe;

import partiesList.model.IPartiesList;
import partiesList.model.IPartiesList.PartyDoesNotExist;
import partiesList.model.IParty;
import mainframe.communication.IStationsController;
import mainframe.logic.IMainframe.VoterDoesNotExist;
import mainframe.logic.IMainframe.VoterStartedVote;
import mainframe.logic.IMainframe.VoterStatus;

/**
 * in the testing, we only use the methods 'initialize', 'markVoted', 
 * 'checkParties' and 'hotBackup' of this stub
 * when we use markVoted we mark that the voter voted to a specific party, in our case - 'oui'
 * 'checkParties' returns true as it will be in the real running of this class
 * @author Emil
 *
 */
public class StationsControllerStub implements IStationsController {

	/*private IMainframe m;
	private IVotingStationFactory vs;
	private ArrayList<String> pass;*/
	
	
	private IPartiesList partiesList;
	
	
	/*
	public StationsControllerStub(IMainframe m,IVotingStationFactory vs, ArrayList<String> pass, int numOfStations){
		
	}*/

	@Override
	public void peep() {
	}

	@Override
	public void initialize(IPartiesList parties) {
		this.partiesList = parties;

	}

	@Override
	public void retire() {
	}

	@Override
	public VoterStatus getVoterStatus(int id) {
		return null;
	}

	@Override
	public void markVoted(int id) throws VoterDoesNotExist {
		IParty p;
		try {
			p = this.partiesList.getPartyBySymbol("oui");
			p.increaseVoteNumber();
		} catch (PartyDoesNotExist e) {
			e.printStackTrace();
		}
		

	}

	@Override
	public void markStartedVote(int id) throws VoterDoesNotExist,
			VoterStartedVote {
	}

	@Override
	public IPartiesList gatherVotesFromVotingStations() {
		return this.partiesList;
	}

	@Override
	public boolean checkParties(IPartiesList partiesList) {
		return true;
	}
	
	/*public void getPartiesListForHotBackup(IPartiesList p){
		this.partiesList = p;
	}*/

}
