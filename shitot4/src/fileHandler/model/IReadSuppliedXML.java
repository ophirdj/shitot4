package fileHandler.model;

import partiesList.model.IPartiesList;
import votersList.model.IVotersList;

/**
 * interface of the class which provides the reading of the supplied XML files
 * @author Emil
 *
 */
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
