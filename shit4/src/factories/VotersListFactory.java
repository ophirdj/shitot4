package factories;

import votersList.IVotersList;
import votersList.VotersList;

public class VotersListFactory implements IVotersListFactory{

	@Override
	public IVotersList createInstance() {
		return new VotersList();
	}

}
