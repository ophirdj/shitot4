package factories;

import backupToXML.Backup;
import backupToXML.IBackup;

public class BackupFactory implements IBackupFactory {

	@Override
	public IBackup createInstance(IPartiesListFactory partiesListFactory,
			IPartyFactory partyFactory, IVotersListFactory voterListFactory,
			IVoterDataFactory voterDataFactory) {
		return new Backup(partiesListFactory, partyFactory, voterListFactory,
				voterDataFactory);
	}

}
