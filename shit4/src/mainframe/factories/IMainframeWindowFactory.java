package mainframe.factories;

import mainframe.gui.IMainframeWindow;
import mainframe.logic.IMainframe;

/**
 * Interface of the MainframeWindow factory
 * @author Emil
 *
 */
public interface IMainframeWindowFactory {

	/**
	 * 
	 * @param callerStation
	 * @return a new instance of IMainframeWindow
	 */
	IMainframeWindow createInstance(IMainframe callerStation);
}
