package factories;

import XML.IBackup;

public interface IBackupFactory {
	/**
	 * 
	 * @param partiesListFactory
	 *            : factory to create IPartiesList
	 * @param partyFactory
	 *            : factory to create IParty
	 * @param voterListFactory
	 *            : factory to create IVotersList
	 * @param voterDataFactory
	 *            : factory to create IVoterData
	 * @param backupedVotersListFile
	 * 			  : path to the file which holds the backup to the voters list
	 * @param backupedPartiesListFile
	 * 			  : path to the file which holds the backup to the parties list
	 * @return new object that implement IBackup
	 */
	IBackup createInstance(IPartiesListFactory partiesListFactory,
			IPartyFactory partyFactory, IVotersListFactory votersListFactory,
			IVoterDataFactory voterDataFactory,
			String backupedVotersListFile, String backupedPartiesListFile);
}
