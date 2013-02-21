package factories;

import XML.IReadSuppliedXML;
import XML.ReadSuppliedXML;

public class ReadSuppliedXMLFactory implements IReadSuppliedXMLFactory {

	private IPartiesListFactory partiesListFactory;
	private IPartyFactory partyFactory;
	private IVotersListFactory voterListFactory;
	private IVoterDataFactory VoterDataFactory;

	public ReadSuppliedXMLFactory(IPartiesListFactory partiesListFactory,
	 IPartyFactory partyFactory,
	 IVotersListFactory voterListFactory,
	 IVoterDataFactory VoterDataFactory) {
		this.partiesListFactory = partiesListFactory;
		this.partyFactory = partyFactory;
		this.voterListFactory = voterListFactory;
		this.VoterDataFactory = VoterDataFactory;
	}
	
	@Override
	public IReadSuppliedXML createInstance() {
		return new ReadSuppliedXML(partiesListFactory,partyFactory,voterListFactory,VoterDataFactory,"voters.XML","votingRecords.xml"); 
	}

}
