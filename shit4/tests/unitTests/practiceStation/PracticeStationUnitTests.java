package unitTests.practiceStation;

import java.util.ArrayList;
import java.util.Collection;

import global.dictionaries.Languages;
import global.dictionaries.Messages;

import org.junit.Test;

import choosingList.factories.IChoosingListFactory;

import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import partiesList.factories.PartiesListFactory;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import practiceStation.factories.IImagePanelFactory;
import practiceStation.factories.IPracticeStationWindowFactory;
import practiceStation.logic.PracticeStation;
import unitTests.practiceStation.ChoosingListStub.ChooseListComponent;
import unitTests.practiceStation.ChoosingListStub.ChoosingListRetireComponent;
import unitTests.practiceStation.ImagePanelStub.ImagePanelRetireComponent;
import unitTests.practiceStation.PracticeStationWindowStub.ConfirmationWithPartyComponent;
import unitTests.practiceStation.PracticeStationWindowStub.ConfirmationWithPartyLongComponent;
import unitTests.practiceStation.PracticeStationWindowStub.PrintConfirmationMessageComponent;
import unitTests.practiceStation.PracticeStationWindowStub.PrintConfirmationMessageLongComponent;
import unitTests.practiceStation.PracticeStationWindowStub.PrintInfoMessageComponent;
import unitTests.practiceStation.PracticeStationWindowStub.PrintInfoMessageLongComponent;

public class PracticeStationUnitTests {
	
	PracticeTestPaths pathes = new PracticeTestPaths();
	
	/**
	 * Build the practice station with default time for practice.
	 * 
	 * @param testEnviroment: The test environment.
	 * @param partiesList: The parties list.
	 * @return The station.
	 */
	public PracticeStation buildStation(TestEnvironment testEnviroment, IPartiesList partiesList){
		IImagePanelFactory ImagePanelFactory = testEnviroment.getImagePanelFactory();
		IPracticeStationWindowFactory practiceWindowFactory = testEnviroment.getPracticeWindowFactory();
		IChoosingListFactory choosingListFactory = testEnviroment.getChoosingListFactory();
		return new PracticeStation(partiesList,choosingListFactory,practiceWindowFactory,ImagePanelFactory);
	}
	
	/**
	 * Build the practice station with given time for practice.
	 * 
	 * @param testEnviroment: The test environment.
	 * @param partiesList: The parties list.
	 * @param waitTime: The maximal time for practice.
	 * @return The station.
	 */
	public PracticeStation buildStation(TestEnvironment testEnviroment, IPartiesList partiesList, long waitTime){
		IImagePanelFactory ImagePanelFactory = testEnviroment.getImagePanelFactory();
		IPracticeStationWindowFactory practiceWindowFactory = testEnviroment.getPracticeWindowFactory();
		IChoosingListFactory choosingListFactory = testEnviroment.getChoosingListFactory();
		long actualStationTime = PracticeTestPaths.getStationActualTime(waitTime);
		return new PracticeStation(partiesList,choosingListFactory,practiceWindowFactory,ImagePanelFactory,actualStationTime);
	}
	
	/**
	 * Check that the station build successfully.
	 */
	@Test
	public void buildTest(){
		TestEnvironment buildTestEnviroment = new TestEnvironment("buildTest");
		IPartiesList emptyList = (new PartiesListFactory(new PartyStubFactory())).createInstance();
		PracticeStation testedStation = buildStation(buildTestEnviroment,emptyList);
		buildTestEnviroment.runTest(testedStation);
	}
	
	/**
	 * Check the Mss.
	 */
	@Test
	public void trivialTest(){
		TestEnvironment trivialTestEnviroment = new TestEnvironment("trivialTest");
		IPartyFactory partyFactory = new PartyStubFactory();
		IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		IParty party1 = partyFactory.createInstance("tested1", "1");
		
		Languages language = Languages.Hebrew;
		trivialTestEnviroment.addSetLanguage(language);
		pathes.shortestPathCall(trivialTestEnviroment, party1);
		
		/*
		 * will also work with partiesList = null,
		 * but I want to be fair about the parties.
		 */
		IPartiesList partiesList = partiesListFactory.createInstance();
		partiesList.addParty(party1);
		
		PracticeStation testedStation = buildStation(trivialTestEnviroment,partiesList);
		trivialTestEnviroment.runTest(testedStation);
	}
	
