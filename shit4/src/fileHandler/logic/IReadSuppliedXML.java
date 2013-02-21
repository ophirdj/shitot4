package fileHandler.logic;

import partiesList.model.IPartiesList;
import votersList.model.IVotersList;

public interface IReadSuppliedXML {

	/**
	 * Read voters list from the initial file (voters.xml)
	 * @return
	 */
	IVotersList readVotersList();
	
	
	/**
	 * Read parties list from the initial file (votingRecords.xml)
	 * @return
	 */
	IPartiesList readPartiesList();
}
