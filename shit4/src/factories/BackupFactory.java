package factories;

import backup.Backup;
import backup.IBackup;

public class BackupFactory implements IBackupFactory {

	@Override
	public IBackup createInstance(IPartiesListFactory partiesListFactory,
			IPartyFactory partyFactory, IVotersListFactory voterListFactory,
			IVoterDataFactory voterDataFactory) {
		return new Backup(partiesListFactory, partyFactory, voterListFactory, voterDataFactory);
	}

}
