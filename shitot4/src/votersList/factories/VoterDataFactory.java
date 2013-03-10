package votersList.factories;

import votersList.model.IVoterData;
import votersList.model.VoterData;

/**
 * interface of voter data
 * @author Emil
 *
 */
public class VoterDataFactory implements IVoterDataFactory {

	@Override
	public IVoterData createInstance(int id) {
		return new VoterData(id);
	}

}
