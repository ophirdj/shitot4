package mainframe.factories;

import mainframe.logic.IMainframe;

/**
 * Interface of the Mainframe factory
 * @author Emil
 *
 */
public interface IMainframeFactory {

	/**
	 * 
	 * @return new instance of IMainframe
	 */
	IMainframe createInstance();
}
