package factories;

import XML.Backup;
import XML.IBackup;

public class BackupFactory implements IBackupFactory {

	@Override
	public IBackup createInstance(IPartiesListFactory partiesListFactory,
			IPartyFactory partyFactory, IVotersListFactory voterListFactory,
			IVoterDataFactory voterDataFactory/*,
			String backupedVotersListFile, String backupedPartiesListFile,
			  String unregisteredVotersFile*/) {
			 
		return new Backup(partiesListFactory, partyFactory, voterListFactory,
				voterDataFactory/*,
				backupedVotersListFile, backupedPartiesListFile, unregisteredVotersFile*/
				,"VotersListBackup.xml", "PartiesListBackup.xml", "UnregisteredVoters.xml");
	}

}
