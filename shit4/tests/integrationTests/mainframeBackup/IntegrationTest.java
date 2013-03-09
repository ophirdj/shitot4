package integrationTests.mainframeBackup;

import static org.junit.Assert.*;

import org.junit.*;
import integrationTests.mainframeBackup.BackupFactoryInt;
import fileHandler.factories.IBackupFactory;
import fileHandler.factories.IReadSuppliedXMLFactory;
import fileHandler.model.IBackup;
import mainframe.logic.IMainframe;
import mainframe.logic.IMainframe.IdentificationError;
import mainframe.logic.IMainframe.VoterDoesNotExist;
import mainframe.logic.Mainframe;
import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import partiesList.factories.PartiesListFactory;
import partiesList.factories.PartyFactory;
import partiesList.model.IPartiesList;
import partiesList.model.IPartiesList.PartyDoesNotExist;
import partiesList.model.IParty;
import integrationTests.mainframeBackup.MainframeWindowStubFactory;
import integrationTests.mainframeBackup.StationsControllerStubFactory;
import integrationTests.mainframeBackup.StationsControllerStub;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import votersList.factories.VoterDataFactory;
import votersList.factories.VotersListFactory;
import votersList.model.IVoterData;
import votersList.model.IVotersList;
import votersList.model.IVotersList.VoterDoesntExist;

public class IntegrationTest {
	/**
	 * time for hot backup test
	 */
	private final int backupTimeIntervalSeconds = 1;	
	
	IPartyFactory partyFactory = new PartyFactory();
	IVoterDataFactory voterDataFactory = new VoterDataFactory();
	IVotersListFactory votersListFactory = new VotersListFactory();
	IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
	
	//stub factories
	
	IBackupFactory backupFactoryInt = new BackupFactoryInt(partiesListFactory, partyFactory, votersListFactory, voterDataFactory) ;
	MainframeWindowStubFactory mainframeWindowStubFactory = new MainframeWindowStubFactory();
	StationsControllerStubFactory stationsControllerStubFactory = new StationsControllerStubFactory();
	IReadSuppliedXMLFactory readSuppliedXMLFactoryInt = new ReadSuppliedXMLFactoryInt(partiesListFactory, partyFactory, votersListFactory, voterDataFactory); 
	
	IMainframe mainframe;
	IVotersList readVotersList;
	IPartiesList readPartiesList;
	
	/**
	 * invokes the method 'markVoted' of the stub 'stationsControllerStub' 'numOfVotes' times
	 * @param numOfVotes the number of times we want the station controller to know that we voted
	 */
	private void markVotesOnStationController(int numOfVotes){
		for(int i=0; i<numOfVotes; i++){
			try {
				this.stationsControllerStubFactory.getStationsController().markVoted(-1);
			} catch (VoterDoesNotExist e) {
				e.printStackTrace();
			}
		}
	}
	
	@Before
	public void preprocessing(){
		//we create a mainframe
		mainframe = new Mainframe(backupTimeIntervalSeconds, backupFactoryInt
				, mainframeWindowStubFactory
				, readSuppliedXMLFactoryInt
				, stationsControllerStubFactory
				, voterDataFactory
				, votersListFactory);
		
		
		//create a little voters list
		readVotersList = votersListFactory.createInstance();
		for(int i=1; i<=100; i++){
			IVoterData voter = voterDataFactory.createInstance(i);
			readVotersList.addVoter(voter);
		}
		//create a little parties list
		readPartiesList = partiesListFactory.createInstance();
		for(int i=1; i<=20; i++){
			IParty party = partyFactory.createInstance("p"+i, "p"+i, 0);
			readPartiesList.addParty(party);
		}
		

	}
	
	
	
	@Test
	public void testBackupShutdown() throws Exception{
		mainframe.initialize();
		for(int i=101;i<=200;i++){
			mainframe.identification(i);
		}
		
		synchronized(this){
			Thread.sleep(4*backupTimeIntervalSeconds*1000);
			mainframe.shutDown();
			IBackup tempBackup = backupFactoryInt.createInstance();
			IVotersList vlist = tempBackup.restoreVoters();
			for(int i=1; i<=100;i++){
				IVoterData voter = vlist.findVoter(i);
				assertEquals(i, voter.getId());
			}
			IPartiesList plist = tempBackup.restoreParties();
			for(int i=1; i<=20;i++){
					IParty party = plist.getPartyBySymbol("p"+i);
					assertEquals(0, party.getVoteNumber());
					assertEquals("p"+i, party.getSymbol());
			}
			IVotersList uvlist = tempBackup.restoreUnregisteredVoters();
			for(int i=101; i<=200;i++){
				IVoterData voter = uvlist.findVoter(i);
				assertEquals(i, voter.getId());
			}
		}
	}
	
