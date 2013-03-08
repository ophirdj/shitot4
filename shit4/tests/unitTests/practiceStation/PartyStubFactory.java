package unitTests.practiceStation;

import partiesList.factories.IPartyFactory;
import partiesList.model.IParty;

public class PartyStubFactory implements IPartyFactory {

	@Override
	public IParty createInstance(String name, String symbol, int voteNumber) {
		return new PartyStub(name);
	}

	@Override
	public IParty createInstance(String name, String symbol) {
		return new PartyStub(name);
	}

}
