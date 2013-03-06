package partiesList.factories;

import partiesList.model.IParty;
import partiesList.model.Party;

/**
 * factory of party
 *
 */
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
