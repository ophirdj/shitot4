package partiesList;

public class PartiesListFactory implements IPartiesListFactory {

	@Override
	public IPartiesList createInstance() {
		return new PartiesList();
	}

}
