package mainframe.tests;

import static org.junit.Assert.*;

import mainframe.communication.IStationsControllerFactory;
import mainframe.factories.IMainframeWindowFactory;
import mainframe.logic.IMainframe;
import mainframe.logic.IMainframe.IdentificationError;
import mainframe.logic.IMainframe.VoterDoesNotExist;
import mainframe.logic.IMainframe.VoterStartedVote;
import mainframe.logic.Mainframe;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import partiesList.factories.PartiesListFactory;
import partiesList.factories.PartyFactory;
import partiesList.model.IPartiesList;
import partiesList.model.IPartiesList.PartyDoesNotExist;
import partiesList.model.IParty;
import practiceStation.guides.ImagePanel.image_action;

import fileHandler.factories.IBackupFactory;
import fileHandler.factories.IReadSuppliedXMLFactory;

import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import votersList.factories.VoterDataFactory;
import votersList.factories.VotersListFactory;
import votersList.model.IVoterData;
import votersList.model.IVoterData.AlreadyIdentified;
import votersList.model.IVoterData.Unidentified;
import votersList.model.IVotersList;
import votersList.model.IVotersList.VoterDoesntExist;

public class MainframeUnitTest {
	
	
	private final int backupTimeIntervalSeconds = 180;	
	
	
	IPartyFactory partyFactory = new PartyFactory();
	IVoterDataFactory voterDataFactory = new VoterDataFactory();
	IVotersListFactory votersListFactory = new VotersListFactory();
	IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
	
	/*
	//VoterDataStub voterDataStub;
	//VotersListStub votersListStub;
	BackupStub backupStub;
	MainframeWindowStub mainframeWindowStub;
	ReadSuppliedXMLStub readSuppliedXMLStub;
	StationsControllerStub stationsControllerStub;*/
	
	
	
	//stub factories
	
	//IBackupFactory backupStubFactory = new BackupStubFactory() ;
	BackupStubFactory backupStubFactory = new BackupStubFactory() ;
	IMainframeWindowFactory mainframeWindowStubFactory = new MainframeWindowStubFactory();
	StationsControllerStubFactory stationsControllerStubFactory = new StationsControllerStubFactory();
	//IVoterDataFactory voterDataStubFactory = new VoterDataFactory();
	//IVotersListFactory votersListStubFactory = new VotersListFactory();
	ReadSuppliedXMLStubFactory readSuppliedXMLStubFactory = new ReadSuppliedXMLStubFactory(); 
	
	IMainframe mainframe;
	IVotersList readVotersList;
	IPartiesList readPartiesList;
	
	
	
