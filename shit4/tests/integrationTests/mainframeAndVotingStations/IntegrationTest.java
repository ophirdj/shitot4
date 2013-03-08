package integrationTests.mainframeAndVotingStations;

import global.dictionaries.Messages;
import global.gui.StationPanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mainframe.communication.IStationsControllerFactory;
import mainframe.factories.IMainframeFactory;
import mainframe.factories.IMainframeWindowFactory;
import mainframe.logic.IMainframe;
import mainframe.logic.IMainframe.IdentificationError;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import votersList.factories.VoterDataFactory;
import votersList.factories.VotersListFactory;
import votersList.model.IVoterData;
import votersList.model.IVoterData.AlreadyIdentified;
import votersList.model.IVoterData.Unidentified;
import votersList.model.IVotersList;
import votersList.model.IVotersList.VoterDoesntExist;
import votersList.model.VoterData;
import votersList.model.VotersList;
import votingStation.factories.IVotingRecordFactory;
import votingStation.factories.IVotingStationFactory;
import votingStation.factories.IVotingStationWindowFactory;
import votingStation.factories.VotingStationFactory;
import org.junit.*;

import choosingList.logic.IChoosingList.ChoosingInterruptedException;

import partiesList.factories.PartyFactory;
import partiesList.model.IPartiesList;
import partiesList.model.IPartiesList.PartyDoesNotExist;
import partiesList.model.IParty;
import partiesList.model.PartiesList;
import partiesList.model.Party;

/**
 * Check integration of Mainframe, StationsController & VotingStation classes
 * 
 * StationsController is a very thin class so we skipped its integrations with
 * Mainframe and VotingStation
 * 
 * @author Ophir De Jager
 * 
 */
public class IntegrationTest {

	private List<String> passwords;
	private List<VotingStationWindowStub> votingWindowStubs;
	private Map<StationPanel, ChoosingListStub> choosingListStubs;
	private MainframeWindowStub mainframeWindowStub;
	private ChoosingListStubFactory choosingListStubFactory;
	private final long maxVotingTimeSeconds = 1L;
	private final int numVotingStations = 50;
	private final int backupTimeIntervalSeconds = 2;
	private IMainframe mainframe;
	private BackupStubFactory backupStubFactory;
	private ReadSuppliedXMLStubFactory readSuppliedXMLStubFactory;
	private IPartiesList parties;
	private IVotersList voters;
	private IVotersList unregistered;
	private IPartiesList expectedParties;
	private IVotersList expectedVoters;
	private IVotersList expectedUnregistered;
	private boolean backupThreadCheckMode;
	private Messages message;

	/**
	 * Initialize test environment & mainframe
	 */
	@Before
	public void before() {

		passwords = new ArrayList<String>();
		passwords.add("password1");
		passwords.add("password2");
		passwords.add("password3");
		passwords.add("password4");

		votingWindowStubs = new ArrayList<VotingStationWindowStub>();
		choosingListStubs = new HashMap<StationPanel, ChoosingListStub>();
		choosingListStubFactory = new ChoosingListStubFactory(this);
		mainframeWindowStub = null;

		IVotersListFactory votersListFactory = new VotersListFactory();
		IVoterDataFactory voterDataFactory = new VoterDataFactory();
		IMainframeWindowFactory mainframeWindowFactory = new MainframeWindowStubFactory(
				this);
		readSuppliedXMLStubFactory = new ReadSuppliedXMLStubFactory();

		IVotingStationWindowFactory votingStationWindowFactory = new VotingStationWindowStubFactory(
				this);
		IVotingRecordFactory votingRecordFactory = new VotingRecordTestConfigurationFactory(
				maxVotingTimeSeconds);
		IVotingStationFactory votingStationFactory = new VotingStationFactory(
				choosingListStubFactory, votingStationWindowFactory,
				votingRecordFactory);
		IStationsControllerFactory stationsControllerFactory = new StationsControllerTestConfigurationFactory(
				votingStationFactory, passwords, numVotingStations);

		backupStubFactory = new BackupStubFactory();
		IMainframeFactory mainframeFactory = new MainframeTestConfigurationFactory(
				backupTimeIntervalSeconds, backupStubFactory,
				mainframeWindowFactory, readSuppliedXMLStubFactory,
				stationsControllerFactory, voterDataFactory, votersListFactory);
		mainframe = mainframeFactory.createInstance();

		initializeLists();
		readSuppliedXMLStubFactory.setPartiesList(parties.copy());
		readSuppliedXMLStubFactory.setVoterList(voters.copy());
		
		backupThreadCheckMode = false;
		mainframe.initialize();
	}


