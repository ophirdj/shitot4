package tests;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import GUI.Global_Window;
import GUI.ImagePanel;
import GUI.View;
import mainframe.MainframeWindow;
import mainframe.Mainframe_stub;
import partiesList.PartiesList;
import partiesList.Party;
import partiesList.IPartiesList;
import practiceStation.IPracticeStation;
import practiceStation.ImagePanelFactory;
import practiceStation.PracticeStation;
import practiceStation.PracticeStationImages;
import practiceStation.PracticeStationWindowFactory;
import votingStation.Driver_StationsContoller;
import votingStation.IVotingStation;
import votingStation.VotingStation;
import choosingList.ChoosingList;
import factories.ChoosingListFactory;
import factories.ChoosingWindowFactory;
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
			IPartiesList parties = new PartiesList();
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
			IPartiesList other_parties = new PartiesList();
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

		IPartiesList parties = ConstParties.getParties();
		IPartiesList other_parties = ConstParties.getOtherParties();
		ChoosingList choose1 = new ChoosingList(parties, null,
				new ChoosingWindowFactory());

		ChoosingList choose2 = new ChoosingList(other_parties, null,
				new ChoosingWindowFactory());
		ChoosingList choose3 = new ChoosingList(other_parties, null,
				new ChoosingWindowFactory());
		ChoosingList choose4 = new ChoosingList(parties, null,
				new ChoosingWindowFactory());
		Global_Window.main_window.show_window();

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
		IPartiesList parties = ConstParties.getParties();
		IPartiesList other_parties = ConstParties.getOtherParties();

		VotingStationFactory fac = new VotingStationFactory();
		List<String> pass = new ArrayList<String>();
		pass.add("abc");
		IVotingStation vote1 = fac.createInstance(pass, "voting station 1",
				new ChoosingListFactory(), new ChoosingWindowFactory(),
				new VotingStationWindowFactory());
		vote1.initialize(parties, new Driver_StationsContoller());
		IVotingStation vote2 = new VotingStation(new ArrayList<String>(),
				"voting station 2", new ChoosingListFactory(),
				new ChoosingWindowFactory(), new VotingStationWindowFactory());
		vote2.initialize(other_parties, new Driver_StationsContoller());
		Global_Window.main_window.show_window();
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
		IPartiesList parties = ConstParties.getParties();
		IPartiesList other_parties = ConstParties.getOtherParties();

		MainframeWindow testedWindow = new MainframeWindow(new Mainframe_stub());
		testedWindow.init();
		Global_Window.main_window.show_window();
		testedWindow.showHistogram(parties);
		testedWindow.showTable(parties);

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
		IPartiesList parties = ConstParties.getParties();
		IPartiesList other_parties = ConstParties.getOtherParties();
		IPracticeStation station = new PracticeStation("practice station",
				parties, new ChoosingListFactory(),
				new ChoosingWindowFactory(),
				new PracticeStationWindowFactory(), new ImagePanelFactory());
	}

	public static void main(String[] args) {
		test3();
	}

}
