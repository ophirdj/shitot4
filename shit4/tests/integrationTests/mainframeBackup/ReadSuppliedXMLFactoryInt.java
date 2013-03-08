package integrationTests.mainframeBackup;

import fileHandler.factories.IReadSuppliedXMLFactory;
import fileHandler.logic.IReadSuppliedXML;
import fileHandler.logic.ReadSuppliedXML;
import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;

/**
 * implementation of the interface IReadSuppliedXMLFactory
 * @author Emil
 *
 */
public class ReadSuppliedXMLFactoryInt implements IReadSuppliedXMLFactory {

	/**
	 * the parties list factory
	 */
	private IPartiesListFactory partiesListFactory;
	/**
	 * the party factory
	 */
	private IPartyFactory partyFactory;
	/**
	 * the voters list factory
	 */
	private IVotersListFactory voterListFactory;
	/**
	 * the voter's data factory
	 */
	private IVoterDataFactory voterDataFactory;

	/**
	 * 
	 * @param partiesListFactory the parties list factory
	 * @param partyFactory the party factory
	 * @param voterListFactory the voters list factory
	 * @param voterDataFactory the voter's data factory
	 */
	public ReadSuppliedXMLFactoryInt(IPartiesListFactory partiesListFactory,
			IPartyFactory partyFactory, IVotersListFactory voterListFactory,
			IVoterDataFactory voterDataFactory) {
		this.partiesListFactory = partiesListFactory;
		this.partyFactory = partyFactory;
		this.voterListFactory = voterListFactory;
		this.voterDataFactory = voterDataFactory;
	}

	@Override
	public IReadSuppliedXML createInstance() {
		String suppliedFolder = "IntegrationTests/mainframeBackup/";
		return new ReadSuppliedXML(partiesListFactory, partyFactory,
				voterListFactory, voterDataFactory, 
				suppliedFolder+ "voters.xml", suppliedFolder + "votingRecords.xml");
	}

}
