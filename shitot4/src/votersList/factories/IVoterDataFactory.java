package votersList.factories;

import votersList.model.IVoterData;

/**
 * interface of factory of IVoterData
 * @author Emil
 *
 */
public interface IVoterDataFactory {
	/**
	 * 
	 * @param id
	 *            : the voter identification number
	 * @return a new object that implement IVoterData
	 */
	IVoterData createInstance(int id);
}
