package integrationTests.mainframeBackup;

import org.junit.*;
import integrationTests.mainframeBackup.BackupFactoryInt;
import fileHandler.factories.IBackupFactory;
import fileHandler.factories.IReadSuppliedXMLFactory;
import fileHandler.logic.Backup;
import fileHandler.logic.IBackup;
import mainframe.logic.IMainframe;
import mainframe.logic.IMainframe.IdentificationError;
import mainframe.logic.Mainframe;
import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import partiesList.factories.PartiesListFactory;
import partiesList.factories.PartyFactory;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import unitTests.mainframe.MainframeWindowStubFactory;
import unitTests.mainframe.StationsControllerStubFactory;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import votersList.factories.VoterDataFactory;
import votersList.factories.VotersListFactory;
import votersList.model.IVoterData;
import votersList.model.IVotersList;

public class IntegrationTest {
	/**
	 * time for hot backup test
	 */
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
			IParty party = partyFactory.createInstance("p"+i, "p"+i, i*123);
			readPartiesList.addParty(party);
		}
		IBackup tempBackup = new Backup(partiesListFactory, partyFactory, votersListFactory, voterDataFactory,
										"IntegrationTests/mainframeBackup/voters.xml", 
										"IntegrationTests/mainframeBackup/votingRecords.xml",
										"IntegrationTests/mainframeBackup/uvoters.xml");
		IVotersList temp = votersListFactory.createInstance();
		tempBackup.storeState(readPartiesList, readVotersList, temp);
		mainframe.initialize();

	}
	
	@Test
	public void testRestore() throws IdentificationError, InterruptedException{
		for(int i=101;i<=200;i++){
			mainframe.identification(i);
		}
		Thread.sleep(2*backupTimeIntervalSeconds*1000);
		mainframe.restore();
		
	}
}




