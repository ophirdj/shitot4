package factories;

import backup.IReadSuppliedXML;


public interface IReadSuppliedXMLFactory {

	IReadSuppliedXML createInstance(IVoterDataFactory voterDataFactory, IVotersListFactory votersListFactory, IPartyFactory partyFactory, IPartiesListFactory partiesListFactory);
}
