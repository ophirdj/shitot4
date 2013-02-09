package choosingList;

public class ChoosingWindowFactory implements IChoosingWindowFactory {

	@Override
	public IChoosingWindow createInstance() {
		return new ChoosingList_window();
	}

}
