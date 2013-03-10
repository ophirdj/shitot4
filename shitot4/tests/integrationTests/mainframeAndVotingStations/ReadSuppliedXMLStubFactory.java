package integrationTests.mainframeAndVotingStations;

import partiesList.model.IPartiesList;
import votersList.model.IVotersList;
import fileHandler.factories.IReadSuppliedXMLFactory;
import fileHandler.model.IReadSuppliedXML;

public class ReadSuppliedXMLStubFactory implements IReadSuppliedXMLFactory{
	
	private ReadSuppliedXMLStub readerStub;
	
	
	public ReadSuppliedXMLStubFactory() {
		readerStub = new ReadSuppliedXMLStub();
	}
	
	public void setVoterList(IVotersList voters){
		readerStub.setVotersList(voters);
	}
	
	public void setPartiesList(IPartiesList parties){
		readerStub.setPartiesList(parties);
	}
	
	@Override
	public IReadSuppliedXML createInstance() {
		return readerStub;
	}

}
