package global.tests;

import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import fileHandler.factories.IReadSuppliedXMLFactory;
import fileHandler.logic.IReadSuppliedXML;
import fileHandler.logic.ReadSuppliedXML;

public class ReadSuppliedXMLTestFactory implements IReadSuppliedXMLFactory {

	private IPartiesListFactory partiesListFactory;
	private IPartyFactory partyFactory;
	private IVotersListFactory votersListFactory;
	private IVoterDataFactory voterDataFactory;
	
	public ReadSuppliedXMLTestFactory(IPartiesListFactory partiesListFactory,
			IPartyFactory partyFactory, IVotersListFactory votersListFactory,
			IVoterDataFactory voterDataFactory) {
		this.partiesListFactory = partiesListFactory;
		this.partyFactory = partyFactory;
		this.votersListFactory = votersListFactory;
		this.voterDataFactory = voterDataFactory;
	}

	@Override
	public IReadSuppliedXML createInstance() {
		String suppliedFolder = "acceptanceTest/initialData/";
		return new ReadSuppliedXML(partiesListFactory, partyFactory,
				votersListFactory, voterDataFactory, 
				suppliedFolder+ "voters.xml", suppliedFolder + "votingRecords.xml");
	}

}
