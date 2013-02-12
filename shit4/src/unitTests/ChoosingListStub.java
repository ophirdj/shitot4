package unitTests;

import partiesList.IParty;
import partiesList.Party;
import choosingList.IChoosingList;

public class ChoosingListStub implements IChoosingList{

	@Override
	public IParty chooseList() throws ChoosingInterruptedException {
		return new Party("likoud","l");
	}

	@Override
	public void retire() {
		// TODO Auto-generated method stub
		
	}

}
