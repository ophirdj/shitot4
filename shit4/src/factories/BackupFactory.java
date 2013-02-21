package factories;

import XML.Backup;
import XML.IBackup;

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
