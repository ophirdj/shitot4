package fileHandler.logic;

import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import partiesList.model.IPartiesList;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import votersList.model.IVotersList;

/**
 * the class which provides the reading of the supplied XML files
 * @author Emil
 *
 */
public class ReadSuppliedXML implements IReadSuppliedXML {
	
	/*
	 * these strings will hold the path to all needed the XML files
	 */
	
	/**
	 * the location of the supplied voters list XML file
	 */
	private String suppliedVotersListFile;
	/**
	 * the location of the supplied parties list XML file
	 */
	private String suppliedPartiesListFile;
	/**
	 * allows us to use the methods of the class ReadXMLFile
	 */
	private ReadXMLFile readService;
	
	/**
	 * 
	 * @param partiesListFactory the parties list factory
	 * @param partyFactory the party factory
	 * @param votersListFactory the voters list factory
	 * @param voterDataFactory the voter's data factory
	 * @param suppliedVotersListFile the location of the supplied voters list XML file
	 * @param suppliedPartiesListFile the location of the supplied parties list XML file
	 */
	public ReadSuppliedXML(IPartiesListFactory partiesListFactory,
			IPartyFactory partyFactory, IVotersListFactory votersListFactory,
			IVoterDataFactory voterDataFactory,
			String suppliedVotersListFile, String suppliedPartiesListFile){
		
		/*
		 * initializing the XML files names
		 */
		this.suppliedVotersListFile = suppliedVotersListFile;
		this.suppliedPartiesListFile = suppliedPartiesListFile;
		
		this.readService = new ReadXMLFile(partiesListFactory, partyFactory, votersListFactory, voterDataFactory);
		
	}

	@Override
	public IVotersList readVotersList() {
		return this.readService.readSuppliedVotersListXML(suppliedVotersListFile);
	}

	@Override
	public IPartiesList readPartiesList() {
		return this.readService.readSuppliedVotingRecordsXML(suppliedPartiesListFile);
	}

}
