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
	private IVoterDataFactory voterDataFactory;

	/**
	 * 
	 * @param partiesListFactory the parties list factory
	 * @param partyFactory the party factory
	 * @param voterListFactory the voters list factory
	 * @param voterDataFactory the voter's data factory
	 */
	public ReadSuppliedXMLFactory(IPartiesListFactory partiesListFactory,
			IPartyFactory partyFactory, IVotersListFactory voterListFactory,
			IVoterDataFactory voterDataFactory) {
		this.partiesListFactory = partiesListFactory;
		this.partyFactory = partyFactory;
		this.voterListFactory = voterListFactory;
		this.voterDataFactory = voterDataFactory;
	}

	@Override
	public IReadSuppliedXML createInstance() {
		String suppliedFolder = "initialData/";
		return new ReadSuppliedXML(partiesListFactory, partyFactory,
				voterListFactory, voterDataFactory, 
				suppliedFolder+ "voters.xml", suppliedFolder + "votingRecords.xml");
	}

}
