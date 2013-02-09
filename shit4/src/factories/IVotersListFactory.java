package factories;

import votersList.IVotersList;

public interface IVotersListFactory {
	/**
	 * 
	 * @return a new object that implements the interface IVotersList.
	 */
	IVotersList createInstance();
}
