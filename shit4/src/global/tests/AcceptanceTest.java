package global.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mainframe.communication.IStationsControllerFactory;
import mainframe.factories.IMainframeFactory;
import mainframe.factories.IMainframeWindowFactory;
import mainframe.factories.MainframeFactory;
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
import votingStation.factories.IVotingStationFactory;
import votingStation.factories.IVotingStationWindowFactory;
import votingStation.factories.VotingStationFactory;
import org.junit.*;

public class AcceptanceTest {
	
	private int numVotingStations = 4;
	private int numPracticeStations = 2;
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
		IReadSuppliedXMLFactory readSuppliedXMLFactory = new ReadSuppliedXMLTestFactory(partiesListFactory, partyFactory, votersListFactory, voterDataFactory);
		
		IChoosingWindowFactory choosingWindowFactory = new ChoosingWindowStubFactory(this);
		IChoosingListFactory choosingListFactory = new ChoosingListFactory(choosingWindowFactory);
		IVotingStationWindowFactory votingStationWindowFactory = new VotingStationWindowStubFactory(this);
		IVotingStationFactory votingStationFactory = new VotingStationFactory(choosingListFactory, votingStationWindowFactory);
		IStationsControllerFactory stationsControllerFactory = new StationsControllerTestFactory(votingStationFactory, passwords, numVotingStations);
		
		IBackupFactory backupFactory = new BackupTestFactory(partiesListFactory, partyFactory, votersListFactory, voterDataFactory, backupVotersListFile, backupPartiesListFile, backupUnregisteredFile);
		IMainframeFactory mainframeFactory = new MainframeFactory(backupFactory, mainframeWindowFactory, readSuppliedXMLFactory, stationsControllerFactory, voterDataFactory, votersListFactory);
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
		
