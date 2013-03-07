package global.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mainframe.communication.IStationsControllerFactory;
import mainframe.factories.IMainframeFactory;
import mainframe.factories.IMainframeWindowFactory;
import mainframe.logic.IMainframe;
import mainframe.logic.IMainframe.IdentificationError;
import choosingList.factories.ChoosingListFactory;
import choosingList.factories.IChoosingListFactory;
import choosingList.factories.IChoosingWindowFactory;
import choosingList.logic.ChooseType;
import fileHandler.factories.IBackupFactory;
import fileHandler.factories.IReadSuppliedXMLFactory;
import fileHandler.logic.Backup;
import fileHandler.logic.IBackup;
import fileHandler.logic.IReadSuppliedXML;
import global.gui.StationPanel;
import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import partiesList.factories.PartiesListFactory;
import partiesList.factories.PartyFactory;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import partiesList.model.Party;
import practiceStation.factories.IImagePanelFactory;
import practiceStation.factories.IPracticeStationFactory;
import practiceStation.factories.IPracticeStationWindowFactory;
import practiceStation.factories.PracticeStationFactory;
import practiceStation.logic.IPracticeStation;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import votersList.factories.VoterDataFactory;
import votersList.factories.VotersListFactory;
import votersList.model.IVoterData;
import votersList.model.IVotersList;
import votersList.model.VoterData;
import votersList.model.VotersList;
import votingStation.factories.IVotingRecordFactory;
import votingStation.factories.IVotingStationFactory;
import votingStation.factories.IVotingStationWindowFactory;
import votingStation.factories.VotingStationFactory;
import org.junit.*;

/**
 * the acceptance tests of the entire project 
 * Ophir add here more details
 * all the GUI are represented by stubs
 */
public class AcceptanceTest {
	
	private int numVotingStations = 4;
	private int numPracticeStations = 2;
	private long maxVotingTimeSeconds = 1L;
	private int backupTimeIntervalSeconds = 2;
	private List<String> passwords;

	private IMainframe mainframe;
	private List<IPracticeStation> practiceStations;
	private IPartiesList practiceParties;
	
	
	private IPartiesList initialPartiesList;
	private IVotersList initialVotersList;
	
	private IPartiesList expectedPartiesList;
	private IVotersList expectedVotersList;
	private IVotersList expectedUnregisteredList;
	
	private Map<StationPanel, ChoosingWindowStub> choosingWindowStubs;
	private List<VotingStationWindowStub> votingWindowStubs;
	private Map<StationPanel, ImagePanelStub> imagePanelStubs;
	private List<PracticeStationWindowStub> practiceStationWindowStubs;
	private MainframeWindowStub mainframeWindowStub;
	
	private final String backupVotersListFile = "acceptanceTest/listBackup/VotersListBackup.xml";
	private final String backupPartiesListFile = "acceptanceTest/listBackup/PartiesListBackup.xml";
	private final String backupUnregisteredFile = "acceptanceTest/unregisteredVoters/UnregisteredVoters.xml";
	
	
	private final static int WhitePartyNum = -1;
	
	
	//keep track of voters (have they identified themselves? who have they voted to?)
	private Set<Integer> identifiedIDs;
	private Map<Integer, IParty> votes;
	
	
	private String getSymbolByPlace(int place) throws Exception{
		if(place == WhitePartyNum) return null;
		for(IParty party : initialPartiesList){
			if(place == 0){
				return party.getSymbol();
			}
			place--;
		}
		throw new AssertionError();
	}
	
	private void peepParties(){
		IPartyFactory partyFactory = new PartyFactory();
		IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		IVotersListFactory votersListFactory = new VotersListFactory();
		IVoterDataFactory voterDataFactory = new VoterDataFactory();
		
		IBackup backup = new Backup(partiesListFactory, partyFactory, votersListFactory, voterDataFactory, backupVotersListFile, backupPartiesListFile, backupUnregisteredFile);
		IPartiesList resultParties = backup.restoreParties();
		resultParties.peep();
	}
	
	private void peepVoters(){
		IPartyFactory partyFactory = new PartyFactory();
		IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		IVotersListFactory votersListFactory = new VotersListFactory();
		IVoterDataFactory voterDataFactory = new VoterDataFactory();
		
		IBackup backup = new Backup(partiesListFactory, partyFactory, votersListFactory, voterDataFactory, backupVotersListFile, backupPartiesListFile, backupUnregisteredFile);
		IVotersList resultVoters = backup.restoreVoters();
		resultVoters.peep();
	}
	
