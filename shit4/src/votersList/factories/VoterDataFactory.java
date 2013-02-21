package votersList.factories;

import votersList.model.IVoterData;
import votersList.model.VoterData;

public class VoterDataFactory implements IVoterDataFactory {

	@Override
	public IVoterData createInstance(int id) {
		return new VoterData(id);
	}

}
