//not a real test
public class testPartiesList {
	
	public static void main(){
		
	}
	
	void test1(){
		PartiesList l = new PartiesList();
		Party party1 = new Party("cazzo", "1");
		Party party2 = new Party("merda", "2");
		Party party3 = new Party("gatto", "3");
		Party party4 = new Party("canno", "4");
		Party party5 = new Party("cocodrilo", "5");
		l.add(party1);
		l.add(party2);
		l.add(party3);
		l.add(party4);
		l.add(party5);
		l.peep();		
	}
}