	/**
	 * Assertions after every test
	 * @throws Exception
	 */
	@After
	public void after() throws Exception {
		if(backupThreadCheckMode) checkBackupOnTheFly();
		else checkBackupWhenShutdown();
	}
	
	
	/**
	 * Shut down mainframe & assert that every station was retired and lists
	 * match expected ones
	 * @throws Exception 
	 */
	private void checkBackupWhenShutdown(){
		mainframe.countVotes();
		Assert.assertTrue(mainframeWindowStub.isVotesCounted());
		Assert.assertEquals(expectedParties, mainframeWindowStub.getParties());
		mainframe.shutDown();
		for (VotingStationWindowStub window : votingWindowStubs)
			Assert.assertTrue(window.isRetired());
		backupStubFactory.matchLists(expectedParties, expectedVoters,
				expectedUnregistered);
	}
	
	
	/**
	 * Wait for backup & crash mainframe. Then assert backup was good
	 * @throws Exception
	 */
	private void checkBackupOnTheFly() throws Exception{
		crashMainframe();
		//no more backupsa from here
		backupStubFactory.matchLists(expectedParties, expectedVoters,
				expectedUnregistered);
	}
	
	
	/**
	 * Crash mainframe & clear all tracking of stubs
	 * @throws Exception
	 */
	private void crashMainframe() throws Exception{
		//wait for backup to happen
		synchronized (this) {wait((backupTimeIntervalSeconds+1)*1000);}
		mainframe.crash();
		//need to clear all tracking of stubs as they are no longer relevant
		votingWindowStubs.clear();
		choosingListStubs.clear();
	}
	
	
	/**
	 * Crash mainframe & then restore from backup
	 * @throws Exception
	 */
	private void crashAndRestore() throws Exception{
		crashMainframe();
		mainframe.restore();
		
	}

	
	
	
	
	public void addChoosingListStub(StationPanel stationPanel,
			ChoosingListStub choosingListStub) {
		this.choosingListStubs.put(stationPanel, choosingListStub);
	}

	
	public void setMainframeWindowStub(MainframeWindowStub stub) {
		this.mainframeWindowStub = stub;
	}

	
	public void addVotingWindowStub(VotingStationWindowStub stub) {
		this.votingWindowStubs.add(stub);
	}
	
	
	public void setMessage(Messages message) {
		this.message = message;
	}

	
	private void initializeLists() {
		parties = new PartiesList(new PartyFactory());
		for (int i = 0; i < 100; i++)
			parties.addParty(new Party("party" + i, "p" + i));

		voters = new VotersList();
		for (int i = 0; i < 1000; i++)
			voters.addVoter(new VoterData(i));

		unregistered = new VotersList();
		for (int i = 0; i < 500; i++)
			unregistered.addVoter(new VoterData(i));

		expectedParties = parties.copy();
		expectedVoters = voters.copy();
		expectedUnregistered = new VotersList();
	}
	
	

	/**
	 * Boot (with some voters & parties)
	 */
	@Test
	public void initialize() {
		// no unregistered on initialize
		expectedUnregistered = new VotersList();
		Assert.assertTrue(mainframeWindowStub.isInitialized());
		for (VotingStationWindowStub window : votingWindowStubs) {
			Assert.assertTrue(window.isInitialized());
		}
		Assert.assertTrue(mainframe.checkParties());
	}
	
	
	/**
	 * Clean boot (no voters, no parties)
	 */
	@Test
	public void initializeEmpty() {
		mainframe.shutDown();
		readSuppliedXMLStubFactory.setPartiesList(new PartiesList(
				new PartyFactory()));
		readSuppliedXMLStubFactory.setVoterList(new VotersList());

		expectedParties = new PartiesList(new PartyFactory());
		expectedVoters = new VotersList();
		expectedUnregistered = new VotersList();
		mainframe.initialize();
		Assert.assertTrue(mainframeWindowStub.isInitialized());
		for (VotingStationWindowStub window : votingWindowStubs) {
			Assert.assertTrue(window.isInitialized());
		}
	}
	

