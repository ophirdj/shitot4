package votingStation.test;

import choosingList.logic.IChoosingList;
import partiesList.model.IParty;
import partiesList.model.Party;

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
