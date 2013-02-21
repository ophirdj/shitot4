package partiesList.factories;

import partiesList.model.IPartiesList;

public interface IPartiesListFactory {

	/**
	 * 
	 * @return a new object that implements the interface IPartiesList.
	 */
	IPartiesList createInstance();
}
