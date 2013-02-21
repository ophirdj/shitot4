package tests;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import GUI.ImagePanel;
import GUI.Main_Window;
import GUI.View;
import mainframe.MainframeWindow;
import mainframe.Mainframe_stub;
import partiesList.PartiesList;
import partiesList.Party;
import partiesList.IPartiesList;
import practiceStation.IPracticeStation;
import practiceStation.ImagePanelFactory;
import practiceStation.PracticeStation;
import practiceStation.PracticeStationImagesEnglish;
import practiceStation.PracticeStationWindowFactory;
import votingStation.Driver_StationsContoller;
import votingStation.IVotingStation;
import votingStation.VotingStation;
import choosingList.ChoosingList;
import factories.ChoosingListFactory;
import factories.ChoosingWindowFactory;
import factories.PartyFactory;
import factories.VotingStationFactory;
import factories.VotingStationWindowFactory;

public class Mini_Test {
	public static class Mini extends Thread {
		ChoosingList choose_list;

		public Mini(ChoosingList choose) {
			choose_list = choose;
		}

		@Override
		public void run() {
			try {
				choose_list.chooseList();
			} catch (Exception e) {
				System.out.println("Mistake");
			}

		}
	}

	public static class ConstParties {
		static IPartiesList getParties() {
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

		static IPartiesList getOtherParties() {
			IPartiesList other_parties = new PartiesList(new PartyFactory());
			other_parties.addParty(new Party("Party14", "a1", 0));
			other_parties.addParty(new Party("Party15", "b1", 0));
			other_parties.addParty(new Party("Party16", "c1", 0));
			other_parties.addParty(new Party("Party17", "d1", 72));
			other_parties.addParty(new Party("Party18", "e1", 0));
			other_parties.addParty(new Party("Party19", "f1", 52));
			other_parties.addParty(new Party("Party20", "g1", 0));
			other_parties.addParty(new Party("Party21", "h1", 0));
			other_parties.addParty(new Party("Party22", "i1", 0));
			other_parties.addParty(new Party("Party23", "j1", 0));
			other_parties.addParty(new Party("Party24", "k1", 18));
			other_parties.addParty(new Party("Party25", "l1", 0));
			other_parties.addParty(new Party("Party26", "m1", 0));
			return other_parties;
		}

	}

	public static void test1() {
		Main_Window window =new Main_Window();
		IPartiesList parties = ConstParties.getParties();
		IPartiesList other_parties = ConstParties.getOtherParties();
		ChoosingList choose1 = new ChoosingList(parties, null,
				new ChoosingWindowFactory(window));

		ChoosingList choose2 = new ChoosingList(other_parties, null,
				new ChoosingWindowFactory(window));
		ChoosingList choose3 = new ChoosingList(other_parties, null,
				new ChoosingWindowFactory(window));
		ChoosingList choose4 = new ChoosingList(parties, null,
				new ChoosingWindowFactory(window));
		window.show_window();

		Mini t1 = new Mini(choose1);
		Mini t2 = new Mini(choose2);
		Mini t3 = new Mini(choose3);
		Mini t4 = new Mini(choose4);
		t1.start();
		t2.run();
		t3.run();
		t4.run();

	}

	public static void test2() {
		Main_Window window =new Main_Window();
		IPartiesList parties = ConstParties.getParties();
		IPartiesList other_parties = ConstParties.getOtherParties();
		ChoosingListFactory chooseFactory = new ChoosingListFactory(new ChoosingWindowFactory(window));
		VotingStationFactory fac = new VotingStationFactory(chooseFactory,new VotingStationWindowFactory(window));
		List<String> pass = new ArrayList<String>();
		pass.add("abc");
		IVotingStation vote1 = fac.createInstance(pass, "voting station 1");
		vote1.initialize(parties, new Driver_StationsContoller());
		IVotingStation vote2 = fac.createInstance(pass, "voting station 2");
		vote2.initialize(other_parties, new Driver_StationsContoller());
		window.show_window();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//vote1.retire();
		//vote2.retire();
	}

	public static void test3() {
		Main_Window window =new Main_Window();
		IPartiesList parties = ConstParties.getParties();
		IPartiesList other_parties = ConstParties.getOtherParties();

		MainframeWindow testedWindow = new MainframeWindow(new Mainframe_stub(),window);
		testedWindow.init();
	
		testedWindow.showHistogram(parties);
		testedWindow.showTable(parties);
		window.show_window();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testedWindow.showHistogram(other_parties);
		testedWindow.showTable(other_parties);
	}

	public static void test4() {
		Main_Window window =new Main_Window();
		IPartiesList parties = ConstParties.getParties();
		IPartiesList other_parties = ConstParties.getOtherParties();
		IPracticeStation station = new PracticeStation("practice station",
				parties, new ChoosingListFactory(new ChoosingWindowFactory(window)),
				new PracticeStationWindowFactory(window), new ImagePanelFactory(window));
		window.show_window();
	}

	public static void main(String[] args) {
		test4();
	}

}