	/**
	 * Clean restore (no voters, no parties)
	 */
	@Test
	public void restoreEmpty() {
		mainframe.shutDown();
		backupStubFactory.setPartiesList(new PartiesList(new PartyFactory()));
		backupStubFactory.setVoterList(new VotersList());
		backupStubFactory.setUnregisteredVoterList(new VotersList());

		expectedParties = new PartiesList(new PartyFactory());
		expectedVoters = new VotersList();
		expectedUnregistered = new VotersList();
		mainframe.restore();
		Assert.assertTrue(mainframeWindowStub.isInitialized());
		for (VotingStationWindowStub window : votingWindowStubs) {
			Assert.assertTrue(window.isInitialized());
		}
	}

	
	/**
	 * Restore (with some voters & parties, no unregistered)
	 */
	@Test
	public void restoreNoUnregistered() {
		mainframe.shutDown();
		backupStubFactory.setPartiesList(parties.copy());
		backupStubFactory.setVoterList(voters.copy());
		backupStubFactory.setUnregisteredVoterList(new VotersList());

		expectedUnregistered = new VotersList();
		mainframe.restore();
		Assert.assertTrue(mainframeWindowStub.isInitialized());
		for (VotingStationWindowStub window : votingWindowStubs) {
			Assert.assertTrue(window.isInitialized());
		}
	}

	
	/**
	 * Restore (with some voters, parties & unregistered)
	 */
	@Test
	public void restore() {
		mainframe.shutDown();
		backupStubFactory.setPartiesList(parties.copy());
		backupStubFactory.setVoterList(voters.copy());
		backupStubFactory.setUnregisteredVoterList(unregistered.copy());
		expectedUnregistered = unregistered.copy();
		mainframe.restore();
		Assert.assertTrue(mainframeWindowStub.isInitialized());
		for (VotingStationWindowStub window : votingWindowStubs) {
			Assert.assertTrue(window.isInitialized());
		}
	}
	
	

	/**
	 * Crash system, then restore & check lists
	 * @throws Exception 
	 */
	@Test
	public void restoreAfterCrash() throws Exception{
		crashAndRestore();
	}
	
	
	/**
	 * Check parties in stations match parties in mainframe after many voters voted 3 times each
	 * @throws Exception
	 */
	@Test
	public void checkPartiesAfterManyVotesCrash() throws Exception {
		allIdentifyAndVote(createVoters(2000));
		crashAndRestore();
		Assert.assertTrue(mainframe.checkParties());
	}

	
	// helping functions for voting

	
	/**
	 * Class to represent a vote of ID <id> to party <party> in station window
	 * <stationWindow>
	 * 
	 * @author Ophir De Jager
	 * 
	 */
	private class Vote {
		private int id;
		private String[] parties;
		private VotingStationWindowStub stationWindow;
		private int nextVote;

		public Vote(int id, String[] parties,
				VotingStationWindowStub stationWindow) {
			this.id = id;
			this.parties = parties;
			this.stationWindow = stationWindow;
			this.nextVote = 0;
		}

		/**
		 * Identify in mainframe and add him to <expectedVoters> (and to
		 * <expectedUnregistered> if was unregistered)
		 * 
		 * @param id
		 * @throws IdentificationError
		 * @throws AlreadyIdentified
		 * @throws VoterDoesntExist 
		 */
		public void identify() throws IdentificationError, AlreadyIdentified, VoterDoesntExist {
			mainframe.identification(getId());
			if(expectedVoters.inList(getId())){
				expectedVoters.findVoter(getId()).markIdentified();
			}
			else{
				IVoterData voter = new VoterData(getId());
				voter.markIdentified();
				expectedVoters.addVoter(voter);
				expectedUnregistered.addVoter(voter);
			}
		}

