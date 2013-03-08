package integrationTests.mainframeBackup;

import fileHandler.factories.IBackupFactory;
import fileHandler.logic.Backup;
import fileHandler.logic.IBackup;
import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;

/**
 * a factory of the backup object
 * @author Emil
 *
 */
public class BackupFactoryInt implements IBackupFactory {

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
	public BackupFactoryInt(IPartiesListFactory partiesListFactory,
			IPartyFactory partyFactory, IVotersListFactory voterListFactory,
			IVoterDataFactory voterDataFactory) {
		
		this.partiesListFactory=partiesListFactory;
		this.partyFactory=partyFactory;
		this.voterListFactory=voterListFactory;
		this.voterDataFactory=voterDataFactory;
	}
	
	@Override
	public IBackup createInstance() {
		
		String backupFolder = "IntegrationTests/mainframeBackup/listBackup/";
		String unregFolder = "IntegrationTests/mainframeBackup/unregisteredVoters/";
		return new Backup(partiesListFactory, partyFactory, voterListFactory,
				voterDataFactory,
				backupFolder+"VotersListBackup.xml", backupFolder+"PartiesListBackup.xml", unregFolder+"UnregisteredVoters.xml");
	}

}
