package unitTests.fileHandler;

import org.junit.*;
import fileHandler.logic.Backup;
import partiesList.factories.PartiesListFactory;
import partiesList.factories.PartyFactory;
import partiesList.model.IPartiesList;
import partiesList.model.IPartiesList.PartyDoesNotExist;
import partiesList.model.PartiesList;
import partiesList.model.Party;
import votersList.factories.VoterDataFactory;
import votersList.factories.VotersListFactory;
import votersList.model.IVotersList;
import votersList.model.IVotersList.VoterDoesntExist;
import votersList.model.VoterData;
import votersList.model.VotersList;

/**
 * Unit tests for backup
 * @author Daniel Eidel
 *
 */

public class BackupUnitTest {
	private Backup backup;
	private IPartiesList partiesStub;
	private IVotersList votersStub;
	private IVotersList unregVotersStub;
	private IPartiesList partiesRestStub;
	private IVotersList votersRestStub;
	private IVotersList unregVotersRestStub;
	
	private String votersBackup = "FileHandlerTest/votersBackup.xml";
	private String partiesBackup = "FileHandlerTest/partiesBackup.xml";
	private String unregVotersBackup = "FileHandlerTest/unregVotersBackup.xml";
	
	/**
	 * Set up parties' and voters' lists and an active backup.
	 */
	@Before
	public void preprocessing(){
		int i;
		partiesStub = new PartiesList(new PartyFactory());
		votersStub = new VotersList();
		unregVotersStub = new VotersList();
		for(i=1; i<=100; i++){
			votersStub.addVoter(new VoterData(i));
			unregVotersStub.addVoter(new VoterData(i+100));
		}
		for(i=1; i<=20; i++){
			partiesStub.addParty(new Party("p"+Integer.toString(i), "p"+Integer.toString(i), i*10));
		}
		backup = new Backup(new PartiesListFactory(new PartyFactory()), new PartyFactory(), 
				new VotersListFactory(), new VoterDataFactory(), votersBackup, partiesBackup, unregVotersBackup);
		backup.storeState(partiesStub, votersStub, unregVotersStub);
	}
	
	/**
	 * Postcondition routine for internal consistency testing.
	 * @throws PartyDoesNotExist
	 * @throws VoterDoesntExist
	 */	
	@After
	public void posttest() throws PartyDoesNotExist, VoterDoesntExist{
		int i;
		for(i=1; i<=100; i++){
			Assert.assertEquals(i, votersStub.findVoter(i).getId());
			Assert.assertEquals(i+100, unregVotersStub.findVoter(i+100).getId());
		}
		for(i=1; i<=20; i++){
			Assert.assertEquals("p"+Integer.toString(i), partiesStub.getPartyBySymbol("p"+Integer.toString(i)).getSymbol());
			Assert.assertEquals(i*10, partiesStub.getPartyBySymbol("p"+Integer.toString(i)).getVoteNumber());
		}		
	}
	
	/**
	 * Simple backup state saving test.
	 */	
	@Test
	public void saveTest(){
		backup.storeState(partiesStub, votersStub, unregVotersStub);
	}
	
	/**
	 * Simple voters restore test.
	 * Restores and checks the voters list.
	 * @throws VoterDoesntExist
	 */	
	@Test
	public void restoreVotersTest() throws VoterDoesntExist{
		votersRestStub = backup.restoreVoters();
		for(int i=1;i<=100;i++){
			Assert.assertEquals(i, votersRestStub.findVoter(i).getId());
		}
	}
	
