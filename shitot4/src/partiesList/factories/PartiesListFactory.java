package partiesList.factories;

import partiesList.model.IPartiesList;
import partiesList.model.PartiesList;

/**
 * factory of PartiesList
 *
 */
public class PartiesListFactory implements IPartiesListFactory {
	
	/**
	 * factory of party 
	 */
	private IPartyFactory partyFactory;

	/**
	 * builds a factory for parties list
	 * @param partyFactory factory of party
	 */
	public PartiesListFactory(IPartyFactory partyFactory) {
		this.partyFactory = partyFactory;
	}
	
	@Override
	public IPartiesList createInstance() {
		return new PartiesList(partyFactory);
	}

}
