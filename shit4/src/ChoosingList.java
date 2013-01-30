import java.util.Iterator;

public class ChoosingList implements IChoosingList{
	private PartiesList parties;
	private IWindow window;
	public ChoosingList(IPartiesList parties,IWindow window){
		this.parties = (PartiesList) parties;
		this.window = window;
	};
	public IParty chooseList(){
		Iterator<Party> iterator = parties.iterator();
		IParty chosenParty = null;
		Boolean hasConfirmed = false;
		while (!hasConfirmed){
		Boolean wantToSeeNext = true;
		while(wantToSeeNext){
			int i = 0;
			while (iterator.hasNext() && i<10){
				window.printParty(iterator.next());
				i++;
			}
			window.printParty(new Party("White vote","",0));
			chosenParty = parties.getPartyBySymbol(window.receiveChoiceSymbol());
			window.eraseAll();
			wantToSeeNext = (chosenParty == null);
			}
			hasConfirmed = window.getConfirmation("Are you sure you want to vote for " + chosenParty.getName() + "?");
		}
		return chosenParty;
	}
}
