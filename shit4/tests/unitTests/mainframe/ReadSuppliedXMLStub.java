package unitTests.mainframe;

import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import partiesList.factories.PartiesListFactory;
import partiesList.factories.PartyFactory;
import partiesList.model.IPartiesList;
import votersList.factories.IVotersListFactory;
import votersList.factories.VotersListFactory;
import votersList.model.IVotersList;
import fileHandler.logic.IReadSuppliedXML;

/**
 * this is the stub of the class ReadSuppliedXML
 * this stub allows us set the lists that the methods of ReadSuppliedXML will return
 * @author Emil
 *
 */
public class ReadSuppliedXMLStub implements IReadSuppliedXML {
	
	private IPartyFactory partyFactory = new PartyFactory();
	private IVotersListFactory votersListFactory = new VotersListFactory();
	private IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);

	private IVotersList readVotersList = votersListFactory.createInstance();
	private IPartiesList readPartiesList = partiesListFactory.createInstance();
	
	/**
	 * set the voters list that the method readVotersList will return
	 * @param v the above voters list
	 */
	public void setReadVotersList(IVotersList v){
		readVotersList = v.copy();
	}
	
	/**
	 * set the parties list that the method readPartiesList will return
	 * @param p the above parties list
	 */
	public void setReadPartiesList(IPartiesList p){
		readPartiesList = p.copy();
	}
	
	
	@Override
	public IVotersList readVotersList() {
		return readVotersList.copy();
	}

	@Override
	public IPartiesList readPartiesList() {
		return readPartiesList.copy();
	}

}
