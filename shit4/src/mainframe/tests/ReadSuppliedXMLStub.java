package mainframe.tests;

import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import partiesList.factories.PartiesListFactory;
import partiesList.factories.PartyFactory;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import votersList.factories.VoterDataFactory;
import votersList.factories.VotersListFactory;
import votersList.model.IVoterData;
import votersList.model.IVotersList;
import fileHandler.logic.IReadSuppliedXML;

public class ReadSuppliedXMLStub implements IReadSuppliedXML {
	
	private IPartyFactory partyFactory = new PartyFactory();
	private IVoterDataFactory voterDataFactory = new VoterDataFactory();
	private IVotersListFactory votersListFactory = new VotersListFactory();
	private IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);

	private IVotersList readVotersList = votersListFactory.createInstance();
	private IPartiesList readPartiesList = partiesListFactory.createInstance();
	
	public void setReadVotersList(IVotersList v){
		readVotersList = v.copy();
	}
	
	public void setReadPartiesList(IPartiesList p){
		readPartiesList = p.copy();
	}
	
	
	@Override
	public IVotersList readVotersList() {
		return readVotersList.copy();
	}

	@Override
	public IPartiesList readPartiesList() {
		return readPartiesList.copy();
	}

}
