package votersList;

public class VotersListFactory implements IVotersListFactory{

	@Override
	public IVotersList createInstance() {
		return new VotersList();
	}

}
