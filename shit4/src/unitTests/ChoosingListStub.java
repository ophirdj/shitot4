package unitTests;

import partiesList.IParty;
import partiesList.Party;
import choosingList.IChoosingList;

public class ChoosingListStub implements IChoosingList{

	private static int partyNumber;
	@Override
	public IParty chooseList() throws ChoosingInterruptedException {
		switch (partyNumber){
		case 0:
			return new Party("likoud","l");
		case 1:
			return new Party("kadima","k");
		case 2:
			return new Party("avoda","a");
		}
		return null;
	}

	@Override
	public void retire() {
	}

	public static void setPartyNumber(int partyNumber){
		ChoosingListStub.partyNumber = partyNumber;
	}

}
