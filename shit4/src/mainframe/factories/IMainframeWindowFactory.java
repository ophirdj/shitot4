package mainframe.factories;

import mainframe.gui.IMainframeWindow;
import mainframe.logic.IMainframe;

public interface IMainframeWindowFactory {

	/**
	 * 
	 * @param callerStation
	 * @return a new instance of MainframeWindow
	 */
	IMainframeWindow createInstance(IMainframe callerStation);
}
