package votersList.factories;

import votersList.model.IVotersList;

public interface IVotersListFactory {
	/**
	 * 
	 * @return a new object that implements the interface IVotersList.
	 */
	IVotersList createInstance();
}
