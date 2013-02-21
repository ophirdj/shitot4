package factories;

import partiesList.IPartiesList;
import partiesList.PartiesList;

public class PartiesListFactory implements IPartiesListFactory {
	
	private IPartyFactory partyFactory;

	public PartiesListFactory(IPartyFactory partyFactory) {
		this.partyFactory = partyFactory;
	}
	
	@Override
	public IPartiesList createInstance() {
		return new PartiesList(partyFactory);
	}

}
