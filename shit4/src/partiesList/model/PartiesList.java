package partiesList.model;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import partiesList.factories.IPartyFactory;


public class PartiesList implements IPartiesList {
	private List<IParty> parties;
	private IParty whiteNote;
	
	private final String whiteNoteName = "white note";
	private IPartyFactory partyFactory;
	
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
			if(party.getSymbol() == symbol){
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
				// TODO won't happen according to Ziv
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


}	