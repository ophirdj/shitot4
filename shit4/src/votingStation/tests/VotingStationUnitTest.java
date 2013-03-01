package votingStation.tests;

import global.dictionaries.Messages;

import java.util.ArrayList;
import java.util.List;

import mainframe.logic.IMainframe.VoterStatus;

import org.junit.*;

import choosingList.logic.IChoosingList.ChoosingInterruptedException;
import partiesList.factories.PartyFactory;
import partiesList.model.IPartiesList;
import partiesList.model.IPartiesList.PartyDoesNotExist;
import partiesList.model.PartiesList;
import partiesList.model.Party;

import votingStation.logic.VotingStation;

public class VotingStationUnitTest {

	ChoosingListStub chooseStub;
	VotingStationWindowStub windowStub;
	List<String> passwordsStub;
	IPartiesList partiesStub;
	StationsControllerStub controllerStub;

	VotingStation station;

	@Before
	public void preprocessing() {
		chooseStub = new ChoosingListStub();
		windowStub = new VotingStationWindowStub();
		passwordsStub = new ArrayList<String>();
		
		passwordsStub.add("password1");
		passwordsStub.add("password2");
		passwordsStub.add("password3");
		
		
		partiesStub = new PartiesList(new PartyFactory());
		partiesStub.addParty(new Party("p1", "p1", 0));
		partiesStub.addParty(new Party("p2", "p2", 5));
		partiesStub.addParty(new Party("p3", "p3", 23));
		partiesStub.addParty(new Party("p4", "p4", 12));
		partiesStub.addParty(new Party("p5", "p5", 18));
		partiesStub.addParty(new Party("p6", "p6", 9));
		
		controllerStub = new StationsControllerStub();
		
		
		station = new VotingStation(passwordsStub, new choosingListStubFactory(
				chooseStub), new stationWindowStubFactory(windowStub));
		
		station.initialize(partiesStub, controllerStub);
	}
	
	@After
	public void intariants() throws PartyDoesNotExist{
		Assert.assertEquals("p1", partiesStub.getPartyBySymbol("p1")
				.getName());
		Assert.assertEquals("p2", partiesStub.getPartyBySymbol("p2")
				.getName());
		Assert.assertEquals("p3", partiesStub.getPartyBySymbol("p3")
				.getName());
		Assert.assertEquals("p4", partiesStub.getPartyBySymbol("p4")
				.getName());
		Assert.assertEquals("p5", partiesStub.getPartyBySymbol("p5")
				.getName());
		Assert.assertEquals("p6", partiesStub.getPartyBySymbol("p6")
				.getName());

		Assert.assertEquals(0, partiesStub.getPartyBySymbol("p1")
				.getVoteNumber());
		Assert.assertEquals(5, partiesStub.getPartyBySymbol("p2")
				.getVoteNumber());
		Assert.assertEquals(23, partiesStub.getPartyBySymbol("p3")
				.getVoteNumber());
		Assert.assertEquals(12, partiesStub.getPartyBySymbol("p4")
				.getVoteNumber());
		Assert.assertEquals(18, partiesStub.getPartyBySymbol("p5")
				.getVoteNumber());
		Assert.assertEquals(9, partiesStub.getPartyBySymbol("p6")
				.getVoteNumber());
	}

	@Test
	public void testInitialize() throws Exception {
		IPartiesList stationParties = station.getPartiesList();

		Assert.assertEquals("p1", stationParties.getPartyBySymbol("p1")
				.getName());
		Assert.assertEquals("p2", stationParties.getPartyBySymbol("p2")
				.getName());
		Assert.assertEquals("p3", stationParties.getPartyBySymbol("p3")
				.getName());
		Assert.assertEquals("p4", stationParties.getPartyBySymbol("p4")
				.getName());
		Assert.assertEquals("p5", stationParties.getPartyBySymbol("p5")
				.getName());
		Assert.assertEquals("p6", stationParties.getPartyBySymbol("p6")
				.getName());

		Assert.assertEquals(0, stationParties.getPartyBySymbol("p1")
				.getVoteNumber());
		Assert.assertEquals(5, stationParties.getPartyBySymbol("p2")
				.getVoteNumber());
		Assert.assertEquals(23, stationParties.getPartyBySymbol("p3")
				.getVoteNumber());
		Assert.assertEquals(12, stationParties.getPartyBySymbol("p4")
				.getVoteNumber());
		Assert.assertEquals(18, stationParties.getPartyBySymbol("p5")
				.getVoteNumber());
		Assert.assertEquals(9, stationParties.getPartyBySymbol("p6")
				.getVoteNumber());
	}
	
