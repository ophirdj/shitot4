package practiceStation.gui;

import practiceStation.logic.PracticeStationAction;
import global.gui.IWindow;

/**
 * Interface for practice station window
 * @author Ophir De Jager
 *
 */
public interface IPracticeStationWindow extends IWindow{

	void setAction(PracticeStationAction action);
}
