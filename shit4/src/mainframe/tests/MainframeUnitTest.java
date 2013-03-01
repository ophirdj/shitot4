package mainframe.tests;

import static org.junit.Assert.*;

import mainframe.communication.IStationsControllerFactory;
import mainframe.factories.IMainframeWindowFactory;
import mainframe.logic.IMainframe;
import mainframe.logic.Mainframe;

import org.junit.Before;
import org.junit.Test;

import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import partiesList.factories.PartiesListFactory;
import partiesList.factories.PartyFactory;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;

import fileHandler.factories.IBackupFactory;
import fileHandler.factories.IReadSuppliedXMLFactory;

import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import votersList.factories.VoterDataFactory;
import votersList.factories.VotersListFactory;
import votersList.model.IVoterData;
import votersList.model.IVotersList;

public class MainframeUnitTest {
	
	
	
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
	IStationsControllerFactory stationsControllerStubFactory = new StationsControllerStubFactory();
	IVoterDataFactory voterDataStubFactory = new VoterDataFactory();
	IVotersListFactory votersListStubFactory = new VotersListFactory();
	ReadSuppliedXMLStubFactory readSuppliedXMLStubFactory = new ReadSuppliedXMLStubFactory(); 
	
	IMainframe mainframe;
	IVotersList readVotersList;
	IPartiesList readPartiesList;
	
	@Before
	public void preprocessing(){
		mainframe = new Mainframe(backupStubFactory
				, mainframeWindowStubFactory
				, readSuppliedXMLStubFactory
				, stationsControllerStubFactory
				, voterDataStubFactory
				, votersListStubFactory);
		
		
		
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
		
		
		IMainframe m = new Mainframe(backupStubFactory
									, mainframeWindowStubFactory
									, readSuppliedXMLStubFactory
									, stationsControllerStubFactory
									, voterDataStubFactory
									, votersListStubFactory);
		
		
		
	}

	@Test
	public void testInitialize() {
		mainframe.initialize();
		
	}

	@Test
	public void testRestore() {
		fail("Not yet implemented");
	}

	@Test
	public void testCountVotes() {
		fail("Not yet implemented");
	}

	@Test
	public void testShutDown() {
		mainframe.shutDown();
		//no check if the backup is correct
		IPartiesList p = backupStubFactory.getCreatedBackupStub().restoreParties();
		IVotersList v = backupStubFactory.getCreatedBackupStub().restoreVoters();
		IVotersList u = backupStubFactory.getCreatedBackupStub().restoreUnregisteredVoters();
	}
	

	@Test
	public void testIdentification() {
		fail("Not yet implemented");
	}

	@Test
	public void testPeep() {
		fail("Not yet implemented");
	}

	@Test
	public void testMarkVoted() {
		fail("Not yet implemented");
	}

	@Test
	public void testMarkStartedVote() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVoterStatus() {
		fail("Not yet implemented");
	}

	@Test
	public void testRun() {
		fail("Not yet implemented");
	}

}
