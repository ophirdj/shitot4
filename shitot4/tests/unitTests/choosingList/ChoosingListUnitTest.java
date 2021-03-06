package unitTests.choosingList;

import global.dictionaries.Messages;
import global.gui.StationPanel;

import org.junit.After;
import org.junit.Test;

import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import unitTests.choosingList.ChoosingListWindowStub.CloseWindowComponent;
import unitTests.choosingList.ChoosingListWindowStub.ConfirmationWithPartyComponent;
import unitTests.choosingList.ChoosingListWindowStub.PrintConfirmationMessageComponent;
import unitTests.choosingList.ChoosingListWindowStub.getChoiceComponent;
import unitTests.choosingList.ChoosingListWindowStub.getChoiceWaitComponent;
import unitTests.choosingList.ChoosingListWindowStub.getPartyComponent;
import unitTests.choosingList.ChoosingListWindowStub.switchOffComponent;
import unitTests.choosingList.ChoosingListWindowStub.switchOnComponent;
import unitTests.choosingList.PartiesListStub.sublistComponent;
import unitTests.choosingList.PartiesListStub.whiteNotePartyComponent;

import choosingList.factories.IChoosingWindowFactory;
import choosingList.logic.ChooseType;
import choosingList.logic.ChoosingList;
import choosingList.logic.IChoosingList;




public class ChoosingListUnitTest {
	
	ChoosingListTestEnvironment testEnviroment;
	int partiesAmount;
	private int max_parties = 9;
	
	private ChoosingList buildChoosingList(ChoosingListTestEnvironment testEnvironment, int partiesCount) {
		IChoosingWindowFactory choosingWindow = testEnvironment.getChoosingWindowFactory();
		IPartiesList parties = testEnvironment.getPartiesFactory(partiesCount).createInstance();
		return new ChoosingList(parties,new StationPanel(),choosingWindow);
	}
	
	/**
	 * Run the test.
	 * @throws Exception
	 */
	@After
	public void runningTheTest() throws Exception{
		IChoosingList tested = buildChoosingList(testEnviroment,partiesAmount);
		testEnviroment.runTest(tested);
	}
	
	/**
	 * Assert that the build ended successfully.
	 */
	@Test
	public void buildTest(){
		testEnviroment = new ChoosingListTestEnvironment("buildTest");
		partiesAmount = 5;
		
		testEnviroment.addRetireCall();
		testEnviroment.addComponentForTest(new CloseWindowComponent()); 
	}
	
	/**
	 * Assert that the mss was done successfully.
	 */
	@Test
	public void easyTest(){
		testEnviroment = new ChoosingListTestEnvironment("easyTest");
		partiesAmount = 6;
		
		IParty party1 = new PartyStub("party1");
		IParty party2 = new PartyStub("party2");
		IPartiesList ret1 = new PartiesListNulled("list1");
		
		testEnviroment.addChooseList(party1);
		testEnviroment.addComponentForTest(new whiteNotePartyComponent(party2));
		testEnviroment.addComponentForTest(new switchOnComponent());
		testEnviroment.addComponentForTest(new sublistComponent(0,Math.min(max_parties,partiesAmount),ret1));
		testEnviroment.addComponentForTest(new getChoiceComponent(ret1,ChooseType.Party));
		testEnviroment.addComponentForTest(new getPartyComponent(party1));
		
		testEnviroment.addComponentForTest(new ConfirmationWithPartyComponent(Messages.Are_you_sure_you_want_to_vote_for,party1,true)); 
		testEnviroment.addComponentForTest(new switchOffComponent());
		testEnviroment.addRetireCall();
		testEnviroment.addComponentForTest(new CloseWindowComponent());
		
	}
	
	/**
	 * Assert that the mss was done successfully with white note.
	 */
	@Test
	public void whiteNoteTest(){
		testEnviroment = new ChoosingListTestEnvironment("whiteNoteTest");
		partiesAmount = 6;
		
		IParty party1 = new PartyStub("party1");
		IPartiesList ret1 = new PartiesListNulled("list1");
		
		testEnviroment.addChooseList(party1);
		testEnviroment.addComponentForTest(new whiteNotePartyComponent(party1));
		testEnviroment.addComponentForTest(new switchOnComponent());
		testEnviroment.addComponentForTest(new sublistComponent(0,Math.min(max_parties,partiesAmount),ret1));
		testEnviroment.addComponentForTest(new getChoiceComponent(ret1,ChooseType.Party));
		testEnviroment.addComponentForTest(new getPartyComponent(party1));
		
		testEnviroment.addComponentForTest(new PrintConfirmationMessageComponent(Messages.Are_you_sure_you_dont_want_to_vote_for_anyone,true)); 
		testEnviroment.addComponentForTest(new switchOffComponent());
		testEnviroment.addRetireCall();
		testEnviroment.addComponentForTest(new CloseWindowComponent());
	}
	