	/**
	 * Check "voting" for 3 parties, then "voting" and change party.
	 */
	@Test
	public void easyTest(){
		TestEnvironment easyTestEnviroment = new TestEnvironment("easyTest");
		IPartyFactory partyFactory = new PartyStubFactory();
		IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		IParty party1 = partyFactory.createInstance("tested1", "1");
		IParty party2 = partyFactory.createInstance("tested2", "2");
		IParty party3 = partyFactory.createInstance("tested3", "3");
		
		pathes.shortestPathCall(easyTestEnviroment, party1);
		pathes.shortestPathCall(easyTestEnviroment, party2);
		pathes.shortestPathCall(easyTestEnviroment, party3);
		pathes.incorrectPartyPathCall(easyTestEnviroment, party1, party2);
		
		IPartiesList partiesList = partiesListFactory.createInstance();
		partiesList.addParty(party1);
		partiesList.addParty(party2);
		partiesList.addParty(party3);
		
		PracticeStation testedStation = buildStation(easyTestEnviroment,partiesList);
		easyTestEnviroment.runTest(testedStation);
	}
	
	/**
	 * Check several paths (one after the other), with showing guides,
	 * re-choosing parties and didn't confirm that understand.
	 */
	@Test
	public void harderTest(){
		TestEnvironment harderTestEnviroment = new TestEnvironment("harderTest");
		IPartyFactory partyFactory = new PartyStubFactory();
		IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		IParty party1 = partyFactory.createInstance("tested1", "1");
		IParty party2 = partyFactory.createInstance("tested2", "2");
		IParty party3 = partyFactory.createInstance("tested3", "3");
		
		IPartiesList partiesList = partiesListFactory.createInstance();
		partiesList.addParty(party1);
		partiesList.addParty(party2);
		partiesList.addParty(party3);
		
		Languages firstLanguage = Languages.English;
		Languages secondLanguage = Languages.Hebrew;
		Languages thirdLanguage = Languages.English;
		
		
		harderTestEnviroment.addSetLanguage(firstLanguage);
		pathes.shortestPathCall(harderTestEnviroment, party1);
		pathes.guidePathCall(harderTestEnviroment, party2, firstLanguage);
		pathes.guidePathCall(harderTestEnviroment, party3, firstLanguage);
		pathes.incorrectPartyPathCall(harderTestEnviroment, party1,party2);
		pathes.incorrectPartyPathCall(harderTestEnviroment, party2,party2);
		
		harderTestEnviroment.addSetLanguage(secondLanguage);
		pathes.guidePathCall(harderTestEnviroment, party1, secondLanguage);
		
		pathes.guidePathCall(harderTestEnviroment, party2, secondLanguage);
		harderTestEnviroment.addSetLanguage(thirdLanguage);
		pathes.guidePathCall(harderTestEnviroment, party2, thirdLanguage);
		pathes.guidePathCall(harderTestEnviroment, party3, thirdLanguage);
		pathes.incorrectPartyPathCall(harderTestEnviroment, partiesList, party3);
		
		harderTestEnviroment.addSetLanguage(thirdLanguage);
		pathes.guidePathCall(harderTestEnviroment, party1, thirdLanguage);
		pathes.didntUnderstandPathCall(harderTestEnviroment, party1, party2, thirdLanguage);
		
		
		PracticeStation testedStation = buildStation(harderTestEnviroment,partiesList);
		harderTestEnviroment.runTest(testedStation);
	}
	
	/**
	 * Assert that exit if "guide seeing" take to long.
	 */
	@Test
	public void trivialWaitingTest(){
		final long stationWaitingTime = 100;
		final long moreThenStationWaitingTime = stationWaitingTime+1;
		
		TestEnvironment trivialWaitingTestEnviroment = new TestEnvironment("trivialWaitingTest");
		IPartyFactory partyFactory = new PartyStubFactory();
		IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		IParty party1 = partyFactory.createInstance("tested1", "1");
		
		
		Languages language = Languages.Hebrew;
		trivialWaitingTestEnviroment.addSetLanguage(language);
		pathes.longFirstGuide(trivialWaitingTestEnviroment, party1, moreThenStationWaitingTime, language,stationWaitingTime);
		
		/*
		 * will also work with partiesList = null,
		 * but I want to be fair about the parties.
		 */
		IPartiesList partiesList = partiesListFactory.createInstance();
		partiesList.addParty(party1);

		PracticeStation testedStation = buildStation(trivialWaitingTestEnviroment,partiesList,stationWaitingTime);
		trivialWaitingTestEnviroment.runTest(testedStation);
	}
	
