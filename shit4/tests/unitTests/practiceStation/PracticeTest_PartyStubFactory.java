package unitTests.practiceStation;

import partiesList.factories.IPartyFactory;
import partiesList.model.IParty;

public class PracticeTest_PartyStubFactory implements IPartyFactory {

	@Override
	public IParty createInstance(String name, String symbol, int voteNumber) {
		return new PracticeTest_PartyStub(name);
	}

	@Override
	public IParty createInstance(String name, String symbol) {
		return new PracticeTest_PartyStub(name);
	}

}
