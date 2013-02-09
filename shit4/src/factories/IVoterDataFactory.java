package factories;

import votersList.IVoterData;

public interface IVoterDataFactory {
	/**
	 * 
	 * @param id
	 *            : the voter identification number
	 * @return a new object that implement IVoterData
	 */
	IVoterData createInstance(int id);
}
