package integrationTests.mainframeAndVotingStations;

import org.junit.Assert;

import global.gui.StationPanel;
import partiesList.model.IPartiesList;
import partiesList.model.IPartiesList.PartyDoesNotExist;
import partiesList.model.IParty;
import choosingList.logic.IChoosingList;

public class ChoosingListStub implements IChoosingList {

	private String party;
	private IPartiesList parties;
	private StationPanel stationPanel;
	private boolean isInterrupt;

	public ChoosingListStub(IntegrationTest test, IPartiesList parties,
			StationPanel stationPanel) {
		Assert.assertNotNull(parties);
		Assert.assertNotNull(stationPanel);
		this.parties = parties;
		this.stationPanel = stationPanel;
		this.isInterrupt = false;
		test.addChoosingListStub(stationPanel, this);
	}
	
	public void setParty(String party){
		this.party = party;
	}
	
	public void interrupt(){
		isInterrupt = true;
	}

	@Override
	public IParty chooseList() throws ChoosingInterruptedException {
		if(party == null || isInterrupt) throw new ChoosingInterruptedException();
		try {
			return parties.getPartyBySymbol(party);
		} catch (PartyDoesNotExist e) {
			Assert.fail();
			return null;
		}
	}

	@Override
	public void retire() {}

}
