package mainframe.factories;

import mainframe.gui.IMainframeWindow;
import mainframe.logic.IMainframe;

public interface IMainframeWindowFactory {

	IMainframeWindow createInstance(IMainframe callerStation);
}
