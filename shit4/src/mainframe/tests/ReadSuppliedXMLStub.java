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
	
	IPartyFactory partyFactory = new PartyFactory();
	IVoterDataFactory voterDataFactory = new VoterDataFactory();
	IVotersListFactory votersListFactory = new VotersListFactory();
	IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);

	IVotersList readVotersList = votersListFactory.createInstance();
	IPartiesList readPartiesList = partiesListFactory.createInstance();
	
	public void setReadVotersList(IVotersList v){
		readVotersList = v;
	}
	
	public void setReadPartiesList(IPartiesList p){
		readPartiesList = p;
	}
	
	
	@Override
	public IVotersList readVotersList() {
		return readVotersList;
	}

	@Override
	public IPartiesList readPartiesList() {
		return readPartiesList;
	}

}
