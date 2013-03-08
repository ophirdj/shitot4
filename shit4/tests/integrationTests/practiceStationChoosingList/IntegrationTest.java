package integrationTests.practiceStationChoosingList;

import global.dictionaries.Languages;

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
import practiceStation.logic.IPracticeStation;

public class IntegrationTest {

	private ChoosingListWindowStub choosingWindowStub;
	private PracticeWindowStub practiceWindowStub;
	private ImagePanelStub imagePanelStub;
	private IPracticeStationFactory practiceStationFactory;
	
	private long practiceTime = 500L;
	private long failTime = 2*practiceTime;
	
	/**
	 * Keep reference to the given choosing window stub.
	 * @param stub: The choosing window stub;
	 */
	public void setChoosingWindowStub(ChoosingListWindowStub stub) {
		this.choosingWindowStub = stub;
	}

	/**
	 * Keep reference to the given practice window stub.
	 * @param stub: The practice window stub;
	 */
	public void setPracticeWindowStub(PracticeWindowStub stub) {
		this.practiceWindowStub = stub;
	}
	
	/**
	 * Keep reference to the given guide stub.
	 * @param stub: The guide stub;
	 */
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
				imagePanelFactory, practiceTime);
	}

	/**
	 * Build a partiesList with amount parties.
	 * 
	 * @param amount: The amount of parties.
	 * @return IPartiesList with "amount" parties, symbols are "0" to "amount-1".
	 */
	private IPartiesList basicParties(int amount) {
		IPartyFactory partyFactory = new PartyFactory();
		IPartiesList parties = new PartiesList(partyFactory);
		for(Integer i = 0; i < amount; i++){
			parties.addParty(partyFactory.createInstance("t"+i, i.toString() ));
		}
		return parties;
	}
	
	/**
	 * Build some basic path of choosing
	 * 
	 * @param amountNext: Choose "next" this number of time.
	 * @param amountPrev: Choose "previous" this number of time.
	 * @param party: The party to return.
	 */
	private void choosePartyPath(int amountNext, int amountPrev, IParty party){
		choosingWindowStub.addType(ChooseType.Next, amountNext);
		choosingWindowStub.addType(ChooseType.Prev, amountPrev);
		choosingWindowStub.addType(ChooseType.Party, 1);
		practiceWindowStub.addConfirmationsResults(false, 1);
		choosingWindowStub.addParty(party);
		practiceWindowStub.addExpectedParties(party);
	}

	/**
	 * Check the basic practice path (no guide, first page, always confirm).
	 */
	@Test
	public void testMss() throws Exception{
		IPartiesList parties = basicParties(25);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		IParty party = parties.getPartyBySymbol("7");
		choosePartyPath(0, 0, party);
		practiceStation.practiceVote();
		imagePanelStub.assertGuideShowedNeededTime(0);
	}
	
	/**
	 * Check practice with one next page choice.
	 */
	@Test
	public void testNext() throws Exception{
		IPartiesList parties = basicParties(25);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		IParty party = parties.getPartyBySymbol("14");
		choosePartyPath(1, 0, party);
		practiceStation.practiceVote();
		imagePanelStub.assertGuideShowedNeededTime(0);
	}
	
	/**
	 * Check practice with looping on all the pages once.
	 */
	@Test
	public void testNextLoop() throws Exception{
		IPartiesList parties = basicParties(25);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		IParty party = parties.getPartyBySymbol("1");
		choosePartyPath(3, 0, party);
		practiceStation.practiceVote();
		imagePanelStub.assertGuideShowedNeededTime(0);
	}
	
	/**
	 * Check practice with one previous page choice.
	 */
	@Test
	public void testPrev() throws Exception{
		IPartiesList parties = basicParties(25);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		IParty party = parties.getPartyBySymbol("18");
		choosePartyPath(0, 1, party);
		practiceStation.practiceVote();
		imagePanelStub.assertGuideShowedNeededTime(0);
	}
	
	/**
	 * Check practice with some movement on the pages.
	 */
	@Test
	public void testMovement() throws Exception{
		IPartiesList parties = basicParties(25);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		IParty party = parties.getPartyBySymbol("24");
		choosePartyPath(4, 5, party);
		practiceStation.practiceVote();
		imagePanelStub.assertGuideShowedNeededTime(0);
	}
	
	/**
	 * Check the basic practice path (no guide, first page, always confirm)
	 * where white note was chosen.
	 */
	@Test
	public void testWhiteNote() throws Exception{
		IPartiesList parties = basicParties(25);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		IParty party = parties.getWhiteNoteParty();
		choosePartyPath(0, 0, party);
		practiceStation.practiceVote();
		imagePanelStub.assertGuideShowedNeededTime(0);
	}
	
	/**
	 * Check practice with one next page choice.
	 * White note was chosen.
	 */
	@Test
	public void testNextWhiteNote() throws Exception{
		IPartiesList parties = basicParties(25);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		IParty party = parties.getWhiteNoteParty();
		choosePartyPath(1, 0, party);
		practiceStation.practiceVote();
		imagePanelStub.assertGuideShowedNeededTime(0);
	}
	
	/**
	 * Check practice with one previous page choice.
	 * White note was chosen.
	 */
	@Test
	public void testPrevWhiteNote() throws Exception{
		IPartiesList parties = basicParties(25);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		IParty party = parties.getWhiteNoteParty();
		choosePartyPath(0, 1, party);
		practiceStation.practiceVote();
		imagePanelStub.assertGuideShowedNeededTime(0);
	}
	
	/**
	 * Check practice with some movement on the pages.
	 * where white note was chosen.
	 */
	@Test
	public void testMovementWhiteNote() throws Exception{
		IPartiesList parties = basicParties(25);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		IParty party = parties.getWhiteNoteParty();
		choosePartyPath(4, 5, party);
		practiceStation.practiceVote();
		imagePanelStub.assertGuideShowedNeededTime(0);
	}
	
	/**
	 * Assert that you can choose white note from any page.
	 */
	@Test
	public void testWhiteNoteEverywhere() throws Exception{
		int partiesAmount = 1000;
		IPartiesList parties = basicParties(partiesAmount);
		IParty party = parties.getWhiteNoteParty();
		for(int i = 0; i < partiesAmount; i++){
			IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
			choosePartyPath(i, 0, party);
			practiceStation.practiceVote();
		}
		imagePanelStub.assertGuideShowedNeededTime(0);
	}

	/**
	 * Assert that you can show the guide.
	 */
	@Test
	public void testShowGuide() throws Exception{
		int partiesAmount = 25;
		Languages language = Languages.Hebrew;
		IPartiesList parties = basicParties(partiesAmount);
		
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		choosingWindowStub.addType(ChooseType.Next, 2);
		choosingWindowStub.addType(ChooseType.Party, 1);
		
		imagePanelStub.setLanguage(language);
		practiceStation.setLanguage(language);
		
		IParty party = parties.getPartyBySymbol("20");
		choosingWindowStub.addParty(party);
		practiceWindowStub.addExpectedParties(party);
		
		practiceStation.practiceVote();
		imagePanelStub.assertGuideShowedNeededTime(1);
	}
	
	/**
	 * Assert that you can re-choose inside the choosing list.
	 */
	@Test
	public void testRechooseParty() throws Exception{
		int partiesAmount = 25;
		Languages language = Languages.English;
		IPartiesList parties = basicParties(partiesAmount);
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		
		practiceWindowStub.addConfirmationsResults(false, 1);
		choosingWindowStub.addType(ChooseType.Party, 2);
		choosingWindowStub.addConfirmationsResults(false, 1);
		
		imagePanelStub.setLanguage(language);
		practiceStation.setLanguage(language);
		
		IParty party1 = parties.getPartyBySymbol("1");
		IParty party2 = parties.getPartyBySymbol("2");
		choosingWindowStub.addParty(party1);
		choosingWindowStub.addParty(party2);
		practiceWindowStub.addExpectedParties(party2);
		
		practiceStation.practiceVote();
		imagePanelStub.assertGuideShowedNeededTime(0);
	}
	
	/**
	 * Assert that you can re-choose inside the choosing list.
	 * Also, check that the page is the same when doing so. 
	 */
	@Test
	public void testRechooseWithMovementParty() throws Exception{
		int partiesAmount = 25;
		Languages language = Languages.English;
		IPartiesList parties = basicParties(partiesAmount);
		
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		practiceWindowStub.addConfirmationsResults(false, 1);
		choosingWindowStub.addType(ChooseType.Next, 2);
		choosingWindowStub.addType(ChooseType.Party, 1);
		choosingWindowStub.addConfirmationsResults(false, 1);
		choosingWindowStub.addType(ChooseType.Prev, 1);
		choosingWindowStub.addType(ChooseType.Party, 1);
		
		imagePanelStub.setLanguage(language);
		practiceStation.setLanguage(language);
		
		IParty party1 = parties.getPartyBySymbol("20");
		IParty party2 = parties.getPartyBySymbol("12");
		choosingWindowStub.addParty(party1);
		choosingWindowStub.addParty(party2);
		practiceWindowStub.addExpectedParties(party2);
		
		practiceStation.practiceVote();
		imagePanelStub.assertGuideShowedNeededTime(0);
	}
	
	/**
	 * Assert that you can re-choose from the practice station.
	 * Also, test that in that case you start from the first page. 
	 */
	@Test
	public void testRechooseInPracticeParty() throws Exception{
		int partiesAmount = 25;
		IPartiesList parties = basicParties(partiesAmount);
		
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		practiceWindowStub.addConfirmationsResults(false, 2);
		choosingWindowStub.addType(ChooseType.Next, 2);
		choosingWindowStub.addType(ChooseType.Party, 1);
		
		IParty party1 = parties.getPartyBySymbol("20");
		choosingWindowStub.addParty(party1);
		practiceWindowStub.addExpectedParties(party1);
		
		choosingWindowStub.addType(ChooseType.Party, 1);
		IParty party2 = parties.getPartyBySymbol("3");
		choosingWindowStub.addParty(party2);
		practiceWindowStub.addExpectedParties(party2);
		
		practiceStation.practiceVote();
		imagePanelStub.assertGuideShowedNeededTime(0);
	}
	
	/**
	 * Assert That you can choose that you didn't understand.
	 * Assert that after not understanding, you can show the guide.
	 */
	@Test
	public void testDidntUnderstand() throws Exception{
		int partiesAmount = 37;
		IPartiesList parties = basicParties(partiesAmount);
		
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		practiceWindowStub.addConfirmationsResults(false, 1);
		choosingWindowStub.addType(ChooseType.Next, 2);
		choosingWindowStub.addType(ChooseType.Party, 1);
		
		practiceWindowStub.addConfirmationsResults(true, 1);
		IParty party1 = parties.getPartyBySymbol("20");
		choosingWindowStub.addParty(party1);
		practiceWindowStub.addExpectedParties(party1);
		
		practiceWindowStub.addConfirmationsResults(false, 1);
		practiceWindowStub.addConfirmationsResults(true, 1);
		
		choosingWindowStub.addType(ChooseType.Party, 1);
		IParty party2 = parties.getPartyBySymbol("3");
		choosingWindowStub.addParty(party2);
		practiceWindowStub.addExpectedParties(party2);
		
		practiceStation.practiceVote();
		imagePanelStub.assertGuideShowedNeededTime(1);
	}
	
	/**
	 * Assert that if choosing while the given time was over,
	 * the process terminate.
	 */
	@Test
	public void testChooseInterrupted() throws Exception{
		int partiesAmount = 37;
		IPartiesList parties = basicParties(partiesAmount);
		
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		practiceWindowStub.addConfirmationsResults(false, 1);
		practiceStation.practiceVote();
		imagePanelStub.assertGuideShowedNeededTime(0);
	}
	
	/**
	 * Assert that if seeing a guide while the given time was over,
	 * the process terminate.
	 */
	@Test
	public void testGuideInterrupted() throws Exception{
		int partiesAmount = 37;
		IPartiesList parties = basicParties(partiesAmount);
		
		IPracticeStation practiceStation = practiceStationFactory.createInstance(parties);
		practiceWindowStub.addConfirmationsResults(true, 1);
		imagePanelStub.setTimePassed(0);
		practiceStation.practiceVote();
		imagePanelStub.assertGuideShowedNeededTime(1);
	}
}
