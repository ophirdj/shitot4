package integrationTests.practiceStationChoosingList;

import org.junit.*;

import choosingList.factories.IChoosingListFactory;
import choosingList.factories.IChoosingWindowFactory;
import choosingList.logic.ChooseType;

import partiesList.factories.IPartyFactory;
import partiesList.factories.PartyFactory;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import partiesList.model.PartiesList;
import practiceStation.factories.IImagePanelFactory;
import practiceStation.factories.IPracticeStationFactory;
import practiceStation.factories.IPracticeStationWindowFactory;
import practiceStation.gui.PracticeStationWindow;
import practiceStation.logic.IPracticeStation;

public class IntegrationTest {

	private ChoosingListWindowStub choosingWindowStub;
	private PracticeWindowStub practiceWindowStub;
	private IPracticeStation testedPracticeStation;
	private ImagePanelStub imagePanelStub;
	private IPracticeStationFactory practiceStationFactory;
	
	private long practiceTime = 500L;
	private long failTime = 2*practiceTime;
	

	public void setChoosingWindowStub(ChoosingListWindowStub stub) {
		this.choosingWindowStub = stub;
	}

	public void setPracticeWindowStub(PracticeWindowStub stub) {
		this.practiceWindowStub = stub;
	}
	
	public void setImagePanelStub(ImagePanelStub stub) {
		this.imagePanelStub = stub;
	}
	
	@Before
	public void initSystem(){
		IPracticeStationWindowFactory practiceStationWindowFactory = new PracticeWindowStubFactory(this);
		IChoosingWindowFactory choosingWindowFactory = new ChoosingListWindowStubFactory(this,failTime);
		IChoosingListFactory choosingListFactory = new ChoosingListTestConfigurationFactory(choosingWindowFactory);
		IImagePanelFactory imagePanelFactory = new ImagePanelStubFactory(this,failTime); 
		practiceStationFactory = new PracticeStationTestConfigurationFactory(
				choosingListFactory, practiceStationWindowFactory,
				imagePanelFactory, failTime);
	}

	private IPartiesList basicParties(int amount) {
		IPartyFactory partyFactory = new PartyFactory();
		IPartiesList parties = new PartiesList(partyFactory);
		for(Integer i = 0; i < amount; i++){
			parties.addParty(partyFactory.createInstance("t"+i, i.toString() ));
		}
		return parties;
	}

	@Test
	public void testMss() throws Exception{
		IPartiesList parties = basicParties(25);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		choosingWindowStub.addType(ChooseType.Party, 1);
		IParty party = parties.getPartyBySymbol("7");
		choosingWindowStub.addParty(party);
		practiceWindowStub.AddExpectedParties(party);
		practiceStation.practiceVote();
	}
	
	@Test
	public void testNext() throws Exception{
		IPartiesList parties = basicParties(25);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		choosingWindowStub.addType(ChooseType.Next, 1);
		choosingWindowStub.addType(ChooseType.Party, 1);
		IParty party = parties.getPartyBySymbol("14");
		choosingWindowStub.addParty(party);
		practiceWindowStub.AddExpectedParties(party);
		practiceStation.practiceVote();
	}
	
	@Test
	public void testPrev() throws Exception{
		IPartiesList parties = basicParties(25);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		choosingWindowStub.addType(ChooseType.Prev, 1);
		choosingWindowStub.addType(ChooseType.Party, 1);
		IParty party = parties.getPartyBySymbol("18");
		choosingWindowStub.addParty(party);
		practiceWindowStub.AddExpectedParties(party);
		practiceStation.practiceVote();
	}
	
	@Test
	public void testMovement() throws Exception{
		IPartiesList parties = basicParties(25);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		choosingWindowStub.addType(ChooseType.Next, 4);
		choosingWindowStub.addType(ChooseType.Prev, 5);
		choosingWindowStub.addType(ChooseType.Party, 1);
		IParty party = parties.getPartyBySymbol("24");
		choosingWindowStub.addParty(party);
		practiceWindowStub.AddExpectedParties(party);
		practiceStation.practiceVote();
	}
	
	@Test
	public void testWhiteNote() throws Exception{
		IPartiesList parties = basicParties(25);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		choosingWindowStub.addType(ChooseType.Party, 1);
		IParty party = parties.getWhiteNoteParty();
		choosingWindowStub.addParty(party);
		practiceWindowStub.AddExpectedParties(party);
		practiceStation.practiceVote();
	}
	
	@Test
	public void testNextWhiteNote() throws Exception{
		IPartiesList parties = basicParties(25);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		choosingWindowStub.addType(ChooseType.Next, 1);
		choosingWindowStub.addType(ChooseType.Party, 1);
		IParty party = parties.getWhiteNoteParty();
		choosingWindowStub.addParty(party);
		practiceWindowStub.AddExpectedParties(party);
		practiceStation.practiceVote();
	}
	
	@Test
	public void testPrevWhiteNote() throws Exception{
		IPartiesList parties = basicParties(25);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		choosingWindowStub.addType(ChooseType.Prev, 1);
		choosingWindowStub.addType(ChooseType.Party, 1);
		IParty party = parties.getWhiteNoteParty();
		choosingWindowStub.addParty(party);
		practiceWindowStub.AddExpectedParties(party);
		practiceStation.practiceVote();
	}
	
	@Test
	public void testMovementWhiteNote() throws Exception{
		IPartiesList parties = basicParties(25);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		choosingWindowStub.addType(ChooseType.Next, 4);
		choosingWindowStub.addType(ChooseType.Prev, 5);
		choosingWindowStub.addType(ChooseType.Party, 1);
		IParty party = parties.getWhiteNoteParty();
		choosingWindowStub.addParty(party);
		practiceWindowStub.AddExpectedParties(party);
		practiceStation.practiceVote();
	}


}
