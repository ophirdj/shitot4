package partiesList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PartiesList implements IPartiesList {
	private List<IParty> parties;
	private IParty whiteNote;
	
	private final String whiteNoteName = "white note";
	
	public PartiesList(){
		parties = new ArrayList<IParty>();
		whiteNote = new Party(whiteNoteName, IParty.WHITE_VOTE_SYMBOL);
	}

	@Override
	public synchronized Iterator<IParty> iterator() {
		return parties.iterator();
	}

	@Override
	public synchronized IParty getPartyBySymbol(String symbol) throws PartyDoesNotExist{
		if(symbol == IParty.WHITE_VOTE_SYMBOL) return whiteNote;
		for(IParty party: parties){
			if(party.getSymbol() == symbol){
				return party;
			}
		}
		throw new PartyDoesNotExist();
	}

	@Override
	public synchronized IPartiesList joinLists(IPartiesList partiesList) {
		PartiesList joined = new PartiesList();
		joined.whiteNote = new Party(whiteNoteName, IParty.WHITE_VOTE_SYMBOL, partiesList.getWhiteNoteParty().getVoteNumber() + whiteNote.getVoteNumber());;
		for(IParty party: this){
			Party newParty;
			try {
				newParty = new Party(party.getName(), party.getSymbol(), party.getVoteNumber() + partiesList.getPartyBySymbol(party.getSymbol()).getVoteNumber());
			} catch (PartyDoesNotExist e) {
				// TODO won't happen according to Ziv
				newParty = new Party(party.getName(), party.getSymbol(), party.getVoteNumber());
			}
			joined.addParty(newParty);
		}
		return joined;
	}

	@Override
	public synchronized void peep() {
		System.out.println("=========================");
		System.out.println("Peep of Parties list");
		System.out.println("=========================");
		System.out.println(parties);
		System.out.println("white note party:");
		System.out.println(whiteNote);
	}

	@Override
	public synchronized void addParty(IParty party) {
		parties.add(party);
	}

	@Override
	public synchronized int getTotalVotes() {
		int total = whiteNote.getVoteNumber();
		for(IParty party: parties){
			total += party.getVoteNumber();
		}
		return total;
	}

	@Override
	public synchronized IParty getWhiteNoteParty() {
		return whiteNote;
	}

	@Override
	public synchronized IPartiesList sublist(int start, int end) {
		List<IParty> l = parties.subList(start, end);
		PartiesList ret = new PartiesList();
		for(IParty party: l){
			ret.addParty(party);
		}
		ret.whiteNote = whiteNote;
		return ret;
	}

	@Override
	public synchronized IPartiesList copy() {
		PartiesList copy = new PartiesList();
		copy.whiteNote = whiteNote.copy();
		for(IParty party: parties){
			copy.addParty(party.copy());
		}
		return copy;
	}

	@Override
	public int size() {
		return parties.size();
	}

	@Override
	public IPartiesList zeroCopy() {
		PartiesList pList = new PartiesList();
		for(IParty p: this){
			Party pClone = new Party(p.getName(), p.getSymbol(), 0);
			pList.addParty(pClone);
		}
		return pList;
	}


}	