	@Test
	public void testVoteWrongPassword() throws ChoosingInterruptedException{
		windowStub.password = "wrong_password";
		windowStub.errorMessage = Messages.wrong_password;
		station.testVoting();
	}
	
	@Test
	public void testVoteWrongID() throws ChoosingInterruptedException{
		windowStub.password = "password1";
		windowStub.errorMessage = Messages.ID_must_be_a_number;
		windowStub.throwID = true;
		station.testVoting();
	}
	
	@Test
	public void testVoteIDUnidentified() throws ChoosingInterruptedException{
		windowStub.password = "password2";
		controllerStub.status = VoterStatus.unidentified;
		windowStub.errorMessage = Messages.You_need_to_identify_yourself_in_the_mainframe;
		windowStub.id = 23;
		station.testVoting();
	}
	
	@Test
	public void testVoteIDVotedNotHere() throws ChoosingInterruptedException{
		windowStub.password = "password3";
		controllerStub.status = VoterStatus.voted;
		windowStub.errorMessage = Messages.You_cannot_vote_here;
		windowStub.id = 23;
		station.testVoting();
	}
	
	@Test
	public void testVoteIDStartedVoteNotHere() throws ChoosingInterruptedException{
		windowStub.password = "password3";
		controllerStub.status = VoterStatus.startedVote;
		windowStub.errorMessage = Messages.You_cannot_vote_here;
		windowStub.id = 23;
		station.testVoting();
	}
	
	@Test
	public void testVoteIDIdentified() throws ChoosingInterruptedException, Exception{
		windowStub.password = "password2";
		controllerStub.status = VoterStatus.identified;
		windowStub.id = 23;
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		windowStub.message = Messages.You_successfully_test_voted_for_the_party;
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		station.testVoting();
		Assert.assertEquals(partiesStub.getPartyBySymbol("p2").getVoteNumber(), chooseStub.partiesList.getPartyBySymbol("p2").getVoteNumber());
	}
	
	@Test
	public void testVoteManyTimes() throws ChoosingInterruptedException, Exception{
		windowStub.password = "password1";
		controllerStub.status = VoterStatus.identified;
		windowStub.id = 23;
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		windowStub.message = Messages.You_successfully_test_voted_for_the_party;
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		
		station.testVoting();
		Assert.assertEquals(partiesStub.getPartyBySymbol("p2").getVoteNumber(), chooseStub.partiesList.getPartyBySymbol("p2").getVoteNumber());
		station.testVoting();
		Assert.assertEquals(partiesStub.getPartyBySymbol("p2").getVoteNumber(), chooseStub.partiesList.getPartyBySymbol("p2").getVoteNumber());
		station.testVoting();
		Assert.assertEquals(partiesStub.getPartyBySymbol("p2").getVoteNumber(), chooseStub.partiesList.getPartyBySymbol("p2").getVoteNumber());
		station.testVoting();
		Assert.assertEquals(partiesStub.getPartyBySymbol("p2").getVoteNumber(), chooseStub.partiesList.getPartyBySymbol("p2").getVoteNumber());
		station.testVoting();
		Assert.assertEquals(partiesStub.getPartyBySymbol("p2").getVoteNumber(), chooseStub.partiesList.getPartyBySymbol("p2").getVoteNumber());
		station.testVoting();
		Assert.assertEquals(partiesStub.getPartyBySymbol("p2").getVoteNumber(), chooseStub.partiesList.getPartyBySymbol("p2").getVoteNumber());
		station.testVoting();
		Assert.assertEquals(partiesStub.getPartyBySymbol("p2").getVoteNumber(), chooseStub.partiesList.getPartyBySymbol("p2").getVoteNumber());
		
	}
	
