package choosingList.tests;

import global.dictionaries.Messages;
import global.gui.StationPanel;

import org.junit.Test;

import partiesList.model.IPartiesList;
import partiesList.model.IParty;

import choosingList.factories.IChoosingWindowFactory;
import choosingList.logic.ChooseType;
import choosingList.logic.ChoosingList;
import choosingList.logic.IChoosingList;
import choosingList.tests.ChoosingListWindowStub.CloseWindowComponent;
import choosingList.tests.ChoosingListWindowStub.ConformationWithPartyComponent;
import choosingList.tests.ChoosingListWindowStub.PrintConformationMessageComponent;
import choosingList.tests.ChoosingListWindowStub.getChoiceComponent;
import choosingList.tests.ChoosingListWindowStub.getChoiceWaitComponent;
import choosingList.tests.ChoosingListWindowStub.getPartyComponent;
import choosingList.tests.ChoosingListWindowStub.switchOffComponent;
import choosingList.tests.ChoosingListWindowStub.switchOnComponent;
import choosingList.tests.PartiesListStub.sublistComponent;
import choosingList.tests.PartiesListStub.whiteNotePartyComponent;




public class ChoosingListUnitTest {
	
	private int max_parties = 9;
	
	private ChoosingList buildChoosingList(ChoosingListTestEnvironment testEnvironment, int partiesCount) {
		IChoosingWindowFactory choosingWindow = testEnvironment.getChoosingWindowFactory();
		IPartiesList parties = testEnvironment.getPartiesFactory(partiesCount).createInstance();
		return new ChoosingList(parties,new StationPanel(),choosingWindow);
	}
	
	@Test
	public void buildTest(){
		ChoosingListTestEnvironment TestEnviroment = new ChoosingListTestEnvironment("buildTest");

		TestEnviroment.addComponentForTest(new CloseWindowComponent()); 
		TestEnviroment.addRetireCall();
		
		IChoosingList tested = buildChoosingList(TestEnviroment,5);
		TestEnviroment.rutTest(tested);
	}
	
	@Test
	public void easyTest(){
		ChoosingListTestEnvironment TestEnviroment = new ChoosingListTestEnvironment("easyTest");
		IParty party1 = new PartyStub("party1");
		IParty party2 = new PartyStub("party2");
		int partiesAmount = 6;
		IPartiesList ret1 = new PartiesListNulled("list1");
		
		TestEnviroment.addChooseList(party1);
		
		TestEnviroment.addComponentForTest(new switchOnComponent());
		TestEnviroment.addComponentForTest(new sublistComponent(0,Math.min(max_parties,partiesAmount),ret1));
		TestEnviroment.addComponentForTest(new getChoiceComponent(ret1,ChooseType.Party));
		TestEnviroment.addComponentForTest(new getPartyComponent(party1));
		TestEnviroment.addComponentForTest(new whiteNotePartyComponent(party2));
		TestEnviroment.addComponentForTest(new ConformationWithPartyComponent(Messages.Are_you_sure_you_want_to_vote_for,party1,true)); 
		TestEnviroment.addComponentForTest(new switchOffComponent());
		TestEnviroment.addComponentForTest(new CloseWindowComponent());
		TestEnviroment.addRetireCall();
		
		IChoosingList tested = buildChoosingList(TestEnviroment,partiesAmount);
		TestEnviroment.rutTest(tested);
	}
	
	@Test
	public void whiteNoteTest(){
		ChoosingListTestEnvironment TestEnviroment = new ChoosingListTestEnvironment("whiteNoteTest");
		IParty party1 = new PartyStub("party1");
		int partiesAmount = 6;
		IPartiesList ret1 = new PartiesListNulled("list1");
		
		TestEnviroment.addChooseList(party1);
		
		TestEnviroment.addComponentForTest(new switchOnComponent());
		TestEnviroment.addComponentForTest(new sublistComponent(0,Math.min(max_parties,partiesAmount),ret1));
		TestEnviroment.addComponentForTest(new getChoiceComponent(ret1,ChooseType.Party));
		TestEnviroment.addComponentForTest(new getPartyComponent(party1));
		TestEnviroment.addComponentForTest(new whiteNotePartyComponent(party1));
		TestEnviroment.addComponentForTest(new PrintConformationMessageComponent(Messages.Are_you_sure_you_dont_want_to_vote_for_anyone,true)); 
		TestEnviroment.addComponentForTest(new switchOffComponent());
		TestEnviroment.addComponentForTest(new CloseWindowComponent());
		TestEnviroment.addRetireCall();
		
		IChoosingList tested = buildChoosingList(TestEnviroment,partiesAmount);
		TestEnviroment.rutTest(tested);
	}
	