	/**
	 * Assert that exit if choosing list takes to long.
	 * Check several paths, some to long and other short enough.
	 */
	@Test
	public void easyWaitingTest(){
		final long stationWaitingTime = 100;
		final long moreThenStationWaitingTime = stationWaitingTime+1;
		
		TestEnvironment easyWaitingTestEnviroment = new TestEnvironment("easyWaitingTest");
		IPartyFactory partyFactory = new PartyStubFactory();
		IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		IParty party1 = partyFactory.createInstance("tested1", "1");
		
		
		Languages language = Languages.Hebrew;
		easyWaitingTestEnviroment.addSetLanguage(language);
		pathes.longFirstGuide(easyWaitingTestEnviroment, party1, moreThenStationWaitingTime, language,stationWaitingTime);
		pathes.shortestPathCall(easyWaitingTestEnviroment, party1);
		pathes.longFirstGuide(easyWaitingTestEnviroment, party1, stationWaitingTime, language, stationWaitingTime);
		pathes.longFirstChoose(easyWaitingTestEnviroment, party1, moreThenStationWaitingTime, language, stationWaitingTime);
		pathes.longFirstChoose(easyWaitingTestEnviroment, party1, stationWaitingTime, language, stationWaitingTime);
		
		/*
		 * will also work with partiesList = null,
		 * but I want to be fair about the parties.
		 */
		IPartiesList partiesList = partiesListFactory.createInstance();
		partiesList.addParty(party1);
		PracticeStation testedStation = buildStation(easyWaitingTestEnviroment,partiesList,stationWaitingTime);
		easyWaitingTestEnviroment.runTest(testedStation);
		
	}
	
	/**
	 * Run The complicated paths from paths
	 */
	@Test
	public void harderWaitingTest(){
		final long stationWaitingTime = 100;
		
		TestEnvironment harderWaitingTestEnviroment = new TestEnvironment("harderWaitingTest");
		IPartyFactory partyFactory = new PartyStubFactory();
		PartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		
		
		IPartiesList parties = partiesListFactory.createInstance();
		parties.addParty(partyFactory.createInstance("tested1", "1"));
		parties.addParty(partyFactory.createInstance("tested2", "2"));
		parties.addParty(partyFactory.createInstance("tested3", "3"));
		parties.addParty(partyFactory.createInstance("tested4", "4"));
		parties.addParty(partyFactory.createInstance("tested5", "5"));
		parties.addParty(partyFactory.createInstance("tested6", "6"));
		
		Collection<IParty> partiesCollection = new ArrayList<IParty>();
		for (IParty party : parties) {
			partiesCollection.add(party);
		}
		
		Languages language = Languages.Hebrew;
		harderWaitingTestEnviroment.addSetLanguage(language);
		pathes.complicatedPath(harderWaitingTestEnviroment, partiesCollection, language, stationWaitingTime);
		
		PracticeStation testedStation = buildStation(harderWaitingTestEnviroment,parties,stationWaitingTime);
		harderWaitingTestEnviroment.runTest(testedStation);
	}
	
	/**
	 * Add the needed Stub calls for ending due to time out.
	 * @param testEnvironment: The TestEnvironment
	 */
	private void timeoutEnding(TestEnvironment testEnvironment){
		testEnvironment.addComponentForTest(new ChoosingListRetireComponent());
		testEnvironment.addComponentForTest(new ImagePanelRetireComponent());
		testEnvironment.addComponentForTest(new PrintInfoMessageComponent(Messages.Your_time_is_up));
	}
	
	/**
	 * Build PartiesList with 'amount' parties. The symbols are 1 to amount.
	 * @param amount: the amount of parties.
	 * @return The parties that was builded.
	 */
	private IPartiesList basicParties(int amount) {
		IPartyFactory partyFactory = new PartyStubFactory();
		PartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		IPartiesList parties = partiesListFactory.createInstance();
		for(Integer i = 1; i < amount; i++){
			parties.addParty(partyFactory.createInstance("tested" + i, i.toString()));
		}
		return parties;
	}

