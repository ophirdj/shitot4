package integrationTests.mainframeVotingStations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mainframe.communication.IStationsControllerFactory;
import mainframe.factories.IMainframeFactory;
import mainframe.factories.IMainframeWindowFactory;
import mainframe.logic.IMainframe;

import org.junit.*;

import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import partiesList.factories.PartiesListFactory;
import partiesList.factories.PartyFactory;
import partiesList.model.IPartiesList;
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
import votersList.model.VotersList;
import votingStation.factories.IVotingRecordFactory;
import votingStation.factories.IVotingStationFactory;
import votingStation.factories.IVotingStationWindowFactory;
import votingStation.factories.VotingStationFactory;

import integrationTests.*;


public class IntegrationTest{
	
	
	private List<String> passwords;
	private List<VotingStationWindowStub> votingWindowStubs;
	private List<ChoosingListStub> choosingListStubs;
	private MainframeWindowStub mainframeWindowStub;
	private ChoosingListStubFactory choosingListStubFactory;
	private final long maxVotingTimeSeconds = 1L;
	private final int numVotingStations = 50;
	private final int backupTimeIntervalSeconds = 2;
	private IMainframe mainframe;
	private BackupStubFactory backupStubFactory;
	private ReadSuppliedXMLStubFactory readSuppliedXMLStubFactory;

	@Before
	public void initSystem() {
		
		passwords = new ArrayList<String>();
		passwords.add("password1");
		passwords.add("password2");
		passwords.add("password3");
		passwords.add("password4");
		
		votingWindowStubs = new ArrayList<VotingStationWindowStub>();
		choosingListStubFactory = new ChoosingListStubFactory(this);
		mainframeWindowStub = null;
		
		
		IPartyFactory partyFactory = new PartyFactory();
		IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		IVotersListFactory votersListFactory = new VotersListFactory();
		IVoterDataFactory voterDataFactory = new VoterDataFactory();
		IMainframeWindowFactory mainframeWindowFactory = new MainframeWindowStubFactory(this);
		readSuppliedXMLStubFactory = new ReadSuppliedXMLStubFactory();
		
		IVotingStationWindowFactory votingStationWindowFactory = new VotingStationWindowStubFactory(this);
		IVotingRecordFactory votingRecordFactory = new VotingRecordTestConfigurationFactory(maxVotingTimeSeconds );
		IVotingStationFactory votingStationFactory = new VotingStationFactory(choosingListStubFactory, votingStationWindowFactory, votingRecordFactory);
		IStationsControllerFactory stationsControllerFactory = new StationsControllerTestConfigurationFactory(votingStationFactory, passwords, numVotingStations );
		
		backupStubFactory = new BackupStubFactory();
		IMainframeFactory mainframeFactory = new MainframeTestConfigurationFactory(backupTimeIntervalSeconds , backupStubFactory, mainframeWindowFactory, readSuppliedXMLStubFactory, stationsControllerFactory, voterDataFactory, votersListFactory);
		mainframe = mainframeFactory.createInstance();
		
	}

	public void addChoosingListStub(ChoosingListStub choosingListStub) {
		this.choosingListStubs.add(choosingListStub);
	}
	
	
}