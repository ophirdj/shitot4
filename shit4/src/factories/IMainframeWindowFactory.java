package factories;

import GUI.Main_Window;
import mainframe.IMainframe;
import mainframe.IMainframeWindow;

public interface IMainframeWindowFactory {

	IMainframeWindow createInstance(IMainframe callerStation, Main_Window window);
}