	@Test
	public void testBackupCrash() throws Exception{
		mainframe.initialize();
		for(int i=101;i<=200;i++){
			mainframe.identification(i);
		}
		
		synchronized(this){
			Thread.sleep(4*backupTimeIntervalSeconds*1000);
			mainframe.crash();
			IBackup tempBackup = backupFactoryInt.createInstance();
			IVotersList vlist = tempBackup.restoreVoters();
			for(int i=1; i<=100;i++){
				IVoterData voter = vlist.findVoter(i);
				assertEquals(i, voter.getId());
			}
			IPartiesList plist = tempBackup.restoreParties();
			for(int i=1; i<=20;i++){
					IParty party = plist.getPartyBySymbol("p"+i);
					assertEquals(0, party.getVoteNumber());
					assertEquals("p"+i, party.getSymbol());
			}
			IVotersList uvlist = tempBackup.restoreUnregisteredVoters();
			for(int i=101; i<=200;i++){
				IVoterData voter = uvlist.findVoter(i);
				assertEquals(i, voter.getId());
			}
		}
	}
	
	@Test(expected = IdentificationError.class)
	public void testIdentifyError() throws IdentificationError{
		mainframe.initialize();
		try{//Super dirty hack
			mainframe.identification(450);
			mainframe.identification(450);
		} catch(IdentificationError e){
			mainframe.shutDown();
			throw e;
		}
	}
	
	@Test(expected = VoterDoesntExist.class)
	public void testBackupVoterError() throws InterruptedException, VoterDoesntExist{
		mainframe.initialize();
		
		synchronized(this){
			Thread.sleep(4*backupTimeIntervalSeconds*1000);
			mainframe.shutDown();
			IBackup tempBackup = backupFactoryInt.createInstance();
			IVotersList vlist = tempBackup.restoreVoters();
			vlist.findVoter(800);
		}
	}
	
	
	@Test(expected = PartyDoesNotExist.class)
	public void testBackupPartyError() throws InterruptedException, PartyDoesNotExist{
		mainframe.initialize();
		
		synchronized(this){
			Thread.sleep(4*backupTimeIntervalSeconds*1000);
			mainframe.shutDown();
			IBackup tempBackup = backupFactoryInt.createInstance();
			IPartiesList plist = tempBackup.restoreParties();
			plist.getPartyBySymbol("ERROR");
		}
	}
	
	@Test(expected = VoterDoesntExist.class)
	public void testBackupUnregVoterError() throws InterruptedException, VoterDoesntExist{
		mainframe.initialize();
		
		synchronized(this){
			Thread.sleep(4*backupTimeIntervalSeconds*1000);
			mainframe.shutDown();
			IBackup tempBackup = backupFactoryInt.createInstance();
			IVotersList vlist = tempBackup.restoreUnregisteredVoters();
			vlist.findVoter(800);
		}
	}
	
	
	
	
	@Test
	public void testCountVotes() throws IdentificationError, PartyDoesNotExist, InterruptedException, VoterDoesNotExist{
		mainframe.initialize();
		
		synchronized(this){
			for(int i=1;i<=100;i++){
				mainframe.identification(i);
				this.stationsControllerStubFactory.getStationsController().markVoted(i);
				System.out.println("Voting"+i);
			}
			//markVotesOnStationController(100);
		//	Thread.sleep(4*backupTimeIntervalSeconds*1000);
			mainframe.shutDown();
			System.out.println("shutdown");
			IBackup tempBackup = backupFactoryInt.createInstance();
			System.out.println("Backup step1");
			IPartiesList plist = tempBackup.restoreParties();
			System.out.println("Backup step1");
			assertEquals(100, plist.getPartyBySymbol("p1").getVoteNumber());
		}
	}
	
	
	
	
	
	
	
	
	
}




