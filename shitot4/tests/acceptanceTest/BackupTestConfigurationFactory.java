package acceptanceTest;

import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import fileHandler.factories.IBackupFactory;
import fileHandler.model.Backup;
import fileHandler.model.IBackup;

public class BackupTestConfigurationFactory implements IBackupFactory {

	private IPartiesListFactory partiesListFactory;
	private IPartyFactory partyFactory;
	private IVotersListFactory voterListFactory;
	private IVoterDataFactory voterDataFactory;
	private String backupVotersListFile;
	private String backupPartiesListFile;
	private String backupUnregisteredFile;

	public BackupTestConfigurationFactory(IPartiesListFactory partiesListFactory,
			IPartyFactory partyFactory, IVotersListFactory voterListFactory,
			IVoterDataFactory voterDataFactory, String backupVotersListFile,
			String backupPartiesListFile, String backupUnregisteredFile) {

		this.partiesListFactory = partiesListFactory;
		this.partyFactory = partyFactory;
		this.voterListFactory = voterListFactory;
		this.voterDataFactory = voterDataFactory;
		this.backupVotersListFile = backupVotersListFile;
		this.backupPartiesListFile = backupPartiesListFile;
		this.backupUnregisteredFile = backupUnregisteredFile;
	}

	@Override
	public IBackup createInstance() {
		return new Backup(partiesListFactory, partyFactory, voterListFactory,
				voterDataFactory, backupVotersListFile,
				backupPartiesListFile, backupUnregisteredFile);
	}

}