	private void peepUnregVoters(){
		IPartyFactory partyFactory = new PartyFactory();
		IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		IVotersListFactory votersListFactory = new VotersListFactory();
		IVoterDataFactory voterDataFactory = new VoterDataFactory();
		
		IBackup backup = new Backup(partiesListFactory, partyFactory, votersListFactory, voterDataFactory, backupVotersListFile, backupPartiesListFile, backupUnregisteredFile);
		IVotersList resultVoters = backup.restoreUnregisteredVoters();
		resultVoters.peep();
	}
	
	
	
	/**
	 * prepare the environment for the tests
	 * executed before each and every test
	 */
	@Before
	public void initSys(){
		practiceStations = new ArrayList<IPracticeStation>();
		
		passwords = new ArrayList<String>();
		passwords.add("password1");
		passwords.add("password2");
		passwords.add("password3");
		passwords.add("password4");
		
		votingWindowStubs  = new ArrayList<VotingStationWindowStub>();
		imagePanelStubs = new HashMap<StationPanel, ImagePanelStub>();
		practiceStationWindowStubs = new ArrayList<PracticeStationWindowStub>();
		choosingWindowStubs = new HashMap<StationPanel, ChoosingWindowStub>();
		mainframeWindowStub = null;
		
		
		IPartyFactory partyFactory = new PartyFactory();
		IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		IVotersListFactory votersListFactory = new VotersListFactory();
		IVoterDataFactory voterDataFactory = new VoterDataFactory();
		IMainframeWindowFactory mainframeWindowFactory = new MainframeWindowStubFactory(this);
		IReadSuppliedXMLFactory readSuppliedXMLFactory = new ReadSuppliedXMLTestConfigurationFactory(partiesListFactory, partyFactory, votersListFactory, voterDataFactory);
		
		IChoosingWindowFactory choosingWindowFactory = new ChoosingWindowStubFactory(this);
		IChoosingListFactory choosingListFactory = new ChoosingListFactory(choosingWindowFactory);
		IVotingStationWindowFactory votingStationWindowFactory = new VotingStationWindowStubFactory(this);
		IVotingRecordFactory votingRecordFactory = new VotingRecordTestConfigurationFactory(maxVotingTimeSeconds);
		IVotingStationFactory votingStationFactory = new VotingStationFactory(choosingListFactory, votingStationWindowFactory, votingRecordFactory);
		IStationsControllerFactory stationsControllerFactory = new StationsControllerTestConfigurationFactory(votingStationFactory, passwords, numVotingStations);
		
		IBackupFactory backupFactory = new BackupTestConfigurationFactory(partiesListFactory, partyFactory, votersListFactory, voterDataFactory, backupVotersListFile, backupPartiesListFile, backupUnregisteredFile);
		IMainframeFactory mainframeFactory = new MainframeTestConfigurationFactory(backupTimeIntervalSeconds, backupFactory, mainframeWindowFactory, readSuppliedXMLFactory, stationsControllerFactory, voterDataFactory, votersListFactory);
		mainframe = mainframeFactory.createInstance();
		
		IPracticeStationWindowFactory practiceStationWindowFactory = new PracticeStationWindowStubFactory(this);
		IImagePanelFactory imagePanelFactory = new ImagePanelStubFactory(this);
		IPracticeStationFactory practiceStationFactory = new PracticeStationFactory(choosingListFactory, practiceStationWindowFactory, imagePanelFactory);

		IPartiesList practicePartiesList = partiesListFactory.createInstance();
		for(Integer i = 1; i <= 13; i++)
			practicePartiesList.addParty(new Party(i.toString(), i.toString(), i));
		
		practiceParties = practicePartiesList;
		for(int i = 0; i < numPracticeStations; i++)
			practiceStations.add(practiceStationFactory.createInstance(practiceParties));
		
		IReadSuppliedXML reader = readSuppliedXMLFactory.createInstance();
		initialPartiesList = reader.readPartiesList();
		initialVotersList = reader.readVotersList();
		
		expectedPartiesList = initialPartiesList.copy();
		expectedVotersList = initialVotersList.copy();
		expectedUnregisteredList = new VotersList();
		
		
		identifiedIDs = new HashSet<Integer>();
		votes = new HashMap<Integer, IParty>();
		
		mainframe.initialize();
	}
	
