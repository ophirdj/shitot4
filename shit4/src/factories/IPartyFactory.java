package factories;

import partiesList.IParty;

public interface IPartyFactory {
	/**
	 * 
	 * @param name
	 *            : the party name
	 * @param symbol
	 *            : the party symbol
	 * @param voteNumber
	 *            : amount of people that vote for this party
	 * @return a new object that implement IParty with given params.
	 */
	IParty createInstance(String name, String symbol, int voteNumber);

	/**
	 * 
	 * @param name
	 *            : the party name
	 * @param symbol
	 *            : the party symbol
	 * @return a new object that implement IParty with given params and
	 *         voteNumber = 0.
	 */
	IParty createInstance(String name, String symbol);
}
