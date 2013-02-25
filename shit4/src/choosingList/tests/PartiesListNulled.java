package choosingList.tests;

import java.util.Iterator;

import partiesList.model.IPartiesList;
import partiesList.model.IParty;

public class PartiesListNulled implements IPartiesList{

	
	
	private String name;

	public PartiesListNulled(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "list " + name;
	}

	@Override
	public void addParty(IParty party) {
		throw new AssertionError();
	}

	@Override
	public IPartiesList copy() {
		throw new AssertionError();
	}

	@Override
	public IParty getPartyBySymbol(String symbol) throws PartyDoesNotExist {
		throw new AssertionError();
	}

	@Override
	public int getTotalVotes() {
		throw new AssertionError();
	}

	@Override
	public IParty getWhiteNoteParty() {
		throw new AssertionError();
	}

	@Override
	public IPartiesList joinLists(IPartiesList partiesList) {
		throw new AssertionError();
	}

	@Override
	public void peep() {
		throw new AssertionError();
	}

	@Override
	public int size() {
		throw new AssertionError();
	}

	@Override
	public IPartiesList sublist(int start, int end) {
		throw new AssertionError();
	}

	@Override
	public IPartiesList zeroCopy() {
		throw new AssertionError();
	}

	@Override
	public Iterator<IParty> iterator() {
		throw new AssertionError();
	}

}
