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
	
	
	private IPartiesList partiesList;
	private boolean isInit;

	@Override
	public void peep() {
	}

	@Override
	public void initialize(IPartiesList parties) {
		this.partiesList = parties;
		isInit = true;
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

	@Override
	public boolean checkInit() {
		return isInit;
	}

}
