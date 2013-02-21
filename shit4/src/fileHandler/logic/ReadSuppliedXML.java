package fileHandler.logic;

import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import partiesList.model.IPartiesList;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import votersList.model.IVotersList;

public class ReadSuppliedXML implements IReadSuppliedXML {
	
	/*
	 * the needed factories
	 */
	/*private IPartiesListFactory partiesListFactory;
	private IPartyFactory partyFactory;
	private IVotersListFactory votersListFactory;
	private IVoterDataFactory voterDataFactory;*/
	
	/*
	 * these strings will hold the path to all needed the XML files
	 */
	private String suppliedVotersListFile;
	private String suppliedPartiesListFile;
	
	
	private ReadXMLFile readService;
	
	public ReadSuppliedXML(IPartiesListFactory partiesListFactory,
			IPartyFactory partyFactory, IVotersListFactory votersListFactory,
			IVoterDataFactory voterDataFactory,
			String suppliedVotersListFile, String suppliedPartiesListFile){
		/*
		 * initializing the factories
		 */
		/*this.partiesListFactory = partiesListFactory;
		this.partyFactory = partyFactory;
		this.voterDataFactory = voterDataFactory;
		this.votersListFactory = votersListFactory;*/
		
		/*
		 * initializing the XML files names
		 */
		this.suppliedVotersListFile = suppliedVotersListFile;
		this.suppliedPartiesListFile = suppliedPartiesListFile;
		/*this.unregisteredVotersFile = unregisteredVotersFile;
		this.backupedVotersListFile = backupedVotersListFile;
		this.backupedPartiesListFile = backupedPartiesListFile;*/
		
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
