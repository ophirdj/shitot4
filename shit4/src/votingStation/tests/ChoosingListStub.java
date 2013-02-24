package votingStation.tests;

import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import choosingList.logic.IChoosingList;

public class ChoosingListStub implements IChoosingList {
	
	public IPartiesList partiesList;
	public IParty party;
	public boolean interrupt = false;
	public boolean retired = false;

	@Override
	public IParty chooseList() throws ChoosingInterruptedException {
		if(interrupt || party == null) throw new ChoosingInterruptedException();
		return party;
	}

	@Override
	public void retire() {
		retired = true;
	}

}