	@Test
	public void voteWrongID() throws ChoosingInterruptedException{
		windowStub.errorMessage = Messages.ID_must_be_a_number;
		windowStub.throwID = true;
		station.voting();
	}
	
	@Test
	public void voteIDUnidentified() throws ChoosingInterruptedException{
		controllerStub.status = VoterStatus.unidentified;
		windowStub.errorMessage = Messages.You_need_to_identify_yourself_in_the_mainframe;
		windowStub.id = 23;
		station.voting();
	}
	
	@Test
	public void voteIDVotedNotHere() throws ChoosingInterruptedException{
		controllerStub.status = VoterStatus.voted;
		windowStub.errorMessage = Messages.You_cannot_vote_here;
		windowStub.id = 23;
		station.voting();
	}
	
	@Test
	public void voteIDStartedVoteNotHere() throws ChoosingInterruptedException{
		controllerStub.status = VoterStatus.startedVote;
		windowStub.errorMessage = Messages.You_cannot_vote_here;
		windowStub.id = 23;
		station.voting();
	}
	
	@Test
	public void voteIdentified() throws Exception{
		controllerStub.status = VoterStatus.identified;
		controllerStub.id = windowStub.id = 23;
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		windowStub.message = Messages.You_successfully_voted_for_the_party;
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		
		station.voting();
		
		//need to verify vote amount of p2 increased by 1
		partiesStub.getPartyBySymbol("p2").decreaseVoteNumber();
	}
	
	@Test
	public void voteMultipleTimes() throws Exception{
		controllerStub.status = VoterStatus.identified;
		controllerStub.id = windowStub.id = 23;
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		windowStub.message = Messages.You_successfully_voted_for_the_party;
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		
		//first 3 votes are good
		station.voting();
		controllerStub.status = VoterStatus.voted;
		station.voting();
		
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p3");
		windowStub.message = Messages.You_successfully_voted_for_the_party;
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p3");
		station.voting();
		
		windowStub.errorMessage = Messages.You_cannot_change_your_vote_anymore;
		station.voting();
		
		//need to verify vote amount of p3 increased by 1
		partiesStub.getPartyBySymbol("p3").decreaseVoteNumber();
	}
	
	@Test
	public void voteMultipleTimesSeveralParties() throws Exception{
		controllerStub.status = VoterStatus.identified;
		controllerStub.id = windowStub.id = 23;
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p3");
		windowStub.message = Messages.You_successfully_voted_for_the_party;
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p3");
		
		//first 3 votes are good
		station.voting();
		controllerStub.status = VoterStatus.voted;
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p1");
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p1");
		station.voting();
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		station.voting();
		
		//4th vote is not allowed
		windowStub.errorMessage = Messages.You_cannot_change_your_vote_anymore;
		station.voting();
		
		//need to verify vote amount of p2 increased by 1 (and other parties stayed the same)
		partiesStub.getPartyBySymbol("p2").decreaseVoteNumber();
	}
	
	@Test
	public void voteMultipleTimesSeveralPartiesRepeats() throws Exception{
		controllerStub.status = VoterStatus.identified;
		controllerStub.id = windowStub.id = 23;
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		windowStub.message = Messages.You_successfully_voted_for_the_party;
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		
		//first 3 votes are good
		station.voting();
		controllerStub.status = VoterStatus.voted;
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p1");
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p1");
		station.voting();
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		station.voting();
		
		//4th vote is not allowed
		windowStub.errorMessage = Messages.You_cannot_change_your_vote_anymore;
		station.voting();
		
		//need to verify vote amount of p2 increased by 1 (and other parties stayed the same)
		partiesStub.getPartyBySymbol("p2").decreaseVoteNumber();
	}
	
