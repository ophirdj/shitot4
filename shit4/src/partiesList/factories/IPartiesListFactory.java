package partiesList.factories;

import partiesList.model.IPartiesList;

/**
 * interface of factory of IPartiesList
 * @author Emil
 *
 */
public interface IPartiesListFactory {

	/**
	 * 
	 * @return a new object that implements the interface IPartiesList.
	 */
	IPartiesList createInstance();
}
