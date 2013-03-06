package mainframe.factories;

import mainframe.logic.IMainframe;

public interface IMainframeFactory {

	/**
	 * 
	 * @return new instance of Mainframe
	 */
	IMainframe createInstance();
}
