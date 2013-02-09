package factories;

import partiesList.IParty;
import partiesList.Party;

public class PartyFactory implements IPartyFactory {

	@Override
	public IParty createInstance(String name, String symbol, int voteNumber) {
		return new Party(name, symbol, voteNumber);
	}

	@Override
	public IParty createInstance(String name, String symbol) {
		return new Party(name, symbol);
	}

}