		/**
		 * Vote in <stationWindow> to <party> by <id>
		 * 
		 * @throws ChoosingInterruptedException
		 * @throws PartyDoesNotExist
		 * @throws Unidentified 
		 * @throws VoterDoesntExist 
		 */
		public void makeVote() throws ChoosingInterruptedException,
				PartyDoesNotExist, Unidentified, VoterDoesntExist {
			stationWindow.setId(getId());
			choosingListStubs.get(stationWindow).setParty(parties[nextVote]);
			stationWindow.makeVote();
			IVoterData voter = expectedVoters.findVoter(getId());
			if(voter.isIdentified()){
				voter.markStartedVote();
				voter.markVoted();
			}
			if (nextVote > 0)
				expectedParties.getPartyBySymbol(parties[nextVote - 1])
						.decreaseVoteNumber();
			expectedParties.getPartyBySymbol(parties[nextVote])
					.increaseVoteNumber();
			nextVote++;
		}
		
		/**
		 * Check if more votes are available
		 * @return
		 */
		public boolean hasMoreVotes(){
			return nextVote < parties.length;
		}

		public int getId() {
			return id;
		}
	}

	/**
	 * Make all the votes
	 * 
	 * @param votes
	 * @throws Exception
	 */
	private void allIdentifyAndVote(Vote votes[]) throws Exception {
		for (Vote vote : votes) {
			vote.identify();
			while(vote.hasMoreVotes()) vote.makeVote();
		}
	}

	/**
	 * Create some votes
	 * @param size
	 * @return
	 */
	private Vote[] createVoters(int size) {
		Vote votes[] = new Vote[size];
		String[] allParties = new String[parties.size()];
		Iterator<IParty> iterator = parties.iterator();
		for (int i = 0; i < allParties.length; i++) {
			allParties[i] = iterator.next().getSymbol();
		}
		for (int i = 0; i < votes.length; i++) {
			String[] parties = {
					allParties[i % allParties.length],
					allParties[(i + allParties.length / 3) % allParties.length],
					allParties[(i + allParties.length * 2 / 3)
							% allParties.length] };
			votes[i] = new Vote(i, parties, votingWindowStubs.get(i
					% votingWindowStubs.size()));
		}
		return votes;
	}

	
	
	
	/**
	 * Simple test of 1 voter (3 votes)
	 * @throws Exception
	 */
	@Test
	public void oneVote() throws Exception {
		allIdentifyAndVote(createVoters(1));
	}
	
	
	/**
	 * More complex test of many voter (3 votes each)
	 * @throws Exception
	 */
	@Test
	public void manyVotes() throws Exception {
		allIdentifyAndVote(createVoters(2000));
	}
	
	
	/**
	 * Simple test of restore after 1 voter voted 3 times
	 * @throws Exception
	 */
	@Test
	public void restoreAfterOneVote() throws Exception {
		allIdentifyAndVote(createVoters(1));
		backupStubFactory.setPartiesList(expectedParties);
		backupStubFactory.setVoterList(voters);
		backupStubFactory.setUnregisteredVoterList(expectedUnregistered);
		mainframe.shutDown();
		mainframe.restore();
	}
	
	
	/**
	 * More complex test of restore after many voters voted 3 times each
	 * @throws Exception
	 */
	@Test
	public void restoreAfterManyVotes() throws Exception {
		allIdentifyAndVote(createVoters(2000));
		backupStubFactory.setPartiesList(expectedParties);
		backupStubFactory.setVoterList(voters);
		backupStubFactory.setUnregisteredVoterList(expectedUnregistered);
		mainframe.shutDown();
		mainframe.restore();
	}
	
	
	/**
	 * Check parties in stations match parties in mainframe after 1 voter voted 3 times
	 * @throws Exception
	 */
	@Test
	public void checkPartiesAfterOneVote() throws Exception {
		allIdentifyAndVote(createVoters(1));
		Assert.assertTrue(mainframe.checkParties());
	}
	
	
	/**
	 * Check parties in stations match parties in mainframe after many voters voted 3 times each
	 * @throws Exception
	 */
	@Test
	public void checkPartiesAfterManyVotes() throws Exception {
		allIdentifyAndVote(createVoters(2000));
		Assert.assertTrue(mainframe.checkParties());
	}
	
	
	/**
	 * Simple test of 1 voter (3 votes)
	 * @throws Exception
	 */
	@Test(expected = IdentificationError.class)
	public void oneVoteIndentificationError() throws Exception {
		Vote voters[] = createVoters(1);
		allIdentifyAndVote(voters);
		mainframe.identification(voters[0].getId());
	}
	
	
	/**
	 * More complex test of many voter (3 votes each)
	 * @throws Exception
	 */
	@Test(expected = IdentificationError.class)
	public void manyVotesIndentificationError() throws Exception {
		Vote voters[] = createVoters(2000);
		allIdentifyAndVote(voters);
		mainframe.identification(voters[0].getId());
	}
	
	
	/**
	 * Simple test of restore after 1 voter voted 3 times
	 * @throws Exception
	 */
	@Test(expected = IdentificationError.class)
	public void restoreAfterOneVoteIndentificationError() throws Exception {
		Vote voters[] = createVoters(1);
		allIdentifyAndVote(voters);
		backupStubFactory.setPartiesList(expectedParties);
		backupStubFactory.setVoterList(this.voters);
		backupStubFactory.setUnregisteredVoterList(expectedUnregistered);
		mainframe.shutDown();
		mainframe.restore();
		mainframe.identification(voters[0].getId());
	}
	
	
	/**
	 * More complex test of restore after many voters voted 3 times each
	 * @throws Exception
	 */
	@Test(expected = IdentificationError.class)
	public void restoreAfterManyVotesIndentificationError() throws Exception {
		Vote voters[] = createVoters(2000);
		allIdentifyAndVote(voters);
		backupStubFactory.setPartiesList(expectedParties);
		backupStubFactory.setVoterList(this.voters);
		backupStubFactory.setUnregisteredVoterList(expectedUnregistered);
		mainframe.shutDown();
		mainframe.restore();
		mainframe.identification(voters[0].getId());
	}
	
	
	/**
	 * Check parties in stations match parties in mainframe after 1 voter voted 3 times
	 * @throws Exception
	 */
	@Test(expected = IdentificationError.class)
	public void checkPartiesAfterOneVoteIndentificationError() throws Exception {
		Vote voters[] = createVoters(1);
		allIdentifyAndVote(voters);
		mainframe.identification(voters[0].getId());
		Assert.assertTrue(mainframe.checkParties());
	}
	
	
	/**
	 * Check parties in stations match parties in mainframe after many voters voted 3 times each
	 * @throws Exception
	 */
	@Test(expected = IdentificationError.class)
	public void checkPartiesAfterManyVotesIndentificationError() throws Exception {
		Vote voters[] = createVoters(2000);
		allIdentifyAndVote(voters);
		mainframe.identification(voters[0].getId());
		Assert.assertTrue(mainframe.checkParties());
	}
	
	
	
