package integrationTests.mainframeVotingStations;

import partiesList.model.IParty;
import choosingList.logic.IChoosingList;

public class ChoosingListStub implements IChoosingList {

	private IParty party;

	public ChoosingListStub(IntegrationTest test) {
		test.addChoosingListStub(this);
	}
	
	public void setParty(IParty party){
		this.party = party;
	}

	@Override
	public IParty chooseList() throws ChoosingInterruptedException {
		if(party == null) throw new ChoosingInterruptedException();
		return party;
	}

	@Override
	public void retire() {
		
	}

}
