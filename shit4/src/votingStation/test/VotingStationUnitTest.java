package votingStation.test;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


import mainframe.communication.IStationsController;

import org.junit.Test;

import choosingList.logic.IChoosingList.ChoosingInterruptedException;



import partiesList.factories.PartyFactory;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import partiesList.model.PartiesList;
import partiesList.model.Party;
import votingStation.logic.IVotingStation;
import votingStation.logic.VotingStation;
import global.gui.Main_Window;


public class VotingStationUnitTest {
	Main_Window window = new Main_Window();
	private List<String> passwords = Arrays.asList("password1", "password2", "password3");
	private IVotingStation votingStation; 
	private IPartiesList parties = new PartiesList(new PartyFactory());	
	private IStationsController stationsController = new StationsControllerStub();

	public VotingStationUnitTest() {
		votingStation = new VotingStation(passwords,
				new ChoosingListFactoryStub(), new VotingStationWindowFactoryStub());
		
	}
	
	@Test
	public void initialize_getPartiesList_Test(){
		parties.addParty(new Party("likoud","l"));
		parties.addParty(new Party("kadima","k"));
		parties.addParty(new Party("avoda","a"));
		votingStation.initialize(parties, stationsController);
		IPartiesList partiesToTest = votingStation.getPartiesList();
		Iterator<IParty> goodIterator = parties.iterator();
		Iterator<IParty> iteratorToTest = partiesToTest.iterator();
		while (goodIterator.hasNext()){
			assertEquals(goodIterator.next(),iteratorToTest.next());
		}
		assertFalse(iteratorToTest.hasNext());
	}
	
	@Test(expected=WrongPasswordError.class)
	public void wrong_password_testVoting_Test() throws ChoosingInterruptedException{
		VotingStationWindowStub.set_i(4);
		votingStation.testVoting();
	}
	
	@Test
	public void testVoting_Test1() throws ChoosingInterruptedException{
		parties.addParty(new Party("likoud","l"));
		parties.addParty(new Party("kadima","k"));
		parties.addParty(new Party("avoda","a"));
		votingStation.initialize(parties, stationsController);
		VotingStationWindowStub.set_i(1);
		Driver_Mainframe.setPlace(0);
		ChoosingListStub.setPartyNumber(0);
		votingStation.testVoting();
		assertEquals(VotingStationWindowStub.getMessage(),"You successfully test voted for the party likoud");
	}
	
	@Test(expected=UnidentifiedError.class)
	public void testVoting_Test2() throws ChoosingInterruptedException{
		parties.addParty(new Party("likoud","l"));
		parties.addParty(new Party("kadima","k"));
		parties.addParty(new Party("avoda","a"));
		votingStation.initialize(parties, stationsController);
		VotingStationWindowStub.set_i(2);
		Driver_Mainframe.setPlace(1);
		votingStation.testVoting();		
	}
	
	@Test(expected=WrongStationError.class)
	public void testVoting_Test3() throws ChoosingInterruptedException{
		parties.addParty(new Party("likoud","l"));
		parties.addParty(new Party("kadima","k"));
		parties.addParty(new Party("avoda","a"));
		votingStation.initialize(parties, stationsController);
		VotingStationWindowStub.set_i(3);
		Driver_Mainframe.setPlace(2);
		votingStation.testVoting();		
	}
	
	@Test(expected=UnidentifiedError.class)
	public void voting_Test1() throws ChoosingInterruptedException{
		parties.addParty(new Party("likoud","l"));
		parties.addParty(new Party("kadima","k"));
		parties.addParty(new Party("avoda","a"));
		votingStation.initialize(parties, stationsController);
		Driver_Mainframe.setPlace(1);
		votingStation.voting();		
	}
	
	@Test(expected=WrongStationError.class)
	public void voting_Test2() throws ChoosingInterruptedException{
		parties.addParty(new Party("likoud","l"));
		parties.addParty(new Party("kadima","k"));
		parties.addParty(new Party("avoda","a"));
		votingStation.initialize(parties, stationsController);
		Driver_Mainframe.setPlace(2);
		votingStation.voting();		
	}
	
	@Test(expected=UnchangeableVoteError.class)
	public void voting_Test3() throws ChoosingInterruptedException{
		parties.addParty(new Party("likoud","l"));
		parties.addParty(new Party("kadima","k"));
		parties.addParty(new Party("avoda","a"));
		votingStation.initialize(parties, stationsController);
		Driver_Mainframe.setPlace(0);
		ChoosingListStub.setPartyNumber(0);
		votingStation.voting();
		assertEquals(VotingStationWindowStub.getMessage(),"You successfully voted for the party likoud");
		Driver_Mainframe.setPlace(2);
		ChoosingListStub.setPartyNumber(1);
		votingStation.voting();
		assertEquals(VotingStationWindowStub.getMessage(),"You successfully voted for the party kadima");
		ChoosingListStub.setPartyNumber(2);
		votingStation.voting();
		assertEquals(VotingStationWindowStub.getMessage(),"You successfully voted for the party avoda");
		votingStation.voting();
	}	
	
	@Test(expected=UnchangeableVoteError.class)
	public void voting_Test4() throws ChoosingInterruptedException{
		parties.addParty(new Party("likoud","l"));
		parties.addParty(new Party("kadima","k"));
		parties.addParty(new Party("avoda","a"));
		votingStation.initialize(parties, stationsController);
		Driver_Mainframe.setPlace(0);
		votingStation.voting();
		Driver_Mainframe.setPlace(2);
		try {
			Thread.sleep(125000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		votingStation.voting();
	}	

}


