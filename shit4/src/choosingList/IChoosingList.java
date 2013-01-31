package choosingList;

import partiesList.IParty;

public interface IChoosingList {
public final static IParty NO_PARTY = null;
//public final static IParty WHITE_NOTE = new Party("white note", "", 0);
public IParty chooseList();
}
