package mainframe.factories;

import mainframe.logic.IMainframe;

/**
 * the interface of the Mainframe factory
 * @author Emil
 *
 */
public interface IMainframeFactory {

	/**
	 * 
	 * @return new instance of Mainframe
	 */
	IMainframe createInstance();
}