	/**
	 * Check choosing list done successfully with one next choice.
	 */
	@Test
	public void oneNextTest(){
		testEnviroment = new ChoosingListTestEnvironment("oneNextTest");
		//need to be at least 10
		partiesAmount = 10;
		int finalEnd = Math.min(2*max_parties, partiesAmount);
		
		IParty party1 = new PartyStub("party1");
		IPartiesList ret1 = new PartiesListNulled("list1");
		IPartiesList ret2 = new PartiesListNulled("list2");
		
		testEnviroment.addChooseList(party1);
		testEnviroment.addComponentForTest(new whiteNotePartyComponent(party1));
		testEnviroment.addComponentForTest(new switchOnComponent());
		testEnviroment.addComponentForTest(new sublistComponent(0,max_parties,ret1));
		testEnviroment.addComponentForTest(new getChoiceComponent(ret1,ChooseType.Next));
		testEnviroment.addComponentForTest(new sublistComponent(max_parties,finalEnd,ret2));
		testEnviroment.addComponentForTest(new getChoiceComponent(ret2,ChooseType.Party));
		testEnviroment.addComponentForTest(new getPartyComponent(party1));
		
		testEnviroment.addComponentForTest(new PrintConfirmationMessageComponent(Messages.Are_you_sure_you_dont_want_to_vote_for_anyone,true)); 
		testEnviroment.addComponentForTest(new switchOffComponent());
		testEnviroment.addRetireCall();
		testEnviroment.addComponentForTest(new CloseWindowComponent());
	}

	/**
	 * Check choosing list done successfully with two next choice.
	 */
	@Test
	public void twoNextTest(){
		testEnviroment = new ChoosingListTestEnvironment("twoNextTest");
		//need to be at least 19
		partiesAmount = 25;
		
		IParty party1 = new PartyStub("party1");
		IParty party2 = new PartyStub("party2");
		
		IPartiesList ret1 = new PartiesListNulled("list1");
		IPartiesList ret2 = new PartiesListNulled("list2");
		IPartiesList ret3 = new PartiesListNulled("list3");
		
		int finalEnd = Math.min(3*max_parties, partiesAmount);
		
		testEnviroment.addChooseList(party1);
		testEnviroment.addComponentForTest(new whiteNotePartyComponent(party2));
		testEnviroment.addComponentForTest(new switchOnComponent());
		testEnviroment.addComponentForTest(new sublistComponent(0,max_parties,ret1));
		testEnviroment.addComponentForTest(new getChoiceComponent(ret1,ChooseType.Next));
		testEnviroment.addComponentForTest(new sublistComponent(max_parties,2*max_parties,ret2));
		testEnviroment.addComponentForTest(new getChoiceComponent(ret2,ChooseType.Next));
		testEnviroment.addComponentForTest(new sublistComponent(2*max_parties,finalEnd,ret3));
		testEnviroment.addComponentForTest(new getChoiceComponent(ret3,ChooseType.Party));
		testEnviroment.addComponentForTest(new getPartyComponent(party1));
		
		testEnviroment.addComponentForTest(new ConfirmationWithPartyComponent(Messages.Are_you_sure_you_want_to_vote_for,party1,true)); 
		testEnviroment.addComponentForTest(new switchOffComponent());
		testEnviroment.addRetireCall();
		testEnviroment.addComponentForTest(new CloseWindowComponent());
	}
	
	/**
	 * Check choosing list done successfully with one next and one prev.
	 */
	@Test
	public void forthAndBack(){
		testEnviroment = new ChoosingListTestEnvironment("forthAndBack");
		//need to be at least 18
		partiesAmount = 25;
		
		IParty party1 = new PartyStub("party1");
		IParty party2 = new PartyStub("party2");
		
		IPartiesList ret1 = new PartiesListNulled("list1");
		IPartiesList ret2 = new PartiesListNulled("list2");
		
		testEnviroment.addChooseList(party1);
		testEnviroment.addComponentForTest(new whiteNotePartyComponent(party2));
		testEnviroment.addComponentForTest(new switchOnComponent());
		testEnviroment.addComponentForTest(new sublistComponent(0,max_parties,ret1));
		testEnviroment.addComponentForTest(new getChoiceComponent(ret1,ChooseType.Next));
		testEnviroment.addComponentForTest(new sublistComponent(max_parties,2*max_parties,ret2));
		testEnviroment.addComponentForTest(new getChoiceComponent(ret2,ChooseType.Prev));
		testEnviroment.addComponentForTest(new sublistComponent(0,max_parties,ret1));
		testEnviroment.addComponentForTest(new getChoiceComponent(ret1,ChooseType.Party));
		testEnviroment.addComponentForTest(new getPartyComponent(party1));
		
		testEnviroment.addComponentForTest(new ConfirmationWithPartyComponent(Messages.Are_you_sure_you_want_to_vote_for,party1,true)); 
		testEnviroment.addComponentForTest(new switchOffComponent());
		testEnviroment.addRetireCall();
		testEnviroment.addComponentForTest(new CloseWindowComponent());
	}
	
