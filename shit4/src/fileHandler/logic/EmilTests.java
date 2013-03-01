package fileHandler.logic;

import partiesList.factories.PartiesListFactory;
import partiesList.factories.PartyFactory;
import partiesList.model.IPartiesList;
import votersList.factories.VoterDataFactory;
import votersList.factories.VotersListFactory;

public class EmilTests {

	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		testPartiesListRead();
	}*/
	
	
	
	public static void testPartiesListRead(){
		ReadSuppliedXML rs = new ReadSuppliedXML(new PartiesListFactory(new PartyFactory())
							, new PartyFactory()
							, new VotersListFactory()
							, new VoterDataFactory()
							, "initialData/voters.xml", "initialData/votingRecords.xml");
		IPartiesList pList = rs.readPartiesList();
		pList.peep();
		
		Backup b = new Backup(new PartiesListFactory(new PartyFactory())
		, new PartyFactory()
		, new VotersListFactory()
		, new VoterDataFactory()
		, "listBackup/VotersListBackup.xml"
		, "listBackup/PartiesListBackup.xml"
		, "unregisteredVoters/UnregisteredVoters.xml");
		
		b.storeState(pList, null, null);
		
		
		
		
	}

}
