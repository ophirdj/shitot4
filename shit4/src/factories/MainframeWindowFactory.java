package factories;

import GUI.Main_Window;
import mainframe.IMainframe;
import mainframe.IMainframeWindow;
import mainframe.MainframeWindow;

public class MainframeWindowFactory implements IMainframeWindowFactory {

	@Override
	public IMainframeWindow createInstance(IMainframe callerStation, Main_Window window) {
		return new MainframeWindow(callerStation, window);
	}

}