	@Test
	public void oneNextTest(){
		ChoosingListTestEnvironment TestEnviroment = new ChoosingListTestEnvironment("oneNextTest");
		IParty party1 = new PartyStub("party1");
		int partiesAmount = 25;
		IPartiesList ret1 = new PartiesListNulled("list1");
		IPartiesList ret2 = new PartiesListNulled("list2");
		
		TestEnviroment.addChooseList(party1);
		
		TestEnviroment.addComponentForTest(new switchOnComponent());
		TestEnviroment.addComponentForTest(new sublistComponent(0,max_parties,ret1));
		TestEnviroment.addComponentForTest(new getChoiceComponent(ret1,ChooseType.Next));
		TestEnviroment.addComponentForTest(new sublistComponent(max_parties,2*max_parties,ret2));
		TestEnviroment.addComponentForTest(new getChoiceComponent(ret2,ChooseType.Party));
		TestEnviroment.addComponentForTest(new getPartyComponent(party1));
		TestEnviroment.addComponentForTest(new whiteNotePartyComponent(party1));
		TestEnviroment.addComponentForTest(new PrintConformationMessageComponent(Messages.Are_you_sure_you_dont_want_to_vote_for_anyone,true)); 
		TestEnviroment.addComponentForTest(new switchOffComponent());
		TestEnviroment.addComponentForTest(new CloseWindowComponent());
		TestEnviroment.addRetireCall();

		IChoosingList tested = buildChoosingList(TestEnviroment,partiesAmount);
		TestEnviroment.rutTest(tested);
	}

	@Test
	public void twoNextTest(){
		ChoosingListTestEnvironment TestEnviroment = new ChoosingListTestEnvironment("twoNextTest");
		IParty party1 = new PartyStub("party1");
		IParty party2 = new PartyStub("party2");
		int partiesAmount = 25;
		IPartiesList ret1 = new PartiesListNulled("list1");
		IPartiesList ret2 = new PartiesListNulled("list2");
		IPartiesList ret3 = new PartiesListNulled("list3");
		
		TestEnviroment.addChooseList(party1);
		
		TestEnviroment.addComponentForTest(new switchOnComponent());
		TestEnviroment.addComponentForTest(new sublistComponent(0,max_parties,ret1));
		TestEnviroment.addComponentForTest(new getChoiceComponent(ret1,ChooseType.Next));
		TestEnviroment.addComponentForTest(new sublistComponent(max_parties,2*max_parties,ret2));
		TestEnviroment.addComponentForTest(new getChoiceComponent(ret2,ChooseType.Next));
		TestEnviroment.addComponentForTest(new sublistComponent(2*max_parties,25,ret3));
		TestEnviroment.addComponentForTest(new getChoiceComponent(ret3,ChooseType.Party));
		TestEnviroment.addComponentForTest(new getPartyComponent(party1));
		TestEnviroment.addComponentForTest(new whiteNotePartyComponent(party2));
		TestEnviroment.addComponentForTest(new ConformationWithPartyComponent(Messages.Are_you_sure_you_want_to_vote_for,party1,true)); 
		TestEnviroment.addComponentForTest(new switchOffComponent());
		TestEnviroment.addComponentForTest(new CloseWindowComponent());
		TestEnviroment.addRetireCall();
		
		IChoosingList tested = buildChoosingList(TestEnviroment,partiesAmount);
		TestEnviroment.rutTest(tested);
	}
	
