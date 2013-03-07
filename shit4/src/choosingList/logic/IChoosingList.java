package choosingList.logic;

import partiesList.model.IParty;

public interface IChoosingList {
public final static IParty NO_PARTY = null;
//public final static IParty WHITE_NOTE = new Party("white note", "", 0);

public static class ChoosingInterruptedException extends Exception{
	private static final long serialVersionUID = 1L;
	}

/**
 * Start the process of choosing a party.
 * @return the party that was chosen by the voter
 */
public IParty chooseList() throws ChoosingInterruptedException;

/**
 * Finish the choosing process and retire the choosing list.
 */
public void retire();

}