	private void markVotesOnStationController(int numOfVotes){
		for(int i=0; i<numOfVotes; i++){
			try {
				this.stationsControllerStubFactory.getStationsController().markVoted(-1);
			} catch (VoterDoesNotExist e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	@Before
	public void preprocessing(){
		mainframe = new Mainframe(backupTimeIntervalSeconds, backupStubFactory
				, mainframeWindowStubFactory
				, readSuppliedXMLStubFactory
				, stationsControllerStubFactory
				, voterDataFactory
				, votersListFactory);
		
		
		
		readVotersList = votersListFactory.createInstance();
		IVoterData v1 = voterDataFactory.createInstance(111);
		IVoterData v2 = voterDataFactory.createInstance(222);
		IVoterData v3 = voterDataFactory.createInstance(333);
		IVoterData v4 = voterDataFactory.createInstance(444);
		IVoterData v5 = voterDataFactory.createInstance(555);
		readVotersList.addVoter(v1);
		readVotersList.addVoter(v2);
		readVotersList.addVoter(v3);
		readVotersList.addVoter(v4);
		readVotersList.addVoter(v5);
		
		
		readPartiesList = partiesListFactory.createInstance();
		IParty p1 = partyFactory.createInstance("Vive la France", "alors on dance!");
		IParty p2 = partyFactory.createInstance("יש עתיד", "יש");
		IParty p3 = partyFactory.createInstance("אין עתיד", "fuck you");
		IParty p4 = partyFactory.createInstance("Liberte", "oui");
		IParty p5 = partyFactory.createInstance("egalite", "non");
		IParty p6 = partyFactory.createInstance("fraternite", "שלום");
		IParty p7 = partyFactory.createInstance("technion", "Ullman's metal dick");
		readPartiesList.addParty(p1);
		readPartiesList.addParty(p2);
		readPartiesList.addParty(p3);
		readPartiesList.addParty(p4);
		readPartiesList.addParty(p5);
		readPartiesList.addParty(p6);
		readPartiesList.addParty(p7);
		
		
		//we want to put these two into the object 
		//that is returned by readSuppliedXMLStubFactory
		
		readSuppliedXMLStubFactory.getReadSuppliedXMLStub().setReadPartiesList(readPartiesList);
		readSuppliedXMLStubFactory.getReadSuppliedXMLStub().setReadVotersList(readVotersList);

		
		
		
		
		
		
		
	}

	@Test
	public void testMainframeBuild() {
		
		
		IMainframe m = new Mainframe(backupTimeIntervalSeconds, backupStubFactory
									, mainframeWindowStubFactory
									, readSuppliedXMLStubFactory
									, stationsControllerStubFactory
									, voterDataFactory
									, votersListFactory);
		
		
		
	}

	@Test
	public void testInitialize(){
		mainframe.initialize();
	}

	@Test
	public void testRestore() throws AlreadyIdentified, VoterDoesntExist, Unidentified {
		mainframe.initialize();
		backupStubFactory.getCreatedBackupStub().setBackupedPartiesList(readPartiesList);
		backupStubFactory.getCreatedBackupStub().setBackupedVotersList(readVotersList);
		IVotersList temp = votersListFactory.createInstance();
		temp.addVoter(voterDataFactory.createInstance(123));
		temp.findVoter(123).markIdentified();
		temp.findVoter(123).markStartedVote();
		backupStubFactory.getCreatedBackupStub().setBackupedUnregisteredVotersList(temp);
		mainframe.restore();
		
		mainframe.shutDown();
		IPartiesList p = backupStubFactory.getCreatedBackupStub().restoreParties();
		IVotersList v = backupStubFactory.getCreatedBackupStub().restoreVoters();
		IVotersList u = backupStubFactory.getCreatedBackupStub().restoreUnregisteredVoters();
		assertEquals(readPartiesList, p);
		assertEquals(readVotersList,v);
		assertEquals(temp,u);
		
	}

	@Test
	public void testCountVotes() {
		fail("Not yet implemented");
	}

	@Test
	public void testShutDown() {
		assertNotNull(readSuppliedXMLStubFactory.getReadSuppliedXMLStub().readPartiesList());
		assertNotNull(readSuppliedXMLStubFactory.getReadSuppliedXMLStub().readVotersList());
		mainframe.initialize();
		mainframe.shutDown();
		//no check if the backup is correct
		IPartiesList p = backupStubFactory.getCreatedBackupStub().restoreParties();
		IVotersList v = backupStubFactory.getCreatedBackupStub().restoreVoters();
		IVotersList u = backupStubFactory.getCreatedBackupStub().restoreUnregisteredVoters();
		assertEquals(votersListFactory.createInstance(), u);
		//votersListFactory.createInstance().peep();
		assertEquals(readVotersList, v);
		assertEquals(readPartiesList, p);
	}
	

	@Test
	public void testIdentification() throws IdentificationError, AlreadyIdentified, VoterDoesntExist {
		mainframe.initialize();
		mainframe.identification(111);
		mainframe.shutDown();
		IPartiesList p = backupStubFactory.getCreatedBackupStub().restoreParties();
		IVotersList v = backupStubFactory.getCreatedBackupStub().restoreVoters();
		IVotersList u = backupStubFactory.getCreatedBackupStub().restoreUnregisteredVoters();
		assertEquals(votersListFactory.createInstance(), u);
		assertTrue(!readVotersList.equals( v));
		assertEquals(readPartiesList, p);
		readVotersList.findVoter(111).markIdentified();
		assertEquals(readVotersList, v);
	}
	
	//same voter identifies more than once
	@Test(expected = IMainframe.IdentificationError.class )
	public void testIdentification2() throws IdentificationError, AlreadyIdentified, VoterDoesntExist {
		mainframe.initialize();
		mainframe.identification(111);
		mainframe.identification(222);
		mainframe.identification(111);
	}
	
	
	//check if an unregistered voter is written to the file
	@Test
	public void testIdentification3() throws IdentificationError, AlreadyIdentified, VoterDoesntExist {
		mainframe.initialize();
		mainframe.identification(123);
		mainframe.shutDown();
		IPartiesList p = backupStubFactory.getCreatedBackupStub().restoreParties();
		IVotersList v = backupStubFactory.getCreatedBackupStub().restoreVoters();
		IVotersList u = backupStubFactory.getCreatedBackupStub().restoreUnregisteredVoters();
		assertTrue(!votersListFactory.createInstance().equals( u));
		assertTrue(!readVotersList.equals( v));
		assertEquals(readPartiesList, p);
		readVotersList.addVoter(voterDataFactory.createInstance(123));
		readVotersList.findVoter(123).markIdentified();
		assertEquals(readVotersList, v);
		IVotersList temp = votersListFactory.createInstance();
		temp.addVoter(voterDataFactory.createInstance(123));
		temp.findVoter(123).markIdentified();
		assertEquals(temp,u);
	}

	@Test
	public void testPeep() {
		//always works
	}

	@Test(expected = IMainframe.VoterDoesNotExist.class)
	public void testMarkVoted() throws VoterDoesNotExist, Unidentified, VoterDoesntExist, IdentificationError {
		mainframe.initialize();
		mainframe.identification(111);
		mainframe.markVoted(111);
		mainframe.markVoted(222);
		
	}
	
	@Test
	public void testMarkVoted2() throws VoterDoesNotExist, Unidentified, VoterDoesntExist, IdentificationError, AlreadyIdentified, PartyDoesNotExist {
		mainframe.initialize();
		mainframe.identification(111);
		mainframe.identification(222);
		mainframe.markVoted(111);
		mainframe.markVoted(222);
		markVotesOnStationController(2);
		mainframe.shutDown();
		IPartiesList p = backupStubFactory.getCreatedBackupStub().restoreParties();
		IVotersList v = backupStubFactory.getCreatedBackupStub().restoreVoters();
		IVotersList u = backupStubFactory.getCreatedBackupStub().restoreUnregisteredVoters();
		assertEquals(votersListFactory.createInstance(), u);
		assertTrue(!readPartiesList.equals( p));
		assertTrue(!readVotersList.equals( v));
		readPartiesList.getPartyBySymbol("oui").increaseVoteNumber();
		readPartiesList.getPartyBySymbol("oui").increaseVoteNumber();
		//readVotersList.peep();
		assertEquals(readPartiesList, p);
		readVotersList.findVoter(111).markIdentified();
		readVotersList.findVoter(111).markVoted();
		readVotersList.findVoter(222).markIdentified();
		readVotersList.findVoter(222).markVoted();
		assertTrue(readVotersList.equals( v));//TODO: recheck that
		
	}
	
	@Test(expected = IMainframe.VoterDoesNotExist.class)
	public void testMarkVoted3() throws IdentificationError, VoterDoesNotExist, Unidentified, VoterDoesntExist {
		mainframe.initialize();
		mainframe.markVoted(123);

	}
	
	@Test
	public void testMarkVoted4() throws IdentificationError, VoterDoesNotExist, Unidentified, VoterDoesntExist, AlreadyIdentified, PartyDoesNotExist {
		mainframe.initialize();
		mainframe.identification(123);
		mainframe.identification(222);
		mainframe.markVoted(123);
		mainframe.markVoted(222);
		markVotesOnStationController(2);
		mainframe.shutDown();
		IPartiesList p = backupStubFactory.getCreatedBackupStub().restoreParties();
		IVotersList v = backupStubFactory.getCreatedBackupStub().restoreVoters();
		IVotersList u = backupStubFactory.getCreatedBackupStub().restoreUnregisteredVoters();
		assertTrue(!votersListFactory.createInstance() .equals( u));
		assertFalse(u.isEmpty());
		assertTrue(!readPartiesList .equals( p));
		readPartiesList.getPartyBySymbol("oui").increaseVoteNumber();
		readPartiesList.getPartyBySymbol("oui").increaseVoteNumber();
		assertEquals(readPartiesList, p);
			
		assertTrue(!readVotersList .equals( v));
		IVotersList temp = votersListFactory.createInstance();
		IVoterData newVoter = voterDataFactory.createInstance(123);
		newVoter.markIdentified();
		newVoter.markVoted();
		temp.addVoter(newVoter);
		assertEquals(temp, u);
		readVotersList.addVoter(voterDataFactory.createInstance(123));
		readVotersList.findVoter(123).markIdentified();
		readVotersList.findVoter(123).markVoted();
		readVotersList.findVoter(222).markIdentified();
		readVotersList.findVoter(222).markVoted();
		assertEquals(readVotersList, v);
	}

	@Test
	public void testMarkStartedVote() throws VoterDoesNotExist, VoterStartedVote, IdentificationError, PartyDoesNotExist, Unidentified, VoterDoesntExist, AlreadyIdentified {
		mainframe.initialize();
		mainframe.identification(111);
		mainframe.markStartedVote(111);
		mainframe.identification(222);
		mainframe.markStartedVote(222);
		mainframe.shutDown();
		IPartiesList p = backupStubFactory.getCreatedBackupStub().restoreParties();
		IVotersList v = backupStubFactory.getCreatedBackupStub().restoreVoters();
		IVotersList u = backupStubFactory.getCreatedBackupStub().restoreUnregisteredVoters();
		assertEquals(votersListFactory.createInstance(), u);
		assertEquals(readPartiesList, p);
		assertTrue(!readVotersList .equals( v));
		readVotersList.findVoter(111).markIdentified();
		readVotersList.findVoter(111).markStartedVote();
		readVotersList.findVoter(222).markIdentified();
		readVotersList.findVoter(222).markStartedVote();
		assertEquals(readVotersList, v);	
	}
	
	@Test(expected = IMainframe.VoterStartedVote.class)
	public void testMarkStartedVote2() throws VoterDoesNotExist, VoterStartedVote, IdentificationError {
		mainframe.initialize();
		mainframe.identification(111);
		mainframe.markStartedVote(111);
		mainframe.markStartedVote(111);
	}
	
	@Test(expected = IMainframe.VoterDoesNotExist.class)
	public void testMarkStartedVote3() throws VoterDoesNotExist, VoterStartedVote {
		mainframe.initialize();
		mainframe.markStartedVote(123);
	}
	
	@Test
	public void testMarkStartedVote4() throws VoterDoesNotExist, VoterStartedVote, IdentificationError, PartyDoesNotExist, Unidentified, VoterDoesntExist, AlreadyIdentified {
		mainframe.initialize();
		mainframe.identification(123);
		mainframe.markStartedVote(123);
		mainframe.markVoted(123);
		markVotesOnStationController(1);
		mainframe.identification(456);
		mainframe.markStartedVote(456);
		mainframe.shutDown();
		IPartiesList p = backupStubFactory.getCreatedBackupStub().restoreParties();
		IVotersList v = backupStubFactory.getCreatedBackupStub().restoreVoters();
		IVotersList u = backupStubFactory.getCreatedBackupStub().restoreUnregisteredVoters();
		assertTrue(!votersListFactory.createInstance().equals(u));
		IVotersList temp = votersListFactory.createInstance();
		temp.addVoter(voterDataFactory.createInstance(123));
		temp.addVoter(voterDataFactory.createInstance(456));
		temp.findVoter(123).markIdentified();
		temp.findVoter(123).markVoted();
		temp.findVoter(456).markIdentified();
		temp.findVoter(456).markStartedVote();
		assertEquals(temp, u);
		assertTrue(!readPartiesList.equals(p));
		readPartiesList.getPartyBySymbol("oui").increaseVoteNumber();
		assertEquals(readPartiesList, p);
		assertTrue(!readVotersList .equals( v));
		readVotersList.addVoter(voterDataFactory.createInstance(123));
		readVotersList.addVoter(voterDataFactory.createInstance(456));
		readVotersList.findVoter(123).markIdentified();
		readVotersList.findVoter(123).markStartedVote();
		readVotersList.findVoter(123).markVoted();
		readVotersList.findVoter(456).markIdentified();
		readVotersList.findVoter(456).markStartedVote();
		assertEquals(readVotersList, v);	
	}
	
	@Test
	public void testMarkStartedVote5() throws VoterDoesNotExist, VoterStartedVote, IdentificationError, PartyDoesNotExist, Unidentified, VoterDoesntExist, AlreadyIdentified {
		mainframe.initialize();
		mainframe.identification(111);
		mainframe.markStartedVote(111);
		mainframe.markVoted(111);
		stationsControllerStubFactory.getStationsController().markVoted(111);
		mainframe.identification(222);
		mainframe.markStartedVote(222);
		mainframe.shutDown();
		IPartiesList p = backupStubFactory.getCreatedBackupStub().restoreParties();
		IVotersList v = backupStubFactory.getCreatedBackupStub().restoreVoters();
		IVotersList u = backupStubFactory.getCreatedBackupStub().restoreUnregisteredVoters();
		assertEquals(votersListFactory.createInstance(), u);
		assertTrue(!readPartiesList.equals(p));
		readPartiesList.getPartyBySymbol("oui").increaseVoteNumber();
		assertEquals(readPartiesList, p);
		assertTrue(!readVotersList .equals( v));
		readVotersList.findVoter(111).markIdentified();
		//readVotersList.findVoter(111).markStartedVote();
		readVotersList.findVoter(111).markVoted();
		readVotersList.findVoter(222).markIdentified();
		readVotersList.findVoter(222).markStartedVote();
		assertEquals(readVotersList, v);	
	}

	@Test
	public void testGetVoterStatus() throws IdentificationError, VoterDoesNotExist, VoterStartedVote {
		mainframe.initialize();
		IMainframe.VoterStatus stat;
		stat = mainframe.getVoterStatus(111);
		assertEquals(IMainframe.VoterStatus.unidentified, stat);
		mainframe.identification(111);
		stat = mainframe.getVoterStatus(111);
		assertEquals(IMainframe.VoterStatus.identified, stat);
		mainframe.markStartedVote(111);
		stat = mainframe.getVoterStatus(111);
		assertEquals(IMainframe.VoterStatus.startedVote, stat);
		mainframe.markVoted(111);
		stat = mainframe.getVoterStatus(111);
		assertEquals(IMainframe.VoterStatus.voted, stat);
		
		stat = mainframe.getVoterStatus(123);
		assertEquals(IMainframe.VoterStatus.unidentified, stat);
	}

	@Test
	public void testRun() {
		fail("Not yet implemented");
	}
	
	@Ignore
	public void hotBackupWorks() throws AlreadyIdentified, VoterDoesntExist, Unidentified{
		mainframe.initialize();
		backupStubFactory.getCreatedBackupStub().setBackupedPartiesList(readPartiesList);
		backupStubFactory.getCreatedBackupStub().setBackupedVotersList(readVotersList);
		IVotersList temp = votersListFactory.createInstance();
		temp.addVoter(voterDataFactory.createInstance(123));
		temp.findVoter(123).markIdentified();
		temp.findVoter(123).markStartedVote();
		backupStubFactory.getCreatedBackupStub().setBackupedUnregisteredVotersList(temp);
		
		//waiting 2 minutes
		/*
		 * just add it here
		 */
		
		
		IPartiesList p = backupStubFactory.getCreatedBackupStub().restoreParties();
		IVotersList v = backupStubFactory.getCreatedBackupStub().restoreVoters();
		IVotersList u = backupStubFactory.getCreatedBackupStub().restoreUnregisteredVoters();
		assertEquals(readPartiesList, p);
		assertEquals(readVotersList,v);
		assertEquals(temp,u);
	}

}
