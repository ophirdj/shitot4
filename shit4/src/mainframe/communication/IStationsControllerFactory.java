package mainframe.communication;

import mainframe.logic.IMainframe;

/**
 * Interface for IStationsController factory
 * 
 * Implementation of this interface configures the number of
 * voting stations and the passwords for test votes
 * @author Ophir De Jager
 *
 */
public interface IStationsControllerFactory {

	/**
	 * 
	 * @param mainframe: mainframe that will use the returned controller
	 * @return a new IStationsController object
	 */
	IStationsController createInstance(IMainframe mainframe);
}