		expectedPartiesList = initialPartiesList;
		expectedVotersList = initialVotersList;
		expectedUnregisteredList = new VotersList();
	}
	
	
	@After
	public void checkBackupLists() throws Exception{
		IPartyFactory partyFactory = new PartyFactory();
		IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		IVotersListFactory votersListFactory = new VotersListFactory();
		IVoterDataFactory voterDataFactory = new VoterDataFactory();
		
		IBackup backup = new Backup(partiesListFactory, partyFactory, votersListFactory, voterDataFactory, backupVotersListFile, backupPartiesListFile, backupUnregisteredFile);
		IPartiesList resultParties = backup.restoreParties();
		IVotersList resultVoters = backup.restoreVoters();
		IVotersList reusltUnregistered = backup.restoreUnregisteredVoters();
		Assert.assertEquals(expectedPartiesList,resultParties);
		Assert.assertEquals(expectedVotersList, resultVoters);
		Assert.assertEquals(expectedUnregisteredList, reusltUnregistered);
	}

	public void addVotingWindowStub(VotingStationWindowStub stub) {
		votingWindowStubs.add(stub);
	}

	public void addImagePanelStub(ImagePanelStub stub, StationPanel caller) {
		imagePanelStubs.put(caller, stub);
	}
	
	public void addChoosingWindowStub(ChoosingWindowStub stub,
			StationPanel stationPanel) {
		choosingWindowStubs.put(stationPanel, stub);
		
	}

	public void addPracticeStationWindowStub(PracticeStationWindowStub stub) {
		practiceStationWindowStubs.add(stub);
	}

	public void setMainframeWindowStub(MainframeWindowStub stub) {
		mainframeWindowStub = stub;
	}
	
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
	
	private void identify(votingData[] voting) throws Exception{
		for(votingData data : voting){
			int id = data.getId();
			try {
				mainframe.identification(id);
				if(!initialVotersList.inList(id)){
					IVoterData toAddUnreg = new VoterData(id);
					expectedUnregisteredList.addVoter(toAddUnreg);
					IVoterData toAdd = new VoterData(id);
					expectedVotersList.addVoter(toAdd);
				}
				IVoterData voter = initialVotersList.findVoter(id);
				voter.markIdentified();
			} catch (IdentificationError e) {
			}
		}
	}
	
	private void doVotes(votingData[] voting) throws Exception{
		for(votingData singleVoting : voting){
			int id = singleVoting.getId();
			int party = singleVoting.getParty();
			int station = singleVoting.getStation();
			
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
	
	private void startVoting(votingData[] voting) throws Exception{
		identify(voting);
		doVotes(voting);
	}
	
	
	
	
	
	@Test
	public void testInitialize(){
		mainframe.initialize();
		Assert.assertEquals(numVotingStations, votingWindowStubs.size());
		Assert.assertNotNull(mainframeWindowStub);
		Assert.assertEquals(numPracticeStations, practiceStationWindowStubs.size());
		for(PracticeStationWindowStub stub: practiceStationWindowStubs){
			Assert.assertTrue(imagePanelStubs.containsKey(stub));
			Assert.assertNotNull(imagePanelStubs.get(stub));
		}
		mainframe.shutDown();
		expectedPartiesList = initialPartiesList;
		expectedVotersList = initialVotersList;
		
	}
	
	
	@Test
	public void testOneVote() throws Exception{
		mainframe.initialize();

		votingData voting[] = {new votingData(1,0,0)};
		
		startVoting(voting);
		mainframe.shutDown();
	}
	
	


	@Test
	public void testOneVoteFromUnregisteredVoter() throws Exception{
		mainframe.initialize();
		
		votingData voting[] = {new votingData(4,0,0)};
		startVoting(voting);
		mainframe.shutDown();
	}
	
	@Test
	public void testOneIdentification() throws Exception{
		mainframe.initialize();

		int id0 = 1;
		
		mainframe.identification(id0);
		mainframe.shutDown();
		
		expectedVotersList.findVoter(id0).markIdentified();
	}
	
	@Test
	public void testIdentificationFromUnregisteredVoter() throws Exception{
		mainframe.initialize();
	
		int id0 = 4;
		
		mainframe.identification(id0);
		mainframe.shutDown();
		
		IVoterData toAdd = new VoterData(id0);
		toAdd.markIdentified();
		IVoterData toAddUnreg = new VoterData(id0);
		
		expectedVotersList.addVoter(toAdd);
		expectedUnregisteredList.addVoter(toAddUnreg);
	}
	
	
	
	@Test
	public void testFirstVotingAllParties() throws Exception{
		mainframe.initialize();
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
		mainframeWindowStub.setExpectedPartiesList(expectedPartiesList);
		mainframe.countVotes();
		mainframe.shutDown();
	}
	
	@Test
	public void testFirstVotingManyParties() throws Exception{
		mainframe.initialize();
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
		mainframeWindowStub.setExpectedPartiesList(expectedPartiesList);
		mainframe.countVotes();
		mainframe.shutDown();
	}
	
	
	@Test
	public void testFirstVotingNoVoting() throws Exception{
		mainframe.initialize();
		mainframeWindowStub.setExpectedPartiesList(expectedPartiesList);
		mainframe.countVotes();
		mainframe.shutDown();
	}
	
	@Test
	public void testFirstFewVotes() throws Exception{
		mainframe.initialize();
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
		mainframeWindowStub.setExpectedPartiesList(expectedPartiesList);
		mainframe.countVotes();
		mainframe.shutDown();
	}
	
	@Test
	public void testRevoteOnce() throws Exception{
		mainframe.initialize();
		votingData voting[] = {new votingData(1, 0, 0)};
		startVoting(voting);
		votingData reVoting[] = {new votingData(1, 0, WhitePartyNum)};
		startVoting(reVoting);
		expectedPartiesList = initialPartiesList;
		expectedPartiesList.peep();
		peepParties();
	}
	
	
	
	
	
}
