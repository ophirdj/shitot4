package integrationTests.mainframeAndVotingStations;

import partiesList.model.IPartiesList;
import votersList.model.IVotersList;
import fileHandler.model.IReadSuppliedXML;

public class ReadSuppliedXMLStub implements IReadSuppliedXML{

	private IVotersList voters;
	private IPartiesList parties;

	@Override
	public IVotersList readVotersList() {
		return voters;
	}

	@Override
	public IPartiesList readPartiesList() {
		return parties;
	}

	public void setVotersList(IVotersList voters) {
		this.voters = voters;
	}

	public void setPartiesList(IPartiesList parties) {
		this.parties = parties;
	}
	
}
