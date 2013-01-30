import java.util.ArrayList;


public class TestVoterDataAndList {
	
	/*public static void main(String[] args) {
		//testVoter3();
		testVotersXML();
		//testVotingRecords();
	}*/

	public static void testVoter1(){
		Party p = new Party("Atudaim's rights in the family", "ATD", 0);
		
		
		VoterData emil = new VoterData(1, p);
		VoterData ziv = new VoterData(2, p);
		VoterData yona = new VoterData(3, p);
		VoterData ophir = new VoterData(4, p);
		VoterData daniel = new VoterData(5, p);
		
		VotersList list1 = new VotersList();
		list1.addVoter(emil);	
		list1.addVoter(ziv);
		list1.peep();
		
		VotersList list2 = new VotersList();
		list2.addVoter(yona);
		list2.addVoter(ophir);
		list2.addVoter(daniel);
		list2.peep();
		
		list1.merge(list2);
		list1.peep();

	}
	
	public static void testVoter2(){
		Party p = new Party("AtudaLashilton", "AL", 0);
		
		
		VoterData emil = new VoterData(1, p);
		VoterData ziv = new VoterData(2, p);
		VoterData yona = new VoterData(3, p);
		VoterData ophir = new VoterData(4, p);
		VoterData daniel = new VoterData(5, p);
		
		VotersList list1 = new VotersList();
		list1.addVoter(emil);	
		list1.addVoter(ziv);
		list1.addVoter(yona);
		list1.peep();
		
		VotersList list2 = new VotersList();
		list2.addVoter(yona);
		list2.addVoter(ophir);
		list2.addVoter(daniel);
		list2.peep();
		
		list1.merge(list2);
		list1.peep();

	}
	
	public static void testVoter3(){
		Party p = new Party("AtudaLashilton", "AL", 0);
		
		
		VoterData emil = new VoterData(1, p);
		VoterData ziv = new VoterData(2, p);
		VoterData yona = new VoterData(3, p);
		VoterData ophir = new VoterData(4, p);
		VoterData daniel = new VoterData(5, p);
		
		VotersList list1 = new VotersList();
		list1.addVoter(emil);	
		list1.addVoter(ziv);
		list1.addVoter(yona);
		list1.peep();
		
		VotersList list2 = new VotersList();
		list2.addVoter(yona);
		list2.addVoter(ophir);
		list2.addVoter(daniel);
		list2.peep();
		
		list1.replaceWith(list2);
		list1.peep();

	}
	
	public static void testVotersXML(){
		ArrayList<Integer> res = ReadXMLFile.readXMLVotersList();
		
		System.out.println("======================");
		for (Integer id : res) {
			System.out.println(id);
		}
	}
	
	public static void testVotingRecords(){
		ArrayList<Party> res = ReadXMLFile.readXMLvotingRecords();
		
		System.out.println("======================");
		for (Party p : res) {
			System.out.println(p);
		}
	}
	
	
}