	/**
	 * Simple parties restore test.
	 * Restores and checks the parties list (symbol and votes)
	 * @throws PartyDoesNotExist
	 */	
	@Test
	public void restorePartiesTest() throws PartyDoesNotExist{
		partiesRestStub = backup.restoreParties();
		for(int i=1;i<=20;i++){
			Assert.assertEquals("p"+i, partiesRestStub.getPartyBySymbol("p"+i).getSymbol());
			Assert.assertEquals(i*10, partiesRestStub.getPartyBySymbol("p"+i).getVoteNumber());
		}
	}
	
	
	/**
	 * Simple unregistered voters restore test.
	 * Restores and checks the unregistered voters list.
	 * @throws VoterDoesntExist
	 */	
	@Test
	public void restoreUnregVotersTest() throws VoterDoesntExist{
		unregVotersRestStub = backup.restoreUnregisteredVoters();
		for(int i=1;i<=100;i++){
			Assert.assertEquals(i+100, unregVotersRestStub.findVoter(i+100).getId());
		}
	}
	
	
	
	/**
	 * Failure test- invalid voter.
	 * Searches for an inexistent voter in the voters list.
	 * Expects a thrown exception of type VoterDoesntExist.
	 * @throws VoterDoesntExist
	 */	
	@Test(expected = VoterDoesntExist.class)
	public void restoreVotersExceptionTest() throws VoterDoesntExist{
		votersRestStub = backup.restoreVoters();
		votersRestStub.findVoter(800);
	}
	
	/**
	 * Failure test- invalid party.
	 * Searches for an inexistent party in the parties list.
	 * Expects a thrown exception of type PartyDoesNotExist.
	 * @throws PartyDoesNotExist
	 */	
	@Test(expected = PartyDoesNotExist.class)
	public void restorePartiesExceptionTest() throws PartyDoesNotExist{
		partiesRestStub = backup.restoreParties();
		partiesRestStub.getPartyBySymbol("ERROR");
	}
	
	
	/**
	 * Failure test- invalid unregistered voter.
	 * Searches for an inexistent voter in the unregistered voters list.
	 * Expects a thrown exception of type VoterDoesntExist.
	 * @throws VoterDoesntExist
	 */	
	@Test(expected = VoterDoesntExist.class)
	public void restoreUnregVotersExceptionTest() throws VoterDoesntExist{
		unregVotersRestStub = backup.restoreUnregisteredVoters();
		unregVotersRestStub.findVoter(800);
	}
	
	
	
	/**
	 * Voters restore stress test.
	 * Restores and checks the voter's list 10 times.
	 * @throws VoterDoesntExist
	 */	
	@Test
	public void multipleRestoreVotersTest() throws VoterDoesntExist{
		backup.storeState(partiesStub, votersStub, unregVotersStub);
		for(int j=1; j<=10; j++){
			votersRestStub = backup.restoreVoters();
			for(int i=1;i<=100;i++){
				Assert.assertEquals(i, votersRestStub.findVoter(i).getId());
			}
		}
	}
	
	/**
	 * Parties restore stress test.
	 * Restores and checks the parties' list 10 times.
	 * @throws PartyDoesNotExist
	 */
	@Test
	public void multipleRestorePartiesTest() throws PartyDoesNotExist{
		backup.storeState(partiesStub, votersStub, unregVotersStub);
		for(int j=1; j<=10; j++){
			partiesRestStub = backup.restoreParties();
			for(int i=1;i<=20;i++){
				Assert.assertEquals("p"+i, partiesRestStub.getPartyBySymbol("p"+i).getSymbol());
				Assert.assertEquals(i*10, partiesRestStub.getPartyBySymbol("p"+i).getVoteNumber());
			}
		}
	}
	
	/**
	 * Unregistered voters restore stress test.
	 * Restores and checks the unregistered voter's list 10 times.
	 * @throws VoterDoesntExist
	 */
	@Test
	public void multipleRestoreUnregVotersTest() throws VoterDoesntExist{
		backup.storeState(partiesStub, votersStub, unregVotersStub);
		for(int j=1; j<=10; j++){
			unregVotersRestStub = backup.restoreUnregisteredVoters();
			for(int i=1;i<=100;i++){
				Assert.assertEquals(i+100, unregVotersRestStub.findVoter(i+100).getId());
			}
		}
	}
	
	
	