	/**
	 * asserts that what the mainframe backup is what it should have been backup
	 */
	private void checkBackUp(){
		IPartyFactory partyFactory = new PartyFactory();
		IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		IVotersListFactory votersListFactory = new VotersListFactory();
		IVoterDataFactory voterDataFactory = new VoterDataFactory();
		
		IBackup backup = new Backup(partiesListFactory, partyFactory, votersListFactory, voterDataFactory, backupVotersListFile, backupPartiesListFile, backupUnregisteredFile);
		IPartiesList resultParties = backup.restoreParties();
		IVotersList resultVoters = backup.restoreVoters();
		IVotersList reusltUnregistered = backup.restoreUnregisteredVoters();
		
//		System.out.println("expected parties");
//		expectedPartiesList.peep();
//		System.out.println("actual parties");
//		resultParties.peep();
//		System.out.println("expected voters");
//		expectedVotersList.peep();
//		System.out.println("actual voters");
//		resultVoters.peep();
//		System.out.println("expected unregistered");
//		expectedUnregisteredList.peep();
//		System.out.println("actual unregistered");
//		reusltUnregistered.peep();
//		
//		System.out.println("\n\n\n\n");
		
		Assert.assertEquals(expectedPartiesList,resultParties);
		Assert.assertEquals(expectedVotersList, resultVoters);
		Assert.assertEquals(expectedUnregisteredList, reusltUnregistered);
	}
	
	/**
	 * checks after each and every test that the data that was backup is correct
	 * i.e. mainframe backup the right things
	 * @throws Exception should not be thrown
	 */
	@After
	public void checkBackupLists() throws Exception{
		mainframeWindowStub.setExpectedPartiesList(expectedPartiesList);
		mainframe.countVotes();
		mainframe.shutDown();
		checkBackUp();
	}

	/**
	 * adds another voting station window stub to the system
	 * @param stub the voting station window stub that should be added
	 */
	public void addVotingWindowStub(VotingStationWindowStub stub) {
		votingWindowStubs.add(stub);
	}

	/**
	 * adds an image panel stub to the given station panel
	 * @param stub the image panel stub that should be added
	 * @param caller the panel that this image panel stub should be added to
	 */
	public void addImagePanelStub(ImagePanelStub stub, StationPanel caller) {
		imagePanelStubs.put(caller, stub);
	}
	
	/**
	 * adds a choosing window stub to the given station panel
	 * @param stub the choosing window stub that should be added
	 * @param stationPanel the panel that this choosing window stub should be added to
	 */
	public void addChoosingWindowStub(ChoosingWindowStub stub,
			StationPanel stationPanel) {
		choosingWindowStubs.put(stationPanel, stub);
		
	}

	/**
	 * adds a practice station window stub to the list of practice practice station window stubs
	 * @param stub the practice station window stub that should be added to the list of practice practice station window stubs
	 */
	public void addPracticeStationWindowStub(PracticeStationWindowStub stub) {
		practiceStationWindowStubs.add(stub);
	}

	/**
	 * sets the mainframe window stub to be the mainframe window stub that was given as a parameter
	 * @param stub the above mainframe window stub
	 */
	public void setMainframeWindowStub(MainframeWindowStub stub) {
		mainframeWindowStub = stub;
	}
	
	/**
	 * 
	 */
	public static class votingData{
		private int id;
		private int station;
		private int party;
		
		public votingData(int id, int station, int party) {
			this.station = station;
			this.party = party;
			this.id = id;
		}

		public int getParty() {
			return party;
		}

		public int getStation() {
			return station;
		}

		public int getId() {
			return id;
		}
	}
	
	/**
	 * identifies every voter in the given array and if the voter is not exists in the
	 * expected voters list then we add him to the expected voters list
	 * and to the expected unregistered voters list
	 * finally we mark the voter that is in the  expected voters list as a voter
	 * who already identified
	 * @param voting the above array of voters
	 * @throws Exception
	 */
	private void identify(votingData[] voting) throws Exception{
		for(votingData data : voting){
			int id = data.getId();
			try {
				mainframe.identification(id);
				if(!expectedVotersList.inList(id)){
					IVoterData toAddUnreg = new VoterData(id);
					expectedUnregisteredList.addVoter(toAddUnreg);
					IVoterData toAdd = new VoterData(id);
					expectedVotersList.addVoter(toAdd);
				}
				IVoterData voter = expectedVotersList.findVoter(id);
				voter.markIdentified();
			} catch (IdentificationError e) {
			}
		}
	}
	