	//crash mainframe after voting and check backup
	
	
	/**
	 * Simple test of 1 voter (3 votes), then crash happens
	 * @throws Exception
	 */
	@Test
	public void oneVoteCrash() throws Exception {
		backupThreadCheckMode = true;
		allIdentifyAndVote(createVoters(1));
	}
	
	
	/**
	 * More complex test of many voter (3 votes each), then crash happens
	 * @throws Exception
	 */
	@Test
	public void manyVotesCrash() throws Exception {
		backupThreadCheckMode = true;
		allIdentifyAndVote(createVoters(2000));
	}
	
	
	/**
	 * Make some votes, crash, restore, check parties
	 * @throws Exception
	 */
	@Test
	public void someVotesCrashRestoreThenCheckParties() throws Exception {
		allIdentifyAndVote(createVoters(23));
		crashAndRestore();
		Assert.assertTrue(mainframe.checkParties());
	}
	
	
	/**
	 * Make some votes, crash, restore, then voter identifies again
	 * @throws Exception
	 */
	@Test(expected = IdentificationError.class)
	public void someVotesCrashRestoreThenIdentifyAgain() throws Exception {
		allIdentifyAndVote(createVoters(100));
		crashAndRestore();
		allIdentifyAndVote(createVoters(1));
	}
	
	
	/**
	 * Make some votes, crash, restore, then voter votes again
	 * After crash voters can't change thier vote
	 * @throws Exception
	 */
	public void someVotesCrashRestoreThenVoteAgain() throws Exception {
		allIdentifyAndVote(createVoters(1000));
		crashAndRestore();
		Vote votes[] = createVoters(1);
		votes[0].makeVote();
		Assert.assertEquals(Messages.You_cannot_vote_here, message);
	}
	

}