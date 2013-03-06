package fileHandler.logic;

import partiesList.model.IPartiesList;
import votersList.model.IVotersList;

public interface IReadSuppliedXML {

	/**
	 * Read voters list from the initial file (voters.xml)
	 * @return the supplied voters list
	 */
	IVotersList readVotersList();
	
	
	/**
	 * Read parties list from the initial file (votingRecords.xml)
	 * @return the supplied parties list
	 */
	IPartiesList readPartiesList();
}