	/**
	 * Run A test when the waiting is in JDialog: Do_you_want_to_see_a_guide. 
	 */
	@Test
	public void waitingInJDialog1Test() throws Exception{
		final long stationWaitingTime = 100;
		
		TestEnvironment JDialogWaitingTestEnviroment = new TestEnvironment("waitingInJDialogTest1");

		IPartiesList parties = basicParties(6); 
		
		Languages language = Languages.Hebrew;
		JDialogWaitingTestEnviroment.addSetLanguage(language);
		JDialogWaitingTestEnviroment.addPracticeVoteCall();
		JDialogWaitingTestEnviroment.addComponentForTest(new PrintInfoMessageComponent(Messages.This_station_is_only_for_practice));
		JDialogWaitingTestEnviroment.addComponentForTest(new PrintConfirmationMessageLongComponent(Messages.Do_you_want_to_see_a_guide,false,stationWaitingTime+PracticeTestPaths.getTimeGuard()));
		timeoutEnding(JDialogWaitingTestEnviroment);
		PracticeStation testedStation = buildStation(JDialogWaitingTestEnviroment,parties,stationWaitingTime);
		JDialogWaitingTestEnviroment.runTest(testedStation);
	}
	
	/**
	 * Run A test when the waiting is in JDialog: This_station_is_only_for_practice.
	 */
	@Test
	public void waitingInJDialog2Test() throws Exception{
		final long stationWaitingTime = 100;
		final long toLongWaiting = stationWaitingTime+PracticeTestPaths.getTimeGuard();
		
		TestEnvironment JDialogWaitingTestEnviroment = new TestEnvironment("waitingInJDialogTest2");
		
		IPartiesList parties = basicParties(6); 
		
		Languages language = Languages.Hebrew;
		JDialogWaitingTestEnviroment.addSetLanguage(language);
		
		JDialogWaitingTestEnviroment.addPracticeVoteCall();
		JDialogWaitingTestEnviroment.addComponentForTest(new PrintInfoMessageLongComponent(Messages.This_station_is_only_for_practice,toLongWaiting));
		timeoutEnding(JDialogWaitingTestEnviroment);
		PracticeStation testedStation = buildStation(JDialogWaitingTestEnviroment,parties,stationWaitingTime);
		JDialogWaitingTestEnviroment.runTest(testedStation);
	}
	
	/**
	 * Run A test when the waiting is in JDialog: This_station_is_only_for_practice the second time.
	 */
	@Test
	public void waitingInJDialog3Test() throws Exception{
		final long stationWaitingTime = 100;
		final long toLongWaiting = stationWaitingTime+PracticeTestPaths.getTimeGuard();
		
		TestEnvironment JDialogWaitingTestEnviroment = new TestEnvironment("waitingInJDialogTest3");
		
		IPartiesList parties = basicParties(6); 
		
		Languages language = Languages.Hebrew;
		JDialogWaitingTestEnviroment.addSetLanguage(language);
		JDialogWaitingTestEnviroment.addPracticeVoteCall();
		
		JDialogWaitingTestEnviroment.addComponentForTest(new PrintInfoMessageComponent(Messages.This_station_is_only_for_practice));
		JDialogWaitingTestEnviroment.addComponentForTest(new PrintConfirmationMessageComponent(Messages.Do_you_want_to_see_a_guide,false));
		JDialogWaitingTestEnviroment.addComponentForTest(new PrintInfoMessageLongComponent(Messages.This_station_is_only_for_practice,toLongWaiting));
		timeoutEnding(JDialogWaitingTestEnviroment);
		
		PracticeStation testedStation = buildStation(JDialogWaitingTestEnviroment,parties,stationWaitingTime);
		JDialogWaitingTestEnviroment.runTest(testedStation);
	}
	
