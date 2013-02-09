package factories;

import votersList.IVoterData;
import votersList.VoterData;

public class VoterDataFactory implements IVoterDataFactory {

	@Override
	public IVoterData createInstance(int id) {
		return new VoterData(id);
	}

}
