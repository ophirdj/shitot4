package mainframe.factories;

import global.gui.Main_Window;
import mainframe.gui.IMainframeWindow;
import mainframe.gui.MainframeWindow;
import mainframe.logic.IMainframe;

/**
 * Factory of the MainframeWindow
 * @author Emil
 *
 */
public class MainframeWindowFactory implements IMainframeWindowFactory {

	private Main_Window main_window;
	
	/**
	 * 
	 * @param main_window
	 */
	public MainframeWindowFactory(Main_Window main_window) {
		this.main_window = main_window;
	}
	
	@Override
	public IMainframeWindow createInstance(IMainframe callerStation) {
		return new MainframeWindow(callerStation,main_window);
	}

}