	/**
	 * Run A test when the waiting is in JDialog: Do_you_want_to_see_a_guide with true.
	 */
	@Test
	public void waitingInJDialog4Test() throws Exception{
		final long stationWaitingTime = 100;
		final long toLongWaiting = stationWaitingTime+PracticeTestPaths.getTimeGuard();
		
		TestEnvironment JDialogWaitingTestEnviroment = new TestEnvironment("waitingInJDialogTest4");

		IPartiesList parties = basicParties(6); 
		
		Languages language = Languages.Hebrew;
		JDialogWaitingTestEnviroment.addSetLanguage(language);
		JDialogWaitingTestEnviroment.addPracticeVoteCall();
		JDialogWaitingTestEnviroment.addComponentForTest(new PrintInfoMessageComponent(Messages.This_station_is_only_for_practice));
		JDialogWaitingTestEnviroment.addComponentForTest(new PrintConfirmationMessageLongComponent(Messages.Do_you_want_to_see_a_guide,true,toLongWaiting));
		timeoutEnding(JDialogWaitingTestEnviroment);
		PracticeStation testedStation = buildStation(JDialogWaitingTestEnviroment,parties,stationWaitingTime);
		JDialogWaitingTestEnviroment.runTest(testedStation);
	}
	
	/**
	 * Run A test when the waiting is in JDialog: Did_you_intend_to_vote_for.
	 */
	@Test
	public void waitingInJDialog5Test() throws Exception{
		final long stationWaitingTime = 100;
		final long toLongWaiting = stationWaitingTime+PracticeTestPaths.getTimeGuard();
		
		TestEnvironment JDialogWaitingTestEnviroment = new TestEnvironment("waitingInJDialogTest5");

		IPartiesList parties = basicParties(6); 
		IParty party = new PartyStub("t1");
		
		Languages language = Languages.Hebrew;
		JDialogWaitingTestEnviroment.addSetLanguage(language);
		JDialogWaitingTestEnviroment.addPracticeVoteCall();
		JDialogWaitingTestEnviroment.addComponentForTest(new PrintInfoMessageComponent(Messages.This_station_is_only_for_practice));
		JDialogWaitingTestEnviroment.addComponentForTest(new PrintConfirmationMessageComponent(Messages.Do_you_want_to_see_a_guide,false));
		JDialogWaitingTestEnviroment.addComponentForTest(new PrintInfoMessageComponent(Messages.This_station_is_only_for_practice));
		JDialogWaitingTestEnviroment.addComponentForTest(new ChooseListComponent(party));
		JDialogWaitingTestEnviroment.addComponentForTest(new ConfirmationWithPartyLongComponent(Messages.Did_you_intend_to_vote_for,party,false,toLongWaiting));
		timeoutEnding(JDialogWaitingTestEnviroment);
		PracticeStation testedStation = buildStation(JDialogWaitingTestEnviroment,parties,stationWaitingTime);
		JDialogWaitingTestEnviroment.runTest(testedStation);
	}
	
	/**
	 * Run A test when the waiting is in JDialog: Have_you_understood_the_process with false.
	 */
	@Test
	public void waitingInJDialog6Test() throws Exception{
		final long stationWaitingTime = 100;
		final long toLongWaiting = stationWaitingTime+PracticeTestPaths.getTimeGuard();
		
		TestEnvironment JDialogWaitingTestEnviroment = new TestEnvironment("waitingInJDialogTest6");

		IPartiesList parties = basicParties(6); 
		IParty party = new PartyStub("t1");
		
		Languages language = Languages.Hebrew;
		JDialogWaitingTestEnviroment.addSetLanguage(language);
		JDialogWaitingTestEnviroment.addPracticeVoteCall();
		JDialogWaitingTestEnviroment.addComponentForTest(new PrintInfoMessageComponent(Messages.This_station_is_only_for_practice));
		JDialogWaitingTestEnviroment.addComponentForTest(new PrintConfirmationMessageComponent(Messages.Do_you_want_to_see_a_guide,false));
		JDialogWaitingTestEnviroment.addComponentForTest(new PrintInfoMessageComponent(Messages.This_station_is_only_for_practice));
		JDialogWaitingTestEnviroment.addComponentForTest(new ChooseListComponent(party));
		JDialogWaitingTestEnviroment.addComponentForTest(new ConfirmationWithPartyComponent(Messages.Did_you_intend_to_vote_for,party,true));
		JDialogWaitingTestEnviroment.addComponentForTest(new PrintConfirmationMessageLongComponent(Messages.Have_you_understood_the_process,false,toLongWaiting));
		timeoutEnding(JDialogWaitingTestEnviroment);
		PracticeStation testedStation = buildStation(JDialogWaitingTestEnviroment,parties,stationWaitingTime);
		JDialogWaitingTestEnviroment.runTest(testedStation);
	}
}
