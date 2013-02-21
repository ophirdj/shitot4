package factories;

import GUI.Main_Window;
import mainframe.IMainframe;
import mainframe.IMainframeWindow;
import mainframe.MainframeWindow;

public class MainframeWindowFactory implements IMainframeWindowFactory {

	private Main_Window main_window;
	
	public MainframeWindowFactory(Main_Window main_window) {
		this.main_window = main_window;
	}
	
	@Override
	public IMainframeWindow createInstance(IMainframe callerStation) {
		return new MainframeWindow(callerStation,main_window);
	}

}
