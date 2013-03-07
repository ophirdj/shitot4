package fileHandler.tests;

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


public class BackupUnitTest {
	private Backup backup;
	private IPartiesList partiesStub;
	private IVotersList votersStub;
	private IVotersList unregVotersStub;
	private IPartiesList partiesRestStub;
	private IVotersList votersRestStub;
	private IVotersList unregVotersRestStub;
	
	private String votersBackup = "votersBackup.xml";
	private String partiesBackup = "partiesBackup.xml";
	private String unregVotersBackup = "unregVotersBackup.xml";
	
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
	}
	
	@After
	public void pretest() throws PartyDoesNotExist, VoterDoesntExist{
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
	
	
	@Test
	public void saveTest(){
		backup.storeState(partiesStub, votersStub, unregVotersStub);
	}
	
	@Test
	public void restoreVotersTest() throws VoterDoesntExist{
		votersRestStub = backup.restoreVoters();
		for(int i=1;i<=100;i++){
			Assert.assertEquals(i, votersRestStub.findVoter(i).getId());
		}
	}
	
	
	@Test
	public void restorePartiesTest() throws PartyDoesNotExist{
		partiesRestStub = backup.restoreParties();
		for(int i=1;i<=20;i++){
			Assert.assertEquals("p"+i, partiesRestStub.getPartyBySymbol("p"+i).getSymbol());
			Assert.assertEquals(i*10, partiesRestStub.getPartyBySymbol("p"+i).getVoteNumber());
		}
	}
	
	
	
	
	@Test
	public void restoreUnregVotersTest() throws VoterDoesntExist{
		unregVotersRestStub = backup.restoreUnregisteredVoters();
		for(int i=1;i<=100;i++){
			Assert.assertEquals(i+100, unregVotersRestStub.findVoter(i+100).getId());
		}
	}
	
	
	
	
	
	@Test(expected = VoterDoesntExist.class)
	public void restoreVotersExceptionTest() throws VoterDoesntExist{
		votersRestStub = backup.restoreVoters();
		votersRestStub.findVoter(800);
	}
	
	
	
	@Test(expected = PartyDoesNotExist.class)
	public void restorePartiesExceptionTest() throws PartyDoesNotExist{
		partiesRestStub = backup.restoreParties();
		partiesRestStub.getPartyBySymbol("ERROR");
	}
	
	
	
	
	@Test(expected = VoterDoesntExist.class)
	public void restoreUnregVotersExceptionTest() throws VoterDoesntExist{
		unregVotersRestStub = backup.restoreUnregisteredVoters();
		unregVotersRestStub.findVoter(800);
	}
	
	
	
	
	
}
