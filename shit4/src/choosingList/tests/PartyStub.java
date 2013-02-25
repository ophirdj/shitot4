package choosingList.tests;

import partiesList.model.IParty;

public class PartyStub implements IParty{

	private String name;

	public PartyStub(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "party " + name;
	}
	
	@Override
	public IParty copy() {
		throw new AssertionError();
	}

	@Override
	public void decreaseVoteNumber() {
		throw new AssertionError();
	}

	@Override
	public String getName() {
		throw new AssertionError();
	}

	@Override
	public String getSymbol() {
		throw new AssertionError();
	}

	@Override
	public int getVoteNumber() {
		throw new AssertionError();
	}

	@Override
	public void increaseVoteNumber() {
		throw new AssertionError();
	}

}
