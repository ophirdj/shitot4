package unitTests;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import partiesList.*;
import votingStation.*;


public class VotingStationUnitTest {
	private List<String> passwords = Arrays.asList("password1", "password2", "password3");
	private IVotingStation votingStation = new VotingStation(passwords,"voting station 1",new ChoosingListFactoryStub(), new ChoosingWindowFactoryStub(), new VotingStationWindowFactoryStub());
	private IPartiesList parties = new PartiesList();	

	@Test
	public void initialize_getPartiesList_Test(){
		parties.addParty(new Party("likoud","l"));
		parties.addParty(new Party("kadima","k"));
		parties.addParty(new Party("avoda","a"));
		votingStation.initialize(parties, new Driver_StationsContoller());
		IPartiesList partiesToTest = votingStation.getPartiesList();
		Iterator<IParty> goodIterator = parties.iterator();
		Iterator<IParty> iteratorToTest = partiesToTest.iterator();
		while (goodIterator.hasNext()){
			assertEquals(goodIterator.next(),iteratorToTest.next());
		}
		assertFalse(iteratorToTest.hasNext());
	}


}


