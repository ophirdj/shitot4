package votingStation;

public interface IVotingStationWindowFactory {
	/**
	 * 
	 * @param name: the view name in main window
	 * @param caller: caller station
	 * @return a new object that implement IVotingStationWindow
	 */
	IVotingStationWindow createInstance(String name, IVotingStation caller);
}
