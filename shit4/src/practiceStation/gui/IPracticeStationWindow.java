package practiceStation.gui;

import global.dictionaries.Messages;
import global.gui.IWindow;
import global.gui.Main_Window;
import global.gui.StationPanel;

/**
 * Interface for practice station window
 * @author Ophir De Jager
 *
 */
public abstract class IPracticeStationWindow extends StationPanel implements IWindow{
	
	/**
	 * For tests only
	 */
	public IPracticeStationWindow(){
		super();
	}
	
	public IPracticeStationWindow(Messages practiceStation, int id,
			Main_Window main_window) {
		super(practiceStation, id, main_window);
	}

	private static final long serialVersionUID = 1L;
}
