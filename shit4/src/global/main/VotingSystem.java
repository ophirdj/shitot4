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
import votingStation.factories.IVotingStationFactory;
import votingStation.factories.IVotingStationWindowFactory;
import votingStation.factories.VotingStationFactory;
import votingStation.factories.VotingStationWindowFactory;
import fileHandler.factories.BackupFactory;
import fileHandler.factories.IBackupFactory;
import fileHandler.factories.IReadSuppliedXMLFactory;
import fileHandler.factories.ReadSuppliedXMLFactory;
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
	
	public static void main1(String[] args) {
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
		IVotingStationFactory votingStationFactory = new VotingStationFactory(choosingListFactory, votingStationWindowFactory);
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
}
