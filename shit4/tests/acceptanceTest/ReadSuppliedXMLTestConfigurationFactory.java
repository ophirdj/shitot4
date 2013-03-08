package acceptanceTest;

import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import fileHandler.factories.IReadSuppliedXMLFactory;
import fileHandler.logic.IReadSuppliedXML;
import fileHandler.logic.ReadSuppliedXML;

public class ReadSuppliedXMLTestConfigurationFactory implements IReadSuppliedXMLFactory {

	private IPartiesListFactory partiesListFactory;
	private IPartyFactory partyFactory;
	private IVotersListFactory votersListFactory;
	private IVoterDataFactory voterDataFactory;
	
	public ReadSuppliedXMLTestConfigurationFactory(IPartiesListFactory partiesListFactory,
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
