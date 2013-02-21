package main;

import partiesList.IPartiesList;
import partiesList.PartiesList;
import partiesList.Party;
import practiceStation.IImagePanelFactory;
import practiceStation.IPracticeStationFactory;
import practiceStation.ImagePanelFactory;
import practiceStation.PracticeStationFactory;
import practiceStation.PracticeStationWindowFactory;
import GUI.Main_Window;
import factories.BackupFactory;
import factories.ChoosingListFactory;
import factories.ChoosingWindowFactory;
import factories.IBackupFactory;
import factories.IChoosingListFactory;
import factories.IChoosingWindowFactory;
import factories.IMainframeFactory;
import factories.IMainframeWindowFactory;
import factories.IPartiesListFactory;
import factories.IPartyFactory;
import factories.IReadSuppliedXMLFactory;
import factories.IStationsControllerFactory;
import factories.IVoterDataFactory;
import factories.IVotersListFactory;
import factories.IVotingStationFactory;
import factories.IVotingStationWindowFactory;
import factories.MainframeFactory;
import factories.MainframeWindowFactory;
import factories.PartiesListFactory;
import factories.PartyFactory;
import factories.ReadSuppliedXMLFactory;
import factories.StationsControllerFactory;
import factories.VoterDataFactory;
import factories.VotersListFactory;
import factories.VotingStationFactory;
import factories.VotingStationWindowFactory;

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
		IVotingStationFactory votingStationFactory = new VotingStationFactory(choosingListFactory, votingStationWindowFactory);
		IStationsControllerFactory stationsControllerFactory = new StationsControllerFactory(votingStationFactory);
		
		IBackupFactory backupFactory = new BackupFactory(partiesListFactory, partyFactory, votersListFactory, voterDataFactory);
		IMainframeFactory mainframeFactory = new MainframeFactory(backupFactory, mainframeWindowFactory, readSuppliedXMLFactory, stationsControllerFactory, voterDataFactory, votersListFactory);
		mainframeFactory.createInstance();
		
		PracticeStationWindowFactory practiceStationWindowFactory = new PracticeStationWindowFactory(main_window);
		IImagePanelFactory imagePanelFactory = new ImagePanelFactory(main_window);
		IPracticeStationFactory practiceStationFactory = new PracticeStationFactory(choosingListFactory, practiceStationWindowFactory, imagePanelFactory);
		practiceStationFactory.createInstance("practice station", getPracticeParties());
		main_window.show_window();
	}
}
