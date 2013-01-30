import java.util.ArrayList;

public class PartiesList extends ArrayList<Party> implements IPartiesList {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PartiesList(){}

	@Override
	public IParty getPartyBySymbol(String symbol) {
		if (symbol == ""){
			return new Party("White vote","",0);
		}
		for (Party party: this){
			if (party.getSymbol().equals(symbol)){
				return party;
			}
		}
		return null;
	}

	@Override
	public IPartiesList joinLists(IPartiesList partiesList) {
		PartiesList joinedList = new PartiesList();
		for (Party party1: this){
			for (Party party2: (PartiesList) partiesList){
				if (party1.getName().equals(party2.getName())){
					joinedList.add(new Party(party1.getName(),party1.getSymbol(),party1.getVoteNumber()+party2.getVoteNumber()));					
				}
			}
		}
	return joinedList;
	}
	
	
	public void peep(){
		System.out.println("=========================");
		System.out.println("Peep of PartiesList");
		System.out.println("=========================");
		for (Party party : this) {
			System.out.println("~~~~~~~~~~~~~~~~");
			System.out.println(party);
			System.out.println("~~~~~~~~~~~~~~~~");
		}
	}
}	