	/**
	 * every voter in the voters array that is given as parameter is making his vote
	 * 'expectedVotersList' and 'expectedPartiesList' are accordingly updated
	 * @param voting the above voting array
	 * @throws Exception if something goes wrong
	 */
	private void doVotes(votingData[] voting) throws Exception{
		for(votingData singleVoting : voting){
			Integer id = singleVoting.getId();
			Integer party = singleVoting.getParty();
			Integer station = singleVoting.getStation();
			
			
			VotingStationWindowStub votingWindowStub = votingWindowStubs.get(station);
			ChoosingWindowStub choosingWindowStub = choosingWindowStubs.get(votingWindowStub);
			String partySymbol = getSymbolByPlace(party);
			
			votingWindowStub.setId(id);
			final int partyInPage = 9;
			for(int count = party; count >= partyInPage; count-=partyInPage){
				choosingWindowStub.setChooseType(ChooseType.Next);
			}
			choosingWindowStub.setParty(partySymbol);
			votingWindowStub.makeVote();
			expectedVotersList.findVoter(id).markVoted();
			expectedPartiesList.getPartyBySymbol(partySymbol).increaseVoteNumber();
		}
	}
	
	/**
	 * identifying every voter in the voters array and makes every voter to vote
	 * also updates the test environment accordingly
	 * @param voting the above voters array
	 * @throws Exception should not happen
	 */
	private void startVoting(votingData[] voting) throws Exception{
		identify(voting);
		doVotes(voting);
	}
	
	
	
	
	
	
	
	@Test
	public void testInitialize(){
		Assert.assertEquals(numVotingStations, votingWindowStubs.size());
		Assert.assertNotNull(mainframeWindowStub);
		Assert.assertEquals(numPracticeStations, practiceStationWindowStubs.size());
		for(PracticeStationWindowStub stub: practiceStationWindowStubs){
			Assert.assertTrue(imagePanelStubs.containsKey(stub));
			Assert.assertNotNull(imagePanelStubs.get(stub));
		}
	}
	