	/**
	 * Backup/restore stress test.
	 * Creates 5 separate instances of backup and restores the voters' list from each instance.
	 * @throws VoterDoesntExist
	 */	
	@Test
	public void multipleBackupRestoreVotersTest() throws VoterDoesntExist{
		Backup backups[] = new Backup[5];
		for(int j=0;j<5;j++){
			backups[j] = new Backup(new PartiesListFactory(new PartyFactory()), new PartyFactory(), 
					new VotersListFactory(), new VoterDataFactory(), "FileHandlerTest/par"+j+".xml", "FileHandlerTest/vote"+j+".xml", "FileHandlerTest/uvote"+j+".xml");
			backups[j].storeState(partiesStub, votersStub, unregVotersStub);
		}
		for(int j=0; j<5; j++){
			votersRestStub = backups[j].restoreVoters();
			for(int i=1;i<=100;i++){
				Assert.assertEquals(i, votersRestStub.findVoter(i).getId());
			}
		}
	}
	
	
	
	
	/**
	 * Backup/restore stress test.
	 * Creates 5 separate instances of backup and restores the parties' list from each instance. 
	 * @throws PartyDoesNotExist
	 */
	@Test
	public void multipleBackupRestorePartiesTest() throws PartyDoesNotExist{
		Backup backups[] = new Backup[5];
		for(int j=0;j<5;j++){
			backups[j] = new Backup(new PartiesListFactory(new PartyFactory()), new PartyFactory(), 
					new VotersListFactory(), new VoterDataFactory(), "FileHandlerTest/par"+j+".xml", "FileHandlerTest/vote"+j+".xml", "FileHandlerTest/uvote"+j+".xml");
			backups[j].storeState(partiesStub, votersStub, unregVotersStub);
		}
		for(int j=0; j<5; j++){
			partiesRestStub = backup.restoreParties();
			for(int i=1;i<=20;i++){
				Assert.assertEquals("p"+i, partiesRestStub.getPartyBySymbol("p"+i).getSymbol());
				Assert.assertEquals(i*10, partiesRestStub.getPartyBySymbol("p"+i).getVoteNumber());
			}
		}
	}
	
	
	/**
	 * Backup/restore stress test.
	 * Creates 5 separate instances of backup and restores the unregistered voters' list from each instance.
	 * @throws VoterDoesntExist
	 */
	@Test
	public void multipleBackupRestoreUnregVotersTest() throws VoterDoesntExist{
		Backup backups[] = new Backup[5];
		for(int j=0;j<5;j++){
			backups[j] = new Backup(new PartiesListFactory(new PartyFactory()), new PartyFactory(), 
					new VotersListFactory(), new VoterDataFactory(), "FileHandlerTest/par"+j+".xml", "FileHandlerTest/vote"+j+".xml", "FileHandlerTest/uvote"+j+".xml");
			backups[j].storeState(partiesStub, votersStub, unregVotersStub);
		}
		for(int j=0; j<5; j++){
			unregVotersRestStub = backup.restoreUnregisteredVoters();
			for(int i=1;i<=100;i++){
				Assert.assertEquals(i+100, unregVotersRestStub.findVoter(i+100).getId());
			}
		}
	}
	
	
	/**
	 * Backup/restore stress test.
	 * Creates 5 separate instances of backup and restores all lists from each instance.
	 * @throws VoterDoesntExist
	 */
	@Test
	public void multipleBackupRestoreAllVotersTest() throws VoterDoesntExist, PartyDoesNotExist{
		Backup backups[] = new Backup[5];
		for(int j=0;j<5;j++){
			backups[j] = new Backup(new PartiesListFactory(new PartyFactory()), new PartyFactory(), 
					new VotersListFactory(), new VoterDataFactory(), "FileHandlerTest/par"+j+".xml", "FileHandlerTest/vote"+j+".xml", "FileHandlerTest/uvote"+j+".xml");
			backups[j].storeState(partiesStub, votersStub, unregVotersStub);
		}
		for(int j=0; j<5; j++){
			votersRestStub = backups[j].restoreVoters();
			for(int i=1;i<=100;i++){
				Assert.assertEquals(i, votersRestStub.findVoter(i).getId());
			}
			partiesRestStub = backups[j].restoreParties();
			for(int i=1;i<=20;i++){
				Assert.assertEquals("p"+i, partiesRestStub.getPartyBySymbol("p"+i).getSymbol());
				Assert.assertEquals(i*10, partiesRestStub.getPartyBySymbol("p"+i).getVoteNumber());
			}
			unregVotersRestStub = backups[j].restoreUnregisteredVoters();
			for(int i=1;i<=100;i++){
				Assert.assertEquals(i+100, unregVotersRestStub.findVoter(i+100).getId());
			}
		}
	}

	
	