	/**
	 * Check choosing list done successfully with one prev and one next.
	 */
	@Test
	public void backAndForth(){
		testEnviroment = new ChoosingListTestEnvironment("backAndForth");
		//need to be at least 9
		partiesAmount = 9;
		
		IParty party1 = new PartyStub("party1");
		IParty party2 = new PartyStub("party2"); 
		IPartiesList ret1 = new PartiesListNulled("list1");
		IPartiesList ret2 = new PartiesListNulled("list2");
		
		int finalPageStart = partiesAmount - (partiesAmount % max_parties);
		
		testEnviroment.addChooseList(party1);
		testEnviroment.addComponentForTest(new whiteNotePartyComponent(party2));
		testEnviroment.addComponentForTest(new switchOnComponent());
		testEnviroment.addComponentForTest(new sublistComponent(0,max_parties,ret1));
		testEnviroment.addComponentForTest(new getChoiceComponent(ret1,ChooseType.Prev));
		testEnviroment.addComponentForTest(new sublistComponent(finalPageStart,partiesAmount,ret2));
		testEnviroment.addComponentForTest(new getChoiceComponent(ret2,ChooseType.Next));
		testEnviroment.addComponentForTest(new sublistComponent(0,max_parties,ret1));
		testEnviroment.addComponentForTest(new getChoiceComponent(ret1,ChooseType.Party));
		testEnviroment.addComponentForTest(new getPartyComponent(party1));
		
		testEnviroment.addComponentForTest(new ConfirmationWithPartyComponent(Messages.Are_you_sure_you_want_to_vote_for,party1,true)); 
		testEnviroment.addComponentForTest(new switchOffComponent());
		testEnviroment.addRetireCall();
		testEnviroment.addComponentForTest(new CloseWindowComponent());
	}
	
	/**
	 * Check choosing list interrupted when choice take to long.
	 */
	@Test
	public void easyInterruptTest(){
		testEnviroment = new ChoosingListTestEnvironment("easyInterruptTest");
		partiesAmount = 25;
		
		IPartiesList ret1 = new PartiesListNulled("list1");
		IParty party1 = new PartyStub("party1");
		
		testEnviroment.addWaitChooseList();
		testEnviroment.addComponentForTest(new whiteNotePartyComponent(party1));
		testEnviroment.addComponentForTest(new switchOnComponent());
		testEnviroment.addComponentForTest(new sublistComponent(0,max_parties,ret1));
		getChoiceWaitComponent choice = new getChoiceWaitComponent(ret1);
		testEnviroment.addComponentForTest(choice);
		testEnviroment.neededRetireCall();
		testEnviroment.addComponentForTest(new CloseWindowComponent(choice));
	}
	
	/**
	 * Assert that the voter can undo is choosing and choose different party.
	 */
	@Test
	public void reChooseTest(){
		testEnviroment = new ChoosingListTestEnvironment("reChooseTest");
		partiesAmount = 25;
		
		IParty party1 = new PartyStub("party1");
		IParty party2 = new PartyStub("party2");
		
		IPartiesList ret1 = new PartiesListNulled("list1");
		IPartiesList ret2 = new PartiesListNulled("list2");
		IPartiesList ret3 = new PartiesListNulled("list3");
		
		testEnviroment.addChooseList(party2);
		testEnviroment.addComponentForTest(new whiteNotePartyComponent(party1));
		testEnviroment.addComponentForTest(new switchOnComponent());
		testEnviroment.addComponentForTest(new sublistComponent(0,max_parties,ret1));
		testEnviroment.addComponentForTest(new getChoiceComponent(ret1,ChooseType.Next));
		testEnviroment.addComponentForTest(new sublistComponent(max_parties,2*max_parties,ret2));
		testEnviroment.addComponentForTest(new getChoiceComponent(ret2,ChooseType.Party));
		testEnviroment.addComponentForTest(new getPartyComponent(party1));
		
		testEnviroment.addComponentForTest(new PrintConfirmationMessageComponent(Messages.Are_you_sure_you_dont_want_to_vote_for_anyone,false));
		testEnviroment.addComponentForTest(new sublistComponent(max_parties,2*max_parties,ret3));
		testEnviroment.addComponentForTest(new getChoiceComponent(ret3,ChooseType.Party));
		testEnviroment.addComponentForTest(new getPartyComponent(party2));

		testEnviroment.addComponentForTest(new ConfirmationWithPartyComponent(Messages.Are_you_sure_you_want_to_vote_for,party2,true));
		testEnviroment.addComponentForTest(new switchOffComponent());
		testEnviroment.addRetireCall(); 
		testEnviroment.addComponentForTest(new CloseWindowComponent());
	}
	
}