	/**
	 * checks that there is no error when only one voter who is in the voters list is voting
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testOneVote() throws Exception{
		votingData voting[] = {new votingData(1,0,0)};
		startVoting(voting);
	}
	
	/**
	 * checks that there is no error when only one voter 
	 * who is not in the voters list is voting
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testOneVoteFromUnregisteredVoter() throws Exception{
		votingData voting[] = {new votingData(4,0,0)};
		startVoting(voting);
	}
	
	/**
	 * checks that only one voter who is registered identifying causing no errors
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testOneIdentification() throws Exception{

		int id0 = 1;
		mainframe.identification(id0);
		
		expectedVotersList.findVoter(id0).markIdentified();
	}
	
	/**
	 * checks that only one voter who is unregistered identifying causing no errors
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testIdentificationFromUnregisteredVoter() throws Exception{
		
	
		int id0 = 4;
		
		mainframe.identification(id0);
		
		
		IVoterData toAdd = new VoterData(id0);
		toAdd.markIdentified();
		IVoterData toAddUnreg = new VoterData(id0);
		
		expectedVotersList.addVoter(toAdd);
		expectedUnregisteredList.addVoter(toAddUnreg);
	}
	
	
	/**
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testFirstVotingAllParties() throws Exception{
		
		int parties[] = new int[initialPartiesList.size()];
		for (int i = 0; i < parties.length; i++) {
			parties[i] = i;
		}
		int stations[] = new int[numVotingStations];
		for (int i = 0; i < stations.length; i++) {
			stations[i] = i;
		}
		votingData voting[] = new votingData[10];
		for (int i = 0; i < voting.length; i++) {
			voting[i] = new votingData(i, stations[i%stations.length], parties[i%parties.length]);
		}
		
		startVoting(voting);
	}
	
	/**
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testFirstVotingManyParties() throws Exception{
		int parties[] = new int[initialPartiesList.size()-1];
		for (int i = 0; i < parties.length; i++) {
			parties[i] = i;
		}
		int stations[] = new int[numVotingStations];
		for (int i = 0; i < stations.length; i++) {
			stations[i] = i;
		}
		votingData voting[] = new votingData[10];
		for (int i = 0; i < voting.length; i++) {
			voting[i] = new votingData(i, stations[i%stations.length], parties[i%parties.length]);
		}
		
		startVoting(voting);
	}
	
	/**
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testFirstVotingNoVoting() throws Exception{
		mainframeWindowStub.setExpectedPartiesList(expectedPartiesList);
		mainframe.countVotes();
		mainframe.shutDown();
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFirstFewVotes() throws Exception{
		int parties[] = new int[initialPartiesList.size()-1];
		for (int i = 0; i < parties.length; i++) {
			parties[i] = i;
		}
		int stations[] = new int[numVotingStations];
		for (int i = 0; i < stations.length; i++) {
			stations[i] = i;
		}
		votingData voting[] = new votingData[2];
		for (int i = 0; i < voting.length; i++) {
			voting[i] = new votingData(i, stations[i%stations.length], parties[i%parties.length]);
		}
		
		startVoting(voting);
	}
	
	/**
	 * tests a scenario when the voter is revoting only once
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testRevoteOnce() throws Exception{
		
		votingData voting[] = {new votingData(1, 0, 0)};
		startVoting(voting);
		votingData reVoting[] = {new votingData(1, 0, WhitePartyNum)};
		
		startVoting(reVoting);
		expectedPartiesList = initialPartiesList.copy();
	}
	
	/**
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testRevoteAfterTimeOnce() throws Exception{
		final long waiting_time = (maxVotingTimeSeconds-1) * 1000;
		votingData voting[] = {new votingData(1, 0, 0)};
		startVoting(voting);
		votingData reVoting[] = {new votingData(1, 0, WhitePartyNum)};
		Thread.sleep(waiting_time);
		startVoting(reVoting);
		expectedPartiesList = initialPartiesList.copy();
	}
	
	/**
	 * tests a scenario when the voter is revoting 
	 * after the time for a legal revote is over
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testRevoteAfterTimeOver() throws Exception{
		final long waiting_time = (maxVotingTimeSeconds+1) * 1000;
		votingData voting[] = {new votingData(1, 0, 0)};
		startVoting(voting);
		votingData reVoting[] = {new votingData(1, 0, WhitePartyNum)};
		Thread.sleep(waiting_time);
		startVoting(reVoting);
	}
	
	/**
	 * tests a scenario when the voter is revoting after a short time (without waiting) in
	 * a different voting station other than the one he is voted on previously
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testRevoteDifferentStation() throws Exception{
		votingData voting[] = {new votingData(1, 0, 0)};
		startVoting(voting);
		votingData reVoting[] = {new votingData(1, 1, 1)};
		startVoting(reVoting);
		expectedPartiesList = initialPartiesList.copy();
		expectedPartiesList.getPartyBySymbol(getSymbolByPlace(0)).increaseVoteNumber();
	}
	
	/**
	 * tests a scenario when the voter is revoting after a long time in
	 * a different voting station other than the one he is voted on previously
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testRevoteDifferentStationAfterLongWait() throws Exception{
		final long waiting_time = (maxVotingTimeSeconds+1) * 1000;
		votingData voting[] = {new votingData(1, 0, 0)};
		startVoting(voting);
		votingData reVoting[] = {new votingData(1, 1, 1)};
		Thread.sleep(waiting_time);
		startVoting(reVoting);
		expectedPartiesList = initialPartiesList.copy();
		expectedPartiesList.getPartyBySymbol(getSymbolByPlace(0)).increaseVoteNumber();
	}
	
	/**
	 * tests if a guide really exists in the practice station
	 */
	@Test
	public void testGuideExist(){
		PracticeStationWindowStub practiceStationWindow = practiceStationWindowStubs.get(0);
		practiceStationWindow.practiceVote();
		Assert.assertTrue(imagePanelStubs.get(practiceStationWindow).hasShowGuide());
	}
	
