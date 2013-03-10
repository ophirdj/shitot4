package votersList.factories;

import votersList.model.IVotersList;

/**
 * interface of factory of IVotersList
 * @author Emil
 *
 */
public interface IVotersListFactory {
	/**
	 * 
	 * @return a new object that implements the interface IVotersList.
	 */
	IVotersList createInstance();
}
