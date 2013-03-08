package unitTests.practiceStation;

import partiesList.model.IParty;

public class PracticeTest_PartyStub implements IParty {

	private String name;

	public PracticeTest_PartyStub(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
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
