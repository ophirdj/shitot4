package partiesList.model;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import partiesList.factories.IPartyFactory;

/**
 * implementation of IPartiesList
 * @author Emil
 *
 */
public class PartiesList implements IPartiesList {
	/**
	 * list of all the parties
	 */
	private List<IParty> parties;
	/**
	 * the party that represents the white note
	 */
	private IParty whiteNote;
	
	private final String whiteNoteName = "white note";
	
	/**
	 * saves the factory of Party
	 */
	private IPartyFactory partyFactory;
	
	/**
	 * builds an empty parties list
	 * @param partyFactory factory of party
	 */
	public PartiesList(IPartyFactory partyFactory){
		parties = new ArrayList<IParty>();
		whiteNote = partyFactory.createInstance(whiteNoteName, IParty.WHITE_VOTE_SYMBOL);
		this.partyFactory = partyFactory;
	}

	@Override
	public synchronized Iterator<IParty> iterator() {
		return parties.iterator();
	}

	@Override
	public synchronized IParty getPartyBySymbol(String symbol) throws PartyDoesNotExist{
		if(symbol == IParty.WHITE_VOTE_SYMBOL) return whiteNote;
		
		for(IParty party: parties){
			if(party.getSymbol().equals(symbol)){
				return party;
			}
		}
		throw new PartyDoesNotExist();
	}

	@Override
	public synchronized IPartiesList joinLists(IPartiesList partiesList) {
		PartiesList joined = new PartiesList(partyFactory);
		joined.whiteNote = partyFactory.createInstance(whiteNoteName, IParty.WHITE_VOTE_SYMBOL, partiesList.getWhiteNoteParty().getVoteNumber() + whiteNote.getVoteNumber());;
		for(IParty party: this){
			IParty newParty;
			try {
				newParty = partyFactory.createInstance(party.getName(), party.getSymbol(), party.getVoteNumber() + partiesList.getPartyBySymbol(party.getSymbol()).getVoteNumber());
			} catch (PartyDoesNotExist e) {
				// won't happen according to Ziv
				newParty = partyFactory.createInstance(party.getName(), party.getSymbol(), party.getVoteNumber());
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
		PartiesList ret = new PartiesList(partyFactory);
		for(IParty party: l){
			ret.addParty(party);
		}
		ret.whiteNote = whiteNote;
		return ret;
	}

	@Override
	public synchronized IPartiesList copy() {
		PartiesList copy = new PartiesList(partyFactory);
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
		PartiesList pList = new PartiesList(partyFactory);
		for(IParty p: this){
			IParty pClone = partyFactory.createInstance(p.getName(), p.getSymbol(), 0);
			pList.addParty(pClone);
		}
		return pList;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null || obj.getClass() != PartiesList.class) return false;
		PartiesList parties = (PartiesList)obj;
		if(parties.size() != size()) return false;
		
		Iterator<IParty> thisPartyIterator = iterator();
		Iterator<IParty> objPartyIterator = parties.iterator();
		while(thisPartyIterator.hasNext() && objPartyIterator.hasNext()){
			IParty thisParty = thisPartyIterator.next();
			IParty objParty = objPartyIterator.next();
			if(!thisParty.equals(objParty)){
				return false;
			}
			if(thisParty.getVoteNumber() != objParty.getVoteNumber()){
				return false;
			}
		}
		return true;
	}
	
}	