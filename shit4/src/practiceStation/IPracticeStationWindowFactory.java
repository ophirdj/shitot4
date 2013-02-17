package practiceStation;


public interface IPracticeStationWindowFactory {
	/**
	 * 
	 * @param name
	 *            : the view name in main window
	 * @param caller
	 *            : caller station
	 * @return a new object that implement IPracticeStationWindow
	 */
	IPracticeStationWindow createInstance(String name, IPracticeStation caller);
}