	/**
	 * testing the "testVote" function of the voting station - the committee member is entering
	 * a correct password
	 * (testVote - when a committee member wishes to check the regularity of the voting station)
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testTestVote() throws Exception{
		int id = 1;
		int party = 0;
		mainframe.identification(id);
		expectedVotersList.findVoter(id).markIdentified();
		VotingStationWindowStub votingWindowStub = votingWindowStubs.get(0);
		ChoosingWindowStub choosingWindowStub = choosingWindowStubs.get(votingWindowStub);
		String partySymbol = getSymbolByPlace(party);
		
		votingWindowStub.setId(id);
		votingWindowStub.setPassword("password1");
		
		choosingWindowStub.setParty(partySymbol);
		votingWindowStub.testVote();
		Assert.assertFalse(votingWindowStub.isWasWrongPassword());
	}
	
	/**
	 * testing the "testVote" function of the voting station when the committee member is entering
	 * a wrong password
	 * (testVote - when a committee member wishes to check the regularity of the voting station)
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testTestVoteWrongPassword() throws Exception{
		int id = 1;
		int party = 0;
		mainframe.identification(id);
		expectedVotersList.findVoter(id).markIdentified();
		VotingStationWindowStub votingWindowStub = votingWindowStubs.get(0);
		ChoosingWindowStub choosingWindowStub = choosingWindowStubs.get(votingWindowStub);
		String partySymbol = getSymbolByPlace(party);
		
		votingWindowStub.setId(id);
		votingWindowStub.setPassword("passwerd2");
		
		choosingWindowStub.setParty(partySymbol);
		votingWindowStub.testVote();
		Assert.assertTrue(votingWindowStub.isWasWrongPassword());
	}
	
	/**
	 * tests that indeed a voter can revote twice at most
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testRevoteOnlyTwice() throws Exception{
		final long waiting1 = maxVotingTimeSeconds * 600;
		final long waiting2 = maxVotingTimeSeconds * 300;
		final long waiting3 = maxVotingTimeSeconds * 100;
		votingData voting[] = {new votingData(1, 0, 0)};
		startVoting(voting);
		votingData reVoting1[] = {new votingData(1, 0, 1)};
		Thread.sleep(waiting1);
		startVoting(reVoting1);
		
		votingData reVoting2[] = {new votingData(1, 0, 2)};
		Thread.sleep(waiting2);
		startVoting(reVoting2);
		
		votingData reVoting3[] = {new votingData(1, 0, WhitePartyNum)};
		Thread.sleep(waiting3);
		startVoting(reVoting3);
		
		expectedPartiesList = initialPartiesList.copy();
		expectedPartiesList.getPartyBySymbol(getSymbolByPlace(2)).increaseVoteNumber();
	}
	
	/**
	 * tests the regularity of system if the time between votes (the voting intensity) is
	 * 'timeBetweenVotes'
	 * @param timeBetweenVotes - time between two votes (in milliseconds)
	 * @throws Exception if an error occurs
	 */
	private void testBackUp(long timeBetweenVotes) throws Exception{
		//voting time during test is 5 seconds
		final long finisingAfter = 5 * 1000;
		//after voting we wait for backup to kick in
		final long waitingForBackup = (backupTimeIntervalSeconds + 2) * 1000;

		int id = 1;
		
		
		int parties[] = new int[initialPartiesList.size()];
		for (int i = 0; i < parties.length; i++) {
			parties[i] = i;
		}
		int stations[] = new int[numVotingStations];
		for (int i = 0; i < stations.length; i++) {
			stations[i] = i;
		}
		votingData voting[] = new votingData[1];
		long finalTime = System.currentTimeMillis() + finisingAfter;
		while(System.currentTimeMillis() < finalTime){
			voting[0] = new votingData(id, stations[id%stations.length], parties[id % parties.length]);
			startVoting(voting);
			Thread.sleep(timeBetweenVotes);
			id++;
		}
		Thread.sleep(waitingForBackup);
		mainframe.crash();
		checkBackUp();
	}
	
	/**
	 * test a scenario when the voting intensity is high
	 * voting intensity = number of votes per time unit
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testIntensiveHotBackUp() throws Exception{
		testBackUp(50);
	}
	
	/**
	 * test a scenario when the voting intensity is average
	 * voting intensity = number of votes per time unit
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testNormalHotBackUp() throws Exception{
		testBackUp(500);
	}
	
	/**
	 * test a scenario when the voting intensity is low
	 * voting intensity = number of votes per time unit
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testSlowHotBackUp() throws Exception{
		testBackUp(5000);
	}
	
}
