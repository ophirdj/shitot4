package factories;

import choosingList.ChoosingList_window;
import choosingList.IChoosingWindow;

public class ChoosingWindowFactory implements IChoosingWindowFactory {

	@Override
	public IChoosingWindow createInstance() {
		return new ChoosingList_window();
	}

}
