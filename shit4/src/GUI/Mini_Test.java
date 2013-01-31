package GUI;

import java.util.ArrayList;

import partiesList.IParty;
import partiesList.PartiesList;
import partiesList.Party;
import partiesList.IPartiesList;
import votingStation.Driver_Mainframe;
import votingStation.IVotingStation;
import votingStation.VotingStation;
import choosingList.ChoosingList;

public class Mini_Test {
	public static class Mini extends Thread{
		ChoosingList choose_list;
		public Mini(ChoosingList choose) {
			choose_list=choose;
		}
		
		@Override
		public void run() {
			choose_list.chooseList();
		}
	}
	
	public static void test1() {
		IPartiesList parties = new PartiesList();
		parties.addParty(new Party("Party1","a",0));
		parties.addParty(new Party("Party2","b",0));
		parties.addParty(new Party("Party3","c",0));
		parties.addParty(new Party("Party4","d",0));
		parties.addParty(new Party("Party5","e",0));
		parties.addParty(new Party("Party6","f",0));
		parties.addParty(new Party("Party7","g",0));
		parties.addParty(new Party("Party8","h",0));
		parties.addParty(new Party("Party9","i",0));
		parties.addParty(new Party("Party10","j",0));
		parties.addParty(new Party("Party11","k",0));
		parties.addParty(new Party("Party12","l",0));
		parties.addParty(new Party("Party13","m",0));
		
		ChoosingList choose1 = new ChoosingList(parties,null);
		
		PartiesList other_parties = new PartiesList();
		other_parties.addParty(new Party("Party14","a1",0));
		other_parties.addParty(new Party("Party15","b1",0));
		other_parties.addParty(new Party("Party16","c1",0));
		other_parties.addParty(new Party("Party17","d1",0));
		other_parties.addParty(new Party("Party18","e1",0));
		other_parties.addParty(new Party("Party19","f1",0));
		other_parties.addParty(new Party("Party20","g1",0));
		other_parties.addParty(new Party("Party21","h1",0));
		other_parties.addParty(new Party("Party22","i1",0));
		other_parties.addParty(new Party("Party23","j1",0));
		other_parties.addParty(new Party("Party24","k1",0));
		other_parties.addParty(new Party("Party25","l1",0));
		other_parties.addParty(new Party("Party26","m1",0));
		
		ChoosingList choose2 = new ChoosingList(other_parties,null);
		ChoosingList choose3 = new ChoosingList(other_parties,null);
		ChoosingList choose4 = new ChoosingList(parties,null);
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
	
	public static void test2(){
		PartiesList parties = new PartiesList();
		parties.addParty(new Party("Party1","a",0));
		parties.addParty(new Party("Party2","b",0));
		parties.addParty(new Party("Party3","c",0));
		parties.addParty(new Party("Party4","d",0));
		parties.addParty(new Party("Party5","e",0));
		parties.addParty(new Party("Party6","f",0));
		parties.addParty(new Party("Party7","g",0));
		parties.addParty(new Party("Party8","h",0));
		parties.addParty(new Party("Party9","i",0));
		parties.addParty(new Party("Party10","j",0));
		parties.addParty(new Party("Party11","k",0));
		parties.addParty(new Party("Party12","l",0));
		parties.addParty(new Party("Party13","m",0));
		VotingStation vote1 = new VotingStation(new ArrayList<String>(),"voting station 1");
		vote1.initialize(parties, new Driver_Mainframe());
		Global_Window.main_window.show_window();
	}
	
	public static void main(String[] args) {
		test2();
	}

}
