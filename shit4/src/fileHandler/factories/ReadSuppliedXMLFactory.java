package fileHandler.factories;

import fileHandler.logic.IReadSuppliedXML;
import fileHandler.logic.ReadSuppliedXML;
import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;

public class ReadSuppliedXMLFactory implements IReadSuppliedXMLFactory {

	private IPartiesListFactory partiesListFactory;
	private IPartyFactory partyFactory;
	private IVotersListFactory voterListFactory;
	private IVoterDataFactory VoterDataFactory;

	public ReadSuppliedXMLFactory(IPartiesListFactory partiesListFactory,
			IPartyFactory partyFactory, IVotersListFactory voterListFactory,
			IVoterDataFactory VoterDataFactory) {
		this.partiesListFactory = partiesListFactory;
		this.partyFactory = partyFactory;
		this.voterListFactory = voterListFactory;
		this.VoterDataFactory = VoterDataFactory;
	}

	@Override
	public IReadSuppliedXML createInstance() {
		String suppliedFolder = "initialData/";
		return new ReadSuppliedXML(partiesListFactory, partyFactory,
				voterListFactory, VoterDataFactory, 
				suppliedFolder+ "voters.xml", suppliedFolder + "votingRecords.xml");
	}

}
