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
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import votersList.factories.VoterDataFactory;
import votersList.factories.VotersListFactory;
import votersList.model.IVoterData;
import votersList.model.IVotersList;
import votersList.model.IVoterData.Unidentified;
import votersList.model.IVotersList.VoterDoesntExist;

/**
 * Integration test for mainframe and file handler packages.
 * It tests the interaction between the main classes of the above mentioned packages. 
 * @author Daniel
 *
 */

public class IntegrationTest {
	
	private final int backupTimeIntervalSeconds = 2;	
	
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
	 * Preprocessing routine.
	 * Creates a mainframe instance.
	 * Generates lists which are functionally equivalent to the
	 * supplied starting files.
	 */
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
	
	/**
	 * Restore testing routine.
	 * Crashes and restores the mainframe, and later compares the consistency of the restore.
	 * Uses backup as method of accessing the mainframe data.
	 * @throws Exception
	 */
	@Test
	public void testRestore() throws Exception{
		mainframe.initialize();
		for(int i=101;i<=200;i++){
			mainframe.identification(i);
		}
		
		synchronized(this){
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
	
	
	
	
	/**
	 * Backup shutdown testing routine.
	 * Checks the consistency of the backup files after shutdown.
	 * @throws Exception
	 */
	@Test
	public void testBackupShutdown() throws Exception{
		mainframe.initialize();
		for(int i=101;i<=200;i++){
			mainframe.identification(i);
		}
		
		synchronized(this){
			Thread.sleep(6*backupTimeIntervalSeconds*1000);
			mainframe.crash();
			mainframe.restore();
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
	
	
	/**
	 * Backup crash testing routine.
	 * Checks the consistency of the backup files after crash.
	 * @throws Exception
	 */
	@Test
	public void testBackupCrash() throws Exception{
		mainframe.initialize();
		for(int i=101;i<=200;i++){
			mainframe.identification(i);
		}
		
		synchronized(this){
			Thread.sleep(6*backupTimeIntervalSeconds*1000);
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
	
	
	
	/**
	 * Identification error testing routine.
	 * Checks that the identification was not broken by backup. 
	 * @throws IdentificationError - expected.
	 */
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
	
	
	
	
	/**
	 * Voter backup testing routine.
	 * Checks the backup doesn't generate extra data.
	 * @throws InterruptedException
	 * @throws VoterDoesntExist - expected
	 */
	@Test(expected = VoterDoesntExist.class)
	public void testBackupVoterError() throws InterruptedException, VoterDoesntExist{
		mainframe.initialize();
		
		synchronized(this){
			mainframe.shutDown();
			IBackup tempBackup = backupFactoryInt.createInstance();
			IVotersList vlist = tempBackup.restoreVoters();
			vlist.findVoter(800);
		}
	}
	
	/**
	 * Party backup testing routine.
	 * Checks the backup doesn't generate extra data.
	 * @throws InterruptedException
	 * @throws PartyDoesNotExist - expected
	 */
	@Test(expected = PartyDoesNotExist.class)
	public void testBackupPartyError() throws InterruptedException, PartyDoesNotExist{
		mainframe.initialize();
		
		synchronized(this){
			mainframe.shutDown();
			IBackup tempBackup = backupFactoryInt.createInstance();
			IPartiesList plist = tempBackup.restoreParties();
			plist.getPartyBySymbol("ERROR");
		}
	}
	
	
	/**
	 * Unregistered voter backup testing routine.
	 * Checks the backup doesn't generate extra data.
	 * @throws InterruptedException
	 * @throws VoterDoesntExist - expected
	 */
	@Test(expected = VoterDoesntExist.class)
	public void testBackupUnregVoterError() throws InterruptedException, VoterDoesntExist{
		mainframe.initialize();
		
		synchronized(this){
			mainframe.shutDown();
			IBackup tempBackup = backupFactoryInt.createInstance();
			IVotersList vlist = tempBackup.restoreUnregisteredVoters();
			vlist.findVoter(800);
		}
	}
	
	
	
	/**
	 * Vote backup testing routine.
	 * Checks the saved number of votes for a party.
	 * @throws IdentificationError
	 * @throws PartyDoesNotExist
	 * @throws InterruptedException
	 * @throws VoterDoesNotExist
	 */
	@Test
	public void testCountVotes() throws IdentificationError, PartyDoesNotExist, InterruptedException, VoterDoesNotExist{
		mainframe.initialize();
		
		synchronized(this){
			for(int i=1;i<=100;i++){
				mainframe.identification(i);
				this.stationsControllerStubFactory.getStationsController().markVoted(i);
			}
			mainframe.shutDown();
			IBackup tempBackup = backupFactoryInt.createInstance();
			IPartiesList plist = tempBackup.restoreParties();
			assertEquals(100, plist.getPartyBySymbol("p1").getVoteNumber());
		}
	}
	
	
	
	/**
	 * Voter identification testing routine.
	 * Checks that the voter identification status id saved correctly.
	 * @throws IdentificationError
	 * @throws VoterDoesntExist
	 */
	@Test
	public void testIdentify() throws IdentificationError, VoterDoesntExist{
		mainframe.initialize();
		synchronized(this){
			for(int i=1;i<=50;i++){
				mainframe.identification(i);
			}
			mainframe.shutDown();
			IBackup tempBackup = backupFactoryInt.createInstance();
			IVotersList vlist = tempBackup.restoreVoters();
			for(int i=1;i<=50;i++){
				assertTrue(vlist.findVoter(i).isIdentified());
			}
			for(int i=51;i<=100;i++){
				assertFalse(vlist.findVoter(i).isIdentified());
			}
			
		}
	}
	
	
	
	/**
	 * Marking error testing routine.
	 * Checks that the voter marking was not broken by backup. 
	 * @throws VoterDoesNotExist
	 * @throws Unidentified
	 * @throws VoterDoesntExist
	 * @throws IdentificationError
	 */
	@Test(expected = VoterDoesNotExist.class)
	public void testMarkVoted() throws VoterDoesNotExist, Unidentified, VoterDoesntExist, IdentificationError {
		mainframe.initialize();
		try{
			mainframe.markVoted(800);
		} catch(VoterDoesNotExist e){
			mainframe.shutDown();
			throw e;
		}
		
	}
	
	

	
	
	
	
	
	
	
	
	
}