	@Test
	public void voteMultipleTimesSeveralPartiesRepeatsAndThenTestVote() throws Exception{
		controllerStub.status = VoterStatus.identified;
		controllerStub.id = windowStub.id = 23;
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		windowStub.message = Messages.You_successfully_voted_for_the_party;
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		
		//first 3 votes are good
		station.voting();
		controllerStub.status = VoterStatus.voted;
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p1");
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p1");
		station.voting();
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		station.voting();
		
		//4th vote is not allowed
		windowStub.errorMessage = Messages.You_cannot_change_your_vote_anymore;
		station.voting();
		
		//not even if it's a test vote
		windowStub.password = "password1";
		station.testVoting();
		
		//need to verify vote amount of p2 increased by 1 (and other parties stayed the same)
		partiesStub.getPartyBySymbol("p2").decreaseVoteNumber();
	}
	
	@Test(expected = ChoosingInterruptedException.class)
	public void voteInterruptedID() throws Exception{
		controllerStub.status = VoterStatus.identified;
		windowStub.interrupt = true;
		
		station.voting();
	}
	
	@Test(expected = ChoosingInterruptedException.class)
	public void voteInterruptedChoose() throws Exception{
		controllerStub.status = VoterStatus.identified;
		controllerStub.id = windowStub.id = 23;
		chooseStub.interrupt = true;
		
		station.voting();
	}
	
	@Test(expected = ChoosingInterruptedException.class)
	public void testVoteInterruptedID() throws Exception{
		controllerStub.status = VoterStatus.identified;
		windowStub.interrupt = true;
		
		station.testVoting();
	}
	
	@Test(expected = ChoosingInterruptedException.class)
	public void testVoteInterruptedChoose() throws Exception{
		controllerStub.status = VoterStatus.identified;
		controllerStub.id = windowStub.id = 23;
		chooseStub.interrupt = true;
		
		station.testVoting();
	}
	
	@Test
	public void retire(){
		station.retire();
		Assert.assertTrue(chooseStub.retired);
		Assert.assertTrue(windowStub.retired);
	}
	
	@Test
	public void multipleVoters() throws Exception{
		controllerStub.status = VoterStatus.identified;
		controllerStub.id = windowStub.id = 23;
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		windowStub.message = Messages.You_successfully_voted_for_the_party;
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		//voter 23 voted for p2
		station.voting();
		
		controllerStub.status = VoterStatus.identified;
		controllerStub.id = windowStub.id = 24;
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		windowStub.message = Messages.You_successfully_voted_for_the_party;
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		//voter 24 voted for p2
		station.voting();
		
		controllerStub.status = VoterStatus.identified;
		controllerStub.id = windowStub.id = 22;
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p3");
		windowStub.message = Messages.You_successfully_voted_for_the_party;
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p3");
		//voter 22 voted for p3
		station.voting();
		
		//need to verify vote amount of p2 increased by 2 and p3 by 1
		partiesStub.getPartyBySymbol("p2").decreaseVoteNumber();
		partiesStub.getPartyBySymbol("p2").decreaseVoteNumber();
		partiesStub.getPartyBySymbol("p3").decreaseVoteNumber();
	}
	
	
	@Test
	public void voteTwiceWaitTooMuch() throws Exception{
		controllerStub.status = VoterStatus.identified;
		controllerStub.id = windowStub.id = 23;
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		windowStub.message = Messages.You_successfully_voted_for_the_party;
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p2");
		
		//first vote is good
		station.voting();
		controllerStub.status = VoterStatus.voted;
		
		//sleep ridiculous amounts of time
		Thread.sleep(3*60*1000);
		
		
		chooseStub.party = chooseStub.partiesList.getPartyBySymbol("p1");
		windowStub.message = Messages.You_successfully_voted_for_the_party;
		windowStub.party = chooseStub.partiesList.getPartyBySymbol("p1");
		//cannot vote anymore
		windowStub.errorMessage = Messages.You_cannot_change_your_vote_anymore;
		station.voting();
		
		//need to verify vote amount of p2 increased by 1
		partiesStub.getPartyBySymbol("p2").decreaseVoteNumber();
	}

}