	/**
	 * Backup/restore consistency test.
	 * Saves and restores the voters' list 5 times to check corruption
	 * @throws VoterDoesntExist
	 */	
	@Test
	public void sequentialBackupRestoreVotersTest() throws VoterDoesntExist{
		for(int j=0; j<5; j++){
			backup.storeState(partiesStub, votersStub, unregVotersStub);
			backup.storeState(partiesStub, votersStub, unregVotersStub);
			votersRestStub = backup.restoreVoters();
			for(int i=1;i<=100;i++){
				Assert.assertEquals(i, votersRestStub.findVoter(i).getId());
			}
		}
	}
	
	
	

	/**
	 * Backup/restore consistency test.
	 * Saves and restores the parties' list 5 times to check corruption
	 * @throws PartyDoesNotExist
	 */
	@Test
	public void sequentialBackupRestorePartiesTest() throws PartyDoesNotExist{
		for(int j=0; j<5; j++){
			backup.storeState(partiesStub, votersStub, unregVotersStub);
			backup.storeState(partiesStub, votersStub, unregVotersStub);
			partiesRestStub = backup.restoreParties();
			for(int i=1;i<=20;i++){
				Assert.assertEquals("p"+i, partiesRestStub.getPartyBySymbol("p"+i).getSymbol());
				Assert.assertEquals(i*10, partiesRestStub.getPartyBySymbol("p"+i).getVoteNumber());
			}
		}
	}
	
	

	/**
	 * Backup/restore consistency test.
	 * Saves and restores the unregistered voters' list 5 times to check corruption
	 * @throws VoterDoesntExist
	 */
	@Test
	public void sequentialBackupRestoreUnregVotersTest() throws VoterDoesntExist{
		for(int j=0; j<5; j++){
			backup.storeState(partiesStub, votersStub, unregVotersStub);
			backup.storeState(partiesStub, votersStub, unregVotersStub);
			unregVotersRestStub = backup.restoreUnregisteredVoters();
			for(int i=1;i<=100;i++){
				Assert.assertEquals(i+100, unregVotersRestStub.findVoter(i+100).getId());
			}
		}
	}
	
	
	/**
	 * Backup/restore stress test.
	 * Saves and restores the all lists 5 times to check corruption
	 * @throws VoterDoesntExist
	 */
	@Test
	public void sequentialBackupRestoreAllVotersTest() throws VoterDoesntExist, PartyDoesNotExist{
		for(int j=0; j<5; j++){
			backup.storeState(partiesStub, votersStub, unregVotersStub);
			backup.storeState(partiesStub, votersStub, unregVotersStub);
			votersRestStub = backup.restoreVoters();
			for(int i=1;i<=100;i++){
				Assert.assertEquals(i, votersRestStub.findVoter(i).getId());
			}
			partiesRestStub = backup.restoreParties();
			for(int i=1;i<=20;i++){
				Assert.assertEquals("p"+i, partiesRestStub.getPartyBySymbol("p"+i).getSymbol());
				Assert.assertEquals(i*10, partiesRestStub.getPartyBySymbol("p"+i).getVoteNumber());
			}
			unregVotersRestStub = backup.restoreUnregisteredVoters();
			for(int i=1;i<=100;i++){
				Assert.assertEquals(i+100, unregVotersRestStub.findVoter(i+100).getId());
			}
		}
	}

	



}




