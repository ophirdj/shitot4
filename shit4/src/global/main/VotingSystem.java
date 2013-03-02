package global.main;

import mainframe.communication.IStationsControllerFactory;
import mainframe.communication.StationsControllerFactory;
import mainframe.factories.IMainframeFactory;
import mainframe.factories.IMainframeWindowFactory;
import mainframe.factories.MainframeFactory;
import mainframe.factories.MainframeWindowFactory;
import choosingList.factories.ChoosingListFactory;
import choosingList.factories.ChoosingWindowFactory;
import choosingList.factories.IChoosingListFactory;
import choosingList.factories.IChoosingWindowFactory;
import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import partiesList.factories.PartiesListFactory;
import partiesList.factories.PartyFactory;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import partiesList.model.PartiesList;
import partiesList.model.Party;
import practiceStation.factories.IImagePanelFactory;
import practiceStation.factories.IPracticeStationFactory;
import practiceStation.factories.IPracticeStationWindowFactory;
import practiceStation.factories.ImagePanelFactory;
import practiceStation.factories.PracticeStationFactory;
import practiceStation.factories.PracticeStationWindowFactory;
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
import votingStation.factories.VotingRecordFactory;
import votingStation.factories.VotingStationFactory;
import votingStation.factories.VotingStationWindowFactory;
import fileHandler.factories.BackupFactory;
import fileHandler.factories.IBackupFactory;
import fileHandler.factories.IReadSuppliedXMLFactory;
import fileHandler.factories.ReadSuppliedXMLFactory;
import fileHandler.logic.Backup;
import fileHandler.logic.IBackup;
import global.gui.Main_Window;

public class VotingSystem {
	
	private static IPartiesList getPracticeParties() {
			IPartiesList parties = new PartiesList(new PartyFactory());
			parties.addParty(new Party("Party1", "a", 0));
			parties.addParty(new Party("Party2", "b", 0));
			parties.addParty(new Party("Party3", "c", 23));
			parties.addParty(new Party("Party4", "d", 0));
			parties.addParty(new Party("Party5", "e", 24));
			parties.addParty(new Party("Party6", "f", 0));
			parties.addParty(new Party("Party7", "g", 100));
			parties.addParty(new Party("Party8", "h", 0));
			parties.addParty(new Party("Party9", "i", 1));
			parties.addParty(new Party("Party10", "j", 0));
			parties.addParty(new Party("Party11", "k", 0));
			parties.addParty(new Party("Party12", "l", 0));
			parties.addParty(new Party("Party13", "m", 0));
			return parties;
	}
	
	public static void main(String[] args) {
		Main_Window main_window = new Main_Window();
		IPartyFactory partyFactory = new PartyFactory();
		IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		IVotersListFactory votersListFactory = new VotersListFactory();
		IVoterDataFactory voterDataFactory = new VoterDataFactory();
		IMainframeWindowFactory mainframeWindowFactory = new MainframeWindowFactory(main_window);
		IReadSuppliedXMLFactory readSuppliedXMLFactory = new ReadSuppliedXMLFactory(partiesListFactory, partyFactory, votersListFactory, voterDataFactory);
		
		IChoosingWindowFactory choosingWindowFactory = new ChoosingWindowFactory(main_window);
		IChoosingListFactory choosingListFactory = new ChoosingListFactory(choosingWindowFactory);
		IVotingStationWindowFactory votingStationWindowFactory = new VotingStationWindowFactory(main_window);
		IVotingRecordFactory votingRecordFactory = new VotingRecordFactory();
		IVotingStationFactory votingStationFactory = new VotingStationFactory(choosingListFactory, votingStationWindowFactory, votingRecordFactory);
		IStationsControllerFactory stationsControllerFactory = new StationsControllerFactory(votingStationFactory);
		
		IBackupFactory backupFactory = new BackupFactory(partiesListFactory, partyFactory, votersListFactory, voterDataFactory);
		IMainframeFactory mainframeFactory = new MainframeFactory(backupFactory, mainframeWindowFactory, readSuppliedXMLFactory, stationsControllerFactory, voterDataFactory, votersListFactory);
		mainframeFactory.createInstance();
		
		IPracticeStationWindowFactory practiceStationWindowFactory = new PracticeStationWindowFactory(main_window);
		IImagePanelFactory imagePanelFactory = new ImagePanelFactory(main_window);
		IPracticeStationFactory practiceStationFactory = new PracticeStationFactory(choosingListFactory, practiceStationWindowFactory, imagePanelFactory);
		practiceStationFactory.createInstance(getPracticeParties());
		main_window.show_window();
	}
	
	
	public static void main1(String[] args) {
		IBackup backup = new Backup(new PartiesListFactory(new PartyFactory()), new PartyFactory(), new VotersListFactory(), new VoterDataFactory(), "test/voters.xml", "test/parties.xml", "test/unregistered.xml");
		
		IPartiesList readPartiesList = new PartiesList(new PartyFactory());
		IParty p1 = new Party("Vive la France", "alors on dance!");
		IParty p2 = new Party("יש עתיד", "יש");
		IParty p3 = new Party("אין עתיד", "fuck you");
		IParty p4 = new Party("Liberte", "oui");
		IParty p5 = new Party("egalite", "non");
		IParty p6 = new Party("fraternite", "23");
		IParty p7 = new Party("technion", "Ullman's metal dick");
		readPartiesList.addParty(p1);
		readPartiesList.addParty(p2);
		readPartiesList.addParty(p3);
		readPartiesList.addParty(p4);
		readPartiesList.addParty(p5);
		readPartiesList.addParty(p6);
		readPartiesList.addParty(p7);
		
		IVotersList readVotersList = new VotersList();
		IVoterData v1 = new VoterData(111);
		IVoterData v2 = new VoterData(222);
		IVoterData v3 = new VoterData(333);
		IVoterData v4 = new VoterData(444);
		IVoterData v5 = new VoterData(555);
		readVotersList.addVoter(v1);
		readVotersList.addVoter(v2);
		readVotersList.addVoter(v3);
		readVotersList.addVoter(v4);
		readVotersList.addVoter(v5);
		
		IVotersList readUnreg = new VotersList();
		readVotersList.addVoter(new VoterData(1));
		readVotersList.addVoter(new VoterData(2));
		readVotersList.addVoter(new VoterData(3));
		readVotersList.addVoter(new VoterData(4));
		readVotersList.addVoter(new VoterData(5));
		
		backup.storeState(readPartiesList, readVotersList, readUnreg);
		System.out.println("yay");
	}
}
