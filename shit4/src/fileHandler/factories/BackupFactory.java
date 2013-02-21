package fileHandler.factories;

import fileHandler.logic.Backup;
import fileHandler.logic.IBackup;
import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;

public class BackupFactory implements IBackupFactory {

	private IPartiesListFactory partiesListFactory;
	private IPartyFactory partyFactory;
	private IVotersListFactory voterListFactory;
	private IVoterDataFactory voterDataFactory;
	
	public BackupFactory(IPartiesListFactory partiesListFactory,
			IPartyFactory partyFactory, IVotersListFactory voterListFactory,
			IVoterDataFactory voterDataFactory) {
		
		this.partiesListFactory=partiesListFactory;
		this.partyFactory=partyFactory;
		this.voterListFactory=voterListFactory;
		this.voterDataFactory=voterDataFactory;
	}
	
	@Override
	public IBackup createInstance(
			/*,String backupedVotersListFile, String backupedPartiesListFile,
			  String unregisteredVotersFile*/
			) {
			 
		return new Backup(partiesListFactory, partyFactory, voterListFactory,
				voterDataFactory,
				/*backupedVotersListFile, backupedPartiesListFile, unregisteredVotersFile*/
				"VotersListBackup.xml", "PartiesListBackup.xml", "UnregisteredVoters.xml");
	}

}