	@Test
	public void forthAndBack(){
		ChoosingListTestEnvironment TestEnviroment = new ChoosingListTestEnvironment("forthAndBack");
		IParty party1 = new PartyStub("party1");
		IParty party2 = new PartyStub("party2");
		int partiesAmount = 25;
		IPartiesList ret1 = new PartiesListNulled("list1");
		IPartiesList ret2 = new PartiesListNulled("list2");
		
		TestEnviroment.addChooseList(party1);
		
		TestEnviroment.addComponentForTest(new switchOnComponent());
		TestEnviroment.addComponentForTest(new sublistComponent(0,max_parties,ret1));
		TestEnviroment.addComponentForTest(new getChoiceComponent(ret1,ChooseType.Next));
		TestEnviroment.addComponentForTest(new sublistComponent(max_parties,2*max_parties,ret2));
		TestEnviroment.addComponentForTest(new getChoiceComponent(ret2,ChooseType.Prev));
		TestEnviroment.addComponentForTest(new sublistComponent(0,max_parties,ret1));
		TestEnviroment.addComponentForTest(new getChoiceComponent(ret1,ChooseType.Party));
		TestEnviroment.addComponentForTest(new getPartyComponent(party1));
		TestEnviroment.addComponentForTest(new whiteNotePartyComponent(party2));
		TestEnviroment.addComponentForTest(new ConformationWithPartyComponent(Messages.Are_you_sure_you_want_to_vote_for,party1,true)); 
		TestEnviroment.addComponentForTest(new switchOffComponent());
		TestEnviroment.addComponentForTest(new CloseWindowComponent());
		TestEnviroment.addRetireCall();
		
		IChoosingList tested = buildChoosingList(TestEnviroment,partiesAmount);
		TestEnviroment.rutTest(tested);
	}
	
	@Test
	public void backAndForth(){
		ChoosingListTestEnvironment TestEnviroment = new ChoosingListTestEnvironment("backAndForth");
		IParty party1 = new PartyStub("party1");
		IParty party2 = new PartyStub("party2");
		int partiesAmount = 25;
		IPartiesList ret1 = new PartiesListNulled("list1");
		IPartiesList ret2 = new PartiesListNulled("list2");
		
		TestEnviroment.addChooseList(party1);
		
		TestEnviroment.addComponentForTest(new switchOnComponent());
		TestEnviroment.addComponentForTest(new sublistComponent(0,max_parties,ret1));
		TestEnviroment.addComponentForTest(new getChoiceComponent(ret1,ChooseType.Prev));
		TestEnviroment.addComponentForTest(new sublistComponent(2*max_parties,partiesAmount,ret2));
		TestEnviroment.addComponentForTest(new getChoiceComponent(ret2,ChooseType.Next));
		TestEnviroment.addComponentForTest(new sublistComponent(0,max_parties,ret1));
		TestEnviroment.addComponentForTest(new getChoiceComponent(ret1,ChooseType.Party));
		TestEnviroment.addComponentForTest(new getPartyComponent(party1));
		TestEnviroment.addComponentForTest(new whiteNotePartyComponent(party2));
		TestEnviroment.addComponentForTest(new ConformationWithPartyComponent(Messages.Are_you_sure_you_want_to_vote_for,party1,true)); 
		TestEnviroment.addComponentForTest(new switchOffComponent());
		TestEnviroment.addComponentForTest(new CloseWindowComponent());
		TestEnviroment.addRetireCall();

		IChoosingList tested = buildChoosingList(TestEnviroment,partiesAmount);
		TestEnviroment.rutTest(tested);
	}
	
	@Test
	public void easyInterruptTest(){
		ChoosingListTestEnvironment TestEnviroment = new ChoosingListTestEnvironment("easyInterruptTest");
		int partiesAmount = 25;
		IPartiesList ret1 = new PartiesListNulled("list1");
		
		TestEnviroment.addWaitChooseList();
		
		TestEnviroment.addComponentForTest(new switchOnComponent());
		TestEnviroment.addComponentForTest(new sublistComponent(0,max_parties,ret1));
		getChoiceWaitComponent choice = new getChoiceWaitComponent(ret1);
		TestEnviroment.addComponentForTest(choice);
		TestEnviroment.addComponentForTest(new CloseWindowComponent(choice));
		
		
		IChoosingList tested = buildChoosingList(TestEnviroment,partiesAmount);
		TestEnviroment.rutTest(tested);
	}
	
}
