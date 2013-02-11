package factories;

import mainframe.IMainframe;
import mainframe.IMainframeWindow;

public interface IMainframeWindowFactory {

	IMainframeWindow createInstance(IMainframe callerStation);
}
