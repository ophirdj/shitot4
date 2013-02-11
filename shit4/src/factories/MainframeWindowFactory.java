package factories;

import mainframe.IMainframe;
import mainframe.IMainframeWindow;
import mainframe.MainframeWindow;

public class MainframeWindowFactory implements IMainframeWindowFactory {

	@Override
	public IMainframeWindow createInstance(IMainframe callerStation) {
		return new MainframeWindow(callerStation);
	}

}
