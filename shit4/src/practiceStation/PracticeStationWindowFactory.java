package practiceStation;

public class PracticeStationWindowFactory implements IPracticeStationWindowFactory{
	@Override
	public IPracticeStationWindow createInstance(String name,
			IPracticeStation caller) {
		return new PracticeStationWindow(name,caller);
	}
}
