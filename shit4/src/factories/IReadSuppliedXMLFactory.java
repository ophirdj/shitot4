package factories;

import backupToXML.IReadSuppliedXML;

public interface IReadSuppliedXMLFactory {

	IReadSuppliedXML createInstance(IVoterDataFactory voterDataFactory,
			IVotersListFactory votersListFactory, IPartyFactory partyFactory,
			IPartiesListFactory partiesListFactory);
}
