package votersList.factories;

import votersList.model.IVotersList;
import votersList.model.VotersList;

/**
 * factory of voters list
 */
public class VotersListFactory implements IVotersListFactory {

	@Override
	public IVotersList createInstance() {
		return new VotersList();
	}

}
