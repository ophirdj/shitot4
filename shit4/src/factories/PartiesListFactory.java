package factories;

import partiesList.IPartiesList;
import partiesList.PartiesList;

public class PartiesListFactory implements IPartiesListFactory {

	@Override
	public IPartiesList createInstance() {
		return new PartiesList();
	}